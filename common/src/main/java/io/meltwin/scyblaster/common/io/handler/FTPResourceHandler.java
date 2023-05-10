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
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

public class FTPResourceHandler extends MultiThreadedResourceHandler {

    public FTPResourceHandler() {
        super();
        LOGGER = LogManager.getLogger("SBCom FTP");
    }

    @Override
    protected void runRes(@NotNull Resource res) {

    }
}
