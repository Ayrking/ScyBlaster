package io.meltwin.scyblaster.files;

import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.Launcher;
import io.meltwin.scyblaster.common.FutureCluster;
import io.meltwin.scyblaster.common.OSInfos;
import io.meltwin.scyblaster.files.handlers.IBaseHandler;
import io.meltwin.scyblaster.files.types.FileType;
import io.meltwin.scyblaster.files.types.ResourceFile;
import io.meltwin.scyblaster.files.handlers.HTTPHandler;

/**
 * Resource handling class
 * Prepare files on the computer for further usage
 * Singleton-object
 */
public class ResourceHandler {

    /*
     * ########################################
     * INSTANCE HANDLING
     * ########################################
     */
    private static ResourceHandler instance = null;

    private ResourceHandler() {
        handlers = new EnumMap<>(FileType.class);
        handlers.put(FileType.HTTP, new HTTPHandler(executor));
    }

    /**
     * @return a valid instance of the Resource Handler
     */
    @NotNull
    public static ResourceHandler instance() {
        if (instance == null)
            instance = new ResourceHandler();
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    /*
     * ########################################
     * File Preparation
     * ########################################
     */
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private EnumMap<FileType, IBaseHandler> handlers;

    private Future<ResourceFile> internalPreparation(@NotNull ResourceFile file) {
        return handlers.get(file.type).getFile(file);
    }

    /**
     * Prepare the given file locally
     * 
     * @param file the file to prepare
     * @return a future to wait for completion
     */
    public static Future<ResourceFile> prepareFile(@NotNull ResourceFile file) {
        return instance().internalPreparation(file);
    }

    /**
     * Prepare the files locally
     * 
     * @param files a list to the files to prepare
     * @return a list of futures to wait for completion
     */
    public static FutureCluster<ResourceFile> prepareFiles(
            @NotNull List<io.meltwin.scyblaster.files.types.ResourceFile> files) {
        FutureCluster<ResourceFile> out = new FutureCluster<>();
        for (ResourceFile file : files)
            out.add(instance().internalPreparation(file));
        return out;
    }

    /*
     * ########################################
     * Path utils
     * ########################################
     */
    /**
     * @return The path to the apps data directory
     */
    public static String getAppDataDir() {
        if (OSInfos.IS_WINDOWS) {
            return System.getenv("AppData");
        }
        if (OSInfos.IS_UNIX) {
            return System.getProperty("user.home");
        }
        if (OSInfos.IS_MAC) {
            return System.getProperty("user.home") + "/Library/Application Support";
        }
        return "";
    }

    /**
     * @return the path to the root directory for the launcher
     */
    public static String getBaseDir() {
        return getAppDataDir() + OSInfos.SEPARATOR + Launcher.configs.getResourceConfig().getBaseDir();
    }

    /**
     * @return the path to the assets directory
     */
    public static String getAssetsDir() {
        return getBaseDir() + OSInfos.SEPARATOR + Launcher.configs.getResourceConfig().getAssetsDir()
                + OSInfos.SEPARATOR;
    }

    /**
     * @return the path to the libs directory
     */
    public static String getLibsDir() {
        return getBaseDir() + OSInfos.SEPARATOR + Launcher.configs.getResourceConfig().getLibsDir() + OSInfos.SEPARATOR;
    }

    /**
     * @return the path to the libs directory
     */
    public static String getVersionsDir() {
        return getBaseDir() + OSInfos.SEPARATOR + Launcher.configs.getResourceConfig().getVersionsDir()
                + OSInfos.SEPARATOR;
    }
}
