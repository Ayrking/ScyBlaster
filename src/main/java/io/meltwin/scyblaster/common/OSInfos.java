package io.meltwin.scyblaster.common;

public abstract class OSInfos {
    private OSInfos() {
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();
    public static final boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
    public static final boolean IS_MAC = (OS.indexOf("mac") >= 0);
    public static final boolean IS_UNIX = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") >= 0);
    public static final boolean IS_SOLARIS = (OS.indexOf("sunos") >= 0);

    public static final String SEPARATOR = (IS_WINDOWS) ? "\\" : "/";
}
