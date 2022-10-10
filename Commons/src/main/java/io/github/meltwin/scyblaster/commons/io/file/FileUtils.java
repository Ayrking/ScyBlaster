package io.github.meltwin.scyblaster.commons.io.file;

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;


/**
 * File utils for the starter & launcher
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public final class FileUtils {
    public static final String USER_DIR = getUserDir();

    @NotNull private static String getUserDir() {
        LogManager.getRootLogger().info("Loading for OS : "+System.getProperty("os.name"));

        if (isWindows())
            return String.format("%s/Roaming/.scyblaster/", System.getenv("APPDATA"));
        if (isMacOS())
            return String.format("%s/.scyblaster", "");
        return String.format("%s/.scyblaster/", System.getProperty("user.home"));
    }
    private static boolean isWindows() {
        if (System.getProperty("os.name").length()<8)
            return false;
        String os_name = System.getProperty("os.name").toLowerCase(Locale.ENGLISH).substring(0,8);
        return Objects.equals(os_name, "windows");
    }
    private static boolean isMacOS() {
        if (System.getProperty("os.name").length()<7)
            return false;
        return Objects.equals(System.getProperty("os.name").toLowerCase(Locale.ENGLISH).substring(0,7), "mac os");
    }
}
