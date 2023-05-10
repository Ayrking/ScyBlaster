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
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

abstract class MultiThreadedResourceHandler extends ResourceHandler {

    protected final Queue<Resource> TO_PROC = new LinkedList<>();
    protected int MAX_PARALLEL = 4;

    protected Logger LOGGER = LogManager.getLogger("SBCom ResHand");

    protected MultiThreadedResourceHandler() { super(); }

    @Override
    public void prepare_resource(@NotNull Resource res) { TO_PROC.add(res); }

    @Override
    public CompletableFuture<Boolean> launch_preparation() { return CompletableFuture.supplyAsync(this::runAll); }

    protected boolean runAll() {
        LOGGER.trace(String.format("Running download of %d files", TO_PROC.size()));

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future[] tasks = new Future[MAX_PARALLEL];
        boolean ended = false;
        boolean task_still_running;
        AtomicInteger n_done = new AtomicInteger();

        // Wait for all resources to be available
        while (!ended) {
            task_still_running = false;
            for (int i = 0; i < MAX_PARALLEL; i++) {
                // Check for ended tasks
                if (tasks[i] != null) {
                    if (tasks[i].isDone() || tasks[i].isCancelled()) {
                        tasks[i] = null;
                    }
                    else {
                        task_still_running = true;
                    }
                }

                // Check for next elements to process if there is a free tasks space
                if (TO_PROC.size() > 0 && tasks[i] == null) {
                    final int idx_task = i;
                    tasks[i] = executorService.submit(() -> {
                        Resource res = TO_PROC.poll();
                        if (res != null) {
                            LOGGER.trace(String.format("Preparing file #%1d [%2d] - %s", n_done.getAndIncrement(), idx_task, res.get_resource_path()));
                            runRes(res);
                        }
                    });
                }
            }

            ended = TO_PROC.size() == 0 && !task_still_running;
        }
        return true;
    }

    abstract protected void runRes(@NotNull Resource res);
}
