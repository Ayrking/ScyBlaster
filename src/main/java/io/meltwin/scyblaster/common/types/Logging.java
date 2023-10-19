package io.meltwin.scyblaster.common.types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Interface providing logging utilities on top those of the Log4J Logger
 */
public interface Logging {

    static final String DEBUG_TIME_TAKEN = "Took %d ms";

    /**
     * @return the logger of the class
     */
    default Logger log() {
        return LogManager.getLogger(this.getClass().getSimpleName());
    }

    /**
     * Print the given delta as a debug message
     * 
     * @param startTime the start time
     * @param endTime   the end time
     */
    default void printDelta(long startTime, long endTime) {
        fdebug(DEBUG_TIME_TAKEN, (int) ((endTime - startTime) / 1E6));
    }

    /**
     * Print the given delta as a debug message. The end time is the current time at
     * the function call
     * 
     * @param startTime the start time
     */
    default void printDelta(long startTime) {
        printDelta(startTime, System.nanoTime());
    }

    // ====================================================================
    // Formatted string output
    // ====================================================================
    default void ftrace(@NotNull String formatString, @NotNull Object... args) {
        log().trace(String.format(formatString, args));
    }

    default void fdebug(@NotNull String formatString, @NotNull Object... args) {
        log().debug(String.format(formatString, args));
    }

    default void finfo(@NotNull String formatString, @NotNull Object... args) {
        log().info(String.format(formatString, args));
    }

    default void fwarn(@NotNull String formatString, @NotNull Object... args) {
        log().warn(String.format(formatString, args));
    }

    default void ferror(@NotNull String formatString, @NotNull Object... args) {
        log().error(String.format(formatString, args));
    }

    default void ffatal(@NotNull String formatString, @NotNull Object... args) {
        log().fatal(String.format(formatString, args));
    }
}
