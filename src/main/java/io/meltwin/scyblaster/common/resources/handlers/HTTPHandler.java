package io.meltwin.scyblaster.common.resources.handlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceStatus;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HTTPHandler implements IBaseHandler {

    private ExecutorService executor;
    private Logger logger = LogManager.getLogger("HTTP Handler");

    public HTTPHandler(@NotNull ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public @NotNull Future<ResourceFile> getFile(@NotNull ResourceFile file) {
        return executor.submit(() -> {
            try {
                // If missing file informations => Error
                if (file.distPath == null || file.localPath == null) {
                    file.status = ResourceStatus.ERROR;
                    return file;
                }

                // Check if is there locally
                if (isSameLocally(file)) {
                    file.status = ResourceStatus.READY;
                    return file;
                }

                // Downloads it
                file.status = ResourceStatus.RUNNING;
                FileUtils.copyURLToFile(file.distPath, file.localPath.toFile());

                // Check if downloaded
                if (file.localPath.toFile().exists()) {
                    file.status = ResourceStatus.READY;
                    logger.trace(String.format("Downloaded %s -> %s", file.distPath, file.localPath));
                } else {
                    logger.error(String.format("File could not be saved locally (%s)", file.distPath));
                    file.status = ResourceStatus.ERROR;
                }
            } catch (Exception e) {
                file.status = ResourceStatus.ERROR;
                e.printStackTrace();
            }
            return file;
        });
    }

    @Override
    public @Nullable ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public @NotNull Logger getLogger() {
        return logger;
    }

}
