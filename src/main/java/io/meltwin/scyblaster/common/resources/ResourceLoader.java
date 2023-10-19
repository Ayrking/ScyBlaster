package io.meltwin.scyblaster.common.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.exceptions.UnavailableResourceException;
import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceStatus;
import io.meltwin.scyblaster.common.types.FutureCluster;
import io.meltwin.scyblaster.common.types.Logging;

/**
 * This interface provides abstractors to load ResourceFile
 */
public interface ResourceLoader extends Logging {
    static final String ERROR_LIST_NULL = "Couldn't prepare the resources, the list is a null object";
    static final String ERROR_RES_FILE_NULL = "Couldn't prepare the resources, the ResourceFile is a null object";
    static final String ERROR_RES_UNAVAILABLE = "Couldn't get the wanted resource";

    /**
     * Prepare a list of resource files
     * 
     * @param fileList the list of files to prepare
     */
    public default boolean prepareAssets(@NotNull List<ResourceFile> fileList) throws UnavailableResourceException {
        // Sanity Check
        if (fileList == null) {
            log().error(ERROR_LIST_NULL);
            return false;
        }

        // Try to download all the files
        UnavailableResourceException exception = null;
        fileList = new ArrayList<>();
        try {
            long start = System.nanoTime();
            FutureCluster<ResourceFile> fileCluster = ResourceHandler.prepareFiles(fileList);
            for (Future<ResourceFile> fut : fileCluster) {
                if (exception != null) {
                    fut.cancel(true);
                    continue;
                }

                // Process file
                ResourceFile f = fut.get();
                fileList.add(f);
                fut.cancel(true);
                if (f.status != ResourceStatus.READY)
                    exception = new UnavailableResourceException(f);
            }
            printDelta(start);

            // If there was an error in the loading
            if (exception != null)
                throw exception;
        } catch (InterruptedException | ExecutionException e) {
            throw new UnavailableResourceException(e);
        }
        return true;
    }

    /**
     * Prepare a single resource file
     * 
     * @param file the file to prepare
     */
    public default boolean prepareAsset(@NotNull ResourceFile file) throws UnavailableResourceException {
        // Sanity Check
        if (file == null) {
            ferror(ERROR_RES_FILE_NULL);
            return false;
        }

        // Try to download all the files
        try {
            long start = System.nanoTime();
            file = ResourceHandler.prepareFile(file).get();
            printDelta(start);

            if (file.status != ResourceStatus.READY)
                throw new UnavailableResourceException(file);
        } catch (InterruptedException | ExecutionException e) {
            throw new UnavailableResourceException(e);
        }
        return true;
    }
}
