package io.meltwin.scyblaster.common.system;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class OSInfos {
    private OSInfos() {
    }

    private static final OSType determine() {
        String osProperty = System.getProperty("os.name").toLowerCase();
        if (osProperty.indexOf("win") >= 0)
            return OSType.WINDOWS;
        if (osProperty.indexOf("mac") >= 0)
            return OSType.MACOS;
        if (osProperty.indexOf("nix") >= 0 || osProperty.indexOf("nux") >= 0 || osProperty.indexOf("aix") >= 0)
            return OSType.LINUX;
        if (osProperty.indexOf("sunos") >= 0)
            return OSType.SOLARIS;
        return OSType.UNKNOWN;
    }

    // ========================================================================
    // OS Properties
    // ========================================================================
    public static final OSType OS = determine();
    public static final String SEPARATOR = (OS == OSType.WINDOWS) ? "\\" : "/";
    public static final String ARCH = System.getProperty("os.arch");

    // ========================================================================
    // Utils functions
    // ========================================================================
    public static final String getOSName() {
        return OS.name();
    }

    /**
     * @return The path to the app data directory
     */
    public static Path getAppDataDir() {
        switch (OS) {
            case WINDOWS:
                return Paths.get(System.getenv("AppData"));
            case LINUX:
            case SOLARIS:
                return Paths.get(System.getProperty("user.home"));
            case MACOS:
                return Paths.get(System.getProperty("user.home") + "/Library/Application Support");
            default:
                return Paths.get(".");
        }
    }

}
