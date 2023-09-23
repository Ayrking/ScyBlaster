package io.meltwin.scyblaster.files;

import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.FutureCluster;
import io.meltwin.scyblaster.files.handlers.IBaseHandler;
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
     * File Preparationg
     * ########################################
     */
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private EnumMap<FileType, IBaseHandler> handlers;

    public Future<ResourceFile> internalPreparation(@NotNull ResourceFile file) {
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
    public static FutureCluster<ResourceFile> prepareFiles(@NotNull List<@NotNull ResourceFile> files) {
        FutureCluster<ResourceFile> out = new FutureCluster<>();
        for (ResourceFile file : files)
            out.add(instance().internalPreparation(file));
        return out;
    }
}
