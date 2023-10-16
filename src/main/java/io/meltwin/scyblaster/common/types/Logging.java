package io.meltwin.scyblaster.common.types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Logging {

    /**
     * @return the logger of the class
     */
    default Logger log() {
        return LogManager.getLogger(this.getClass().getSimpleName());
    }

}
