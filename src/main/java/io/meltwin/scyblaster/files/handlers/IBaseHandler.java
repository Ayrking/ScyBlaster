package io.meltwin.scyblaster.files.handlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.files.types.ResourceFile;

public interface IBaseHandler {

    @NotNull
    Future<ResourceFile> getFile(@NotNull ResourceFile file);

    @Nullable
    ExecutorService getExecutor();

}
