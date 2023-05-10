/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.io;

import io.meltwin.scyblaster.common.SBCommon;
import io.meltwin.scyblaster.common.io.handler.FTPResourceHandler;
import io.meltwin.scyblaster.common.io.handler.HTTPResourceHandler;
import io.meltwin.scyblaster.common.io.handler.ResourceHandler;
import io.meltwin.scyblaster.common.io.resource.PreparedResources;
import io.meltwin.scyblaster.common.io.resource.Resource;
import io.meltwin.scyblaster.common.io.resource.ResourceSource;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

/**
 * A manager that provide resource-related utils
 */
public final class ResourceManager {

    private final static Logger LOGGER = SBCommon.LOGGER;

    /*  ==============================================================
                            Resource Configuration
        ==============================================================
     */
    private static ResourceConfiguration CONFIG = null;

    /**
     * Set a custom configuration structure for resource management
     * @param config the configuration object
     */
    public static void setConfiguration(@NotNull ResourceConfiguration config) {
        CONFIG = config;
    }

    /**
     * Get the resource configuration object
     * @return the resource configuration
     */
    @NotNull
    public static ResourceConfiguration get_config() {
        if (CONFIG == null)
            return DefaultConfiguration.getInstance();
        return CONFIG;
    }

    /*  ==============================================================
                            Resource Preparation
        ==============================================================
     */
    private static final HashMap<ResourceSource, ResourceHandler> HANDLERS = new HashMap<ResourceSource, ResourceHandler>() {{
        put(ResourceSource.HTTP, new HTTPResourceHandler());
        put(ResourceSource.FTP, new FTPResourceHandler());
    }};

    /**
     * Prepare a list of resources locally
     * @param resources the list of resources to prepare
     * @return a response giving details about which resource is ready or not
     */
    public static PreparedResources prepare_resources(@NotNull ArrayList<Resource> resources) {
        LOGGER.debug(String.format("Prepare %d files locally", resources.size()));

        // Distribute resources to each handler
        for(Resource res: resources)
            if (HANDLERS.containsKey(res.source()))
               HANDLERS.get(res.source()).prepare_resource(res);

        // Launch handler works
        LOGGER.debug("Launching handler inner works");
        int idx = 0;
        CompletableFuture[] futures = new CompletableFuture[HANDLERS.size()];
        for(Entry<ResourceSource, ResourceHandler> entry: HANDLERS.entrySet())
            futures[idx++] = entry.getValue().launch_preparation();

        // Waiting for everyone to finish
        LOGGER.debug("Waiting for handlers to finish");
        boolean must_break;
        do {
            must_break = true;
            for (CompletableFuture future : futures) {
                if (future != null && !future.isDone()) {
                    must_break = false;
                    break;
                }
            }
        } while (!must_break);

        // Get how many files are done
        LOGGER.debug("The resource manager has processed the required files.");
        PreparedResources response = new PreparedResources();
        for (Resource r: resources) {
            if (r.is_resource_ready()) {
                LOGGER.debug(String.format("%s is now ready", r.get_resource_path()));
                response.ready_res.add(r);
            }
            else {
                LOGGER.error(String.format("%s could not be loaded !", r.get_resource_path()));
                response.error_res.add(r);
            }
        }

        LOGGER.debug(String.format("%1d/%2d files are ready", response.number_of_ready(), resources.size()));
        if (response.number_of_error() != 0) {
            LOGGER.error(String.format("%1d/%2d files could not be loaded !", response.number_of_error(), resources.size()));
        }

        LOGGER.debug("Preparation ended successfully !");
        return response;
    }

}
