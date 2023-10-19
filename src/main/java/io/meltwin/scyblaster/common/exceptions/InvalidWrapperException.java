package io.meltwin.scyblaster.common.exceptions;

import org.jetbrains.annotations.NotNull;

public class InvalidWrapperException extends ScyblasterException {

    public InvalidWrapperException(@NotNull String errorMsg) {
        super(errorMsg);
    }

}
