package io.meltwin.scyblaster.common.resources;

import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.resources.handlers.HTTPHandler;
import io.meltwin.scyblaster.common.resources.handlers.IBaseHandler;
import io.meltwin.scyblaster.common.resources.types.ResourceType;
import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceStatus;
import io.meltwin.scyblaster.common.types.FutureCluster;
import io.meltwin.scyblaster.common.types.Logging;

/**
 * Resource handling class
 * Prepare files on the computer for further usage
 * Singleton-object
 */
public class ResourceHandler implements Logging {

    /*
     * ########################################
     * INSTANCE HANDLING
     * ########################################
     */
    private static ResourceHandler instance = null;

    private ResourceHandler() {
        handlers = new EnumMap<>(ResourceType.class);
        handlers.put(ResourceType.HTTP, new HTTPHandler(executor));
    }

    /**
     * @return a valid instance of the Resource Handler
     */
    @NotNull
    public static ResourceHandler instance() {
        if (instance == null)
            instance = new ResourceHandler();
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    /*
     * ########################################
     * File Preparation
     * ########################################
     */
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private EnumMap<ResourceType, IBaseHandler> handlers;

    private @NotNull Future<ResourceFile> internalPreparation(@NotNull ResourceFile file) {
        if (handlers.get(file.type) != null)
            return handlers.get(file.type).getFile(file);

        // Unimplemented handler
        log().error(String.format("Handler not implemented for file type %s", file.type));
        return executor.submit(() -> {
            file.status = ResourceStatus.ERROR;
            return file;
        });
    }

    /**
     * Prepare the given file locally
     * 
     * @param file the file to prepare
     * @return a future to wait for completion
     */
    public static @NotNull Future<ResourceFile> prepareFile(@NotNull ResourceFile file) {
        return instance().internalPreparation(file);
    }

    /**
     * Prepare the files locally
     * 
     * @param files a list to the files to prepare
     * @return a list of futures to wait for completion
     */
    public static @NotNull FutureCluster<ResourceFile> prepareFiles(@NotNull List<ResourceFile> files) {
        FutureCluster<ResourceFile> out = new FutureCluster<>();
        for (ResourceFile file : files)
            out.add(instance().internalPreparation(file));
        return out;
    }
}
