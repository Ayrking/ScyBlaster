package io.meltwin.scyblaster.minecraft;

public final class MCArguments {
    // MC arguments
    public static final String USERNAME = "--username";
    public static final String VERSION = "--version";
    public static final String GAME_DIR = "--gameDir";
    public static final String ASSETS_DIR = "--assetsDir";
    public static final String ASSETS_IDX = "--assetIndex";

    // Auth arguments
    public static final String UUID = "--uuid";
    public static final String ACCESS_TOKEN = "--accessToken";
    public static final String CLIENT_ID = "--clientId";
    public static final String XUID = "--xuid";

    // Game-related arguments
    public static final String USER_TYPE = "--userType";
    public static final String VERSION_TYPE = "--versionType";
    public static final String DEMO = "--demo";
    public static final String WIDTH = "--width";
    public static final String HEIGHT = "--height";
    public static final String QUICKPLAY = "--quickPlayPath";
    public static final String SINGLE_QUICKPLAY = "--quickPlaySingleplayer";
    public static final String MULTIP_QUICKPLAY = "--quickPlayMultiplayer";
    public static final String REALMS_QUICKPLAY = "--quickPlayRealms";

    // JVM arguments
    public static final String NAVTIVES_PATH = "${natives_directory}";
    public static final String LIBS_PATH = "-Djava.library.path=${natives_directory}";
    public static final String JNA_TMP = "-Djna.tmpdir=${natives_directory}";
    public static final String LWJGL_DIR = "-Dorg.lwjgl.system.SharedLibraryExtractPath=${natives_directory}";
    public static final String NETTY_DIR = "-Dio.netty.native.workdir=${natives_directory}";

    public static final String PLACEHOLDER_BRAND = "${launcher_name}";
    public static final String LAUNCHER_BRAND = "-Dminecraft.launcher.brand=${launcher_name}";

    public static final String PLACEHOLDER_VERSION = "${launcher_version}";
    public static final String LAUNCHER_VERSION = "-Dminecraft.launcher.version=${launcher_version}";

    public static final String PLACEHOLDER_CLASSPATH = "${classpath}";
    public static final String CLASSPATH = "-cp";

    private MCArguments() {
    }
}
