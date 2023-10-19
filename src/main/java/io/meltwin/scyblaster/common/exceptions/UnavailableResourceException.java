package io.meltwin.scyblaster.common.exceptions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.resources.types.ResourceFile;

/**
 * Exception raised when a resource couldn't be loaded
 */
public class UnavailableResourceException extends ScyblasterException {

    private static final String EXCEPTION_MSG = "The resource %s couldn't be prepared : %s";

    private final ResourceFile file;

    public UnavailableResourceException(@NotNull ResourceFile file) {
        super(String.format(EXCEPTION_MSG, file.distPath, file.status));
        this.file = file;
    }

    public UnavailableResourceException(@NotNull Exception e) {
        super(String.format("Loading interrupted by %1$s%n%2$s",
                e.getClass().getSimpleName(),
                e.getMessage()));
        this.file = null;
    }

    public final @Nullable ResourceFile getResource() {
        return this.file;
    }
}
