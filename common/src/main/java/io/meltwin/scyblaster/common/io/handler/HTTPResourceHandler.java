/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.io.handler;

import io.meltwin.scyblaster.common.io.resource.Resource;
import io.meltwin.scyblaster.common.io.resource.ResourceStatus;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.net.URL;

public class HTTPResourceHandler extends MultiThreadedResourceHandler {

    public HTTPResourceHandler() {
        super();
        LOGGER = LogManager.getLogger("SBCom HTTP");
        Configurator.setLevel(LOGGER, Level.TRACE);
    }

    /**
     * Download a file with HTTP
     * 
     * @param res the resource to download
     */
    protected void runRes(@Nullable Resource res) {
        if (res == null)
            return;
        try {
            URL url = new URL(res.get_resource_path());
            File fileLocation = new File(res.get_local_path());

            FileUtils.copyURLToFile(url, fileLocation);
            if (fileLocation.exists()) {
                res.set_status(ResourceStatus.READY);
            } else {
                LOGGER.error(String.format("File could not be saved locally (%s)", res.get_resource_path()));
                res.set_status(ResourceStatus.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.set_error(e);
            res.set_status(ResourceStatus.ERROR);
        }
    }
}
