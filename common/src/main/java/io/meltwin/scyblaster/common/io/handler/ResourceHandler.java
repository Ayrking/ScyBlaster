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
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Handle resource of a certain type
 */
abstract public class ResourceHandler {
    protected ResourceHandler() {}

    abstract public void prepare_resource(@NotNull Resource res);
    abstract public CompletableFuture<Boolean> launch_preparation();
}
