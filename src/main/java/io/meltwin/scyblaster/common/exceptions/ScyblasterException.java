package io.meltwin.scyblaster.common.exceptions;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class ScyblasterException extends Exception {

    public ScyblasterException(@NotNull String errorMsg) {
        super(errorMsg);
    }

    public final void printStackTrace(@NotNull Logger logger) {
        logger.fatal(this.getLocalizedMessage());
    }
}