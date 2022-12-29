package io.github.meltwin.scyblaster.commons.io;

import io.github.meltwin.scyblaster.commons.Pair;
import io.github.meltwin.scyblaster.commons.io.file.FileType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Methods for logging utilities
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class LogUtils {
    private LogUtils() {}

    /*
        =========================
                LOG TITLE
        =========================
     */
    static final int WIDTH = 100;
    static final String LINE = Strings.repeat("#", WIDTH);
    static final String SPACE = Strings.repeat(" ", WIDTH);
    public static void logTitle(@NotNull final String title) {
        int padav = (int) Math.ceil((WIDTH-title.length())/2.);
        int padap = (int) Math.floor((WIDTH-title.length())/2.);
        String out = Strings.repeat(" ", padav)+title+Strings.repeat(" ", padap);

        LogManager.getRootLogger().info(String.format("\n%4$s%1$s%4$s%2$s%4$s%3$s%4$s%2$s%4$s%1$s", LINE, SPACE, out, "\n"));
    }

    /*
        =========================
              LOG File List
        =========================
     */
    public static void traceList(@NotNull final String msg, @NotNull final ArrayList<Pair<FileType, String>> files) {
        StringBuilder out = new StringBuilder(msg);
        if (files.isEmpty()) out.append("no files.");
        for (Pair<FileType, String> p : files)
            out.append(String.format("\n\t- %-10s : %s", p.getFirst().name(), p.getSecond()));
        LogManager.getRootLogger().trace(out.toString());
    }
    public static void traceList(@NotNull final String msg,
                                 @NotNull final ArrayList<Pair<FileType, String>> files,
                                 HashMap<String, String> file_path) {
        StringBuilder out = new StringBuilder(msg);
        if (files.isEmpty()) out.append("no files.");
        for (Pair<FileType, String> p : files) {
            out.append(String.format(
                    "\n\t- %-10s : %-20s -> %s",
                    p.getFirst().name(),
                    p.getSecond(),
                    file_path.get(p.getSecond())
            ));
        }
        LogManager.getRootLogger().trace(out.toString());
    }
}
