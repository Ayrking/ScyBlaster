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
    /*
        =========================
              Project File
              Configuration
        =========================
     */
    static boolean DIR_SET = false;
    static String USER_DIR = getOSLocalDir("scyblaster", true);

    static final String MSG_DIR_ALREADY_DEFINED = "Launcher directory was already define. Launcher can be find at : %s";
    static final String MSG_OS_CONF = "Loading for OS : %s";

    /*
        =========================
             GETTER / SETTER
        =========================
     */
    public static @NotNull String setUserDir(final String launcher_dir) {
        if (DIR_SET)
                LogManager.getRootLogger().error(String.format(MSG_DIR_ALREADY_DEFINED, USER_DIR));
        else
            USER_DIR = getOSLocalDir(launcher_dir);
        return USER_DIR;
    }
    @NotNull public static String getUserDir() {return USER_DIR;}

    /*
        =========================
               OS Relative
               Local Making
        =========================
     */
    @NotNull static String getOSLocalDir(final @NotNull String launcher_dir) {
        return getOSLocalDir(launcher_dir, false);
    }
    @NotNull static String getOSLocalDir(final @NotNull String launcher_dir, boolean quiet) {
        if (!quiet) LogManager.getRootLogger().info(String.format(MSG_OS_CONF, System.getProperty("os.name")));

        if (isWindows())
            return String.format("%s/Roaming/.%s/", System.getenv("APPDATA"), launcher_dir);
        if (isMacOS())
            return String.format("%s/.%s/", "", launcher_dir);
        return String.format("%s/.%s/", System.getProperty("user.home"), launcher_dir);
    }
    static boolean isWindows() {
        if (System.getProperty("os.name").length()<8)
            return false;
        String os_name = System.getProperty("os.name").toLowerCase(Locale.ENGLISH).substring(0,8);
        return Objects.equals(os_name, "windows");
    }
    static boolean isMacOS() {
        if (System.getProperty("os.name").length()<7)
            return false;
        return Objects.equals(System.getProperty("os.name").toLowerCase(Locale.ENGLISH).substring(0,7), "mac os");
    }
}
