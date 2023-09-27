package io.meltwin.scyblaster.minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import io.meltwin.scyblaster.common.FutureCluster;
import io.meltwin.scyblaster.files.ResourceHandler;
import io.meltwin.scyblaster.files.types.FileType;
import io.meltwin.scyblaster.files.types.ResourceFile;
import io.meltwin.scyblaster.files.types.ResourceStatus;
import io.meltwin.scyblaster.minecraft.adapter.ArgumentAdapter;
import io.meltwin.scyblaster.minecraft.adapter.RuleAdapter;
import io.meltwin.scyblaster.minecraft.dto.version.Argument;
import io.meltwin.scyblaster.minecraft.dto.version.Library;
import io.meltwin.scyblaster.minecraft.dto.version.Rule;
import io.meltwin.scyblaster.minecraft.dto.version.Version;

/**
 * 
 */
public class AssetsManager {
    private Logger logger = LogManager.getLogger("ResManager");
    private int versionID;

    public boolean errored = true;

    public AssetsManager(int mcVersionID) {
        this.versionID = mcVersionID;
        errored = getVersionMetaData() || downloadLibs();
    }

    /*
     * ========================================================================
     * Version Meta Data
     * ========================================================================
     */

    private Version versionMetaData;

    /**
     * Get the version meta-data file and parse it into the Version object
     * 
     * @return true if the operation was successfull
     */
    public boolean getVersionMetaData() {
        logger.info("Retrieving Minecraft versions from distant.");

        // TODO: Check locally for the file

        try {
            // TODO: Determine real file place
            Path tempFile = Files.createTempFile(null, ".json");
            ResourceFile version_file = ResourceHandler
                    .prepareFile(new ResourceFile(MCVersions.getDistVersionFile(versionID), FileType.HTTP,
                            tempFile.toString()))
                    .get();

            if (version_file.status == ResourceStatus.ERROR) {
                logger.fatal("Could not fetch distant version resources descriptor !");
                return true;
            }

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Rule.class, new RuleAdapter());
            builder.registerTypeAdapter(Argument.class, new ArgumentAdapter());
            Gson gson = builder.create();
            versionMetaData = gson.fromJson(new String(Files.readAllBytes(Paths.get(version_file.localPath))),
                    Version.class);

            return false;

        } catch (InterruptedException | ExecutionException e) {
            logger.fatal("Interrupted while retrieving MC versions list !");
            return true;
        } catch (JsonSyntaxException e) {
            logger.fatal("There was an error during version metadata file parsing !");
            e.printStackTrace();
            return true;
        } catch (IOException e) {
            logger.fatal("There was an IO error during the version metadata file parsing !");
            e.printStackTrace();
            return true;
        }
    }

    public Version getMetaData() {
        return versionMetaData;
    }

    /*
     * ========================================================================
     * Libraries
     * ========================================================================
     */

    /**
     * Make a list of ResourceFile object for the file handler
     * 
     * @return the list of the libs to download
     */
    public List<ResourceFile> getLibsFiles() {
        ArrayList<ResourceFile> libs = new ArrayList<>();

        // Computing each lib file
        int i = 0;
        for (Library lib : versionMetaData.libraries) {
            if (lib == null)
                logger.warn(String.format("Null lib object at index %d", i));
            else if (lib.downloads == null)
                logger.warn(String.format("Null lib>download attribute at index %d", i));
            else if (lib.downloads.artifact == null)
                logger.warn(String.format("Null lib>download>artifact attribute at index %d", i));
            else if (lib.downloads.artifact.url == null)
                logger.warn(String.format("Null lib>download>artifact>url attribute at index %d", i));
            else if (lib.downloads.artifact.path == null)
                logger.warn(String.format("Null lib>download>artifact>path attribute at index %d", i));
            else {
                // TODO: Verify existence + SHA1
                // TODO: Verify rules
                ResourceFile resFile = new ResourceFile(lib.downloads.artifact.url, FileType.HTTP,
                        ResourceHandler.getLibsDir() + lib.downloads.artifact.path);
                libs.add(resFile);
            }
            i++;
        }

        return libs;
    }

    /**
     * Download all the libs needed by MC
     * 
     * @return true if an error occured
     */
    public boolean downloadLibs() {
        logger.info("Retrieving Minecraft libs from distant.");
        try {
            List<ResourceFile> filesList = getLibsFiles();
            logger.debug(String.format("Preparing %d libs.", filesList.size()));
            long start = System.nanoTime();
            FutureCluster<ResourceFile> filesCluster = ResourceHandler.prepareFiles(filesList);

            for (Future<ResourceFile> future : filesCluster) {
                ResourceFile resultFile = future.get();
                if (resultFile.status == ResourceStatus.ERROR)
                    return true;
            }
            logger.info(String.format("Took %d ms", Math.round((System.nanoTime() - start) / 1E6)));
            return false;

        } catch (InterruptedException | ExecutionException e) {
            logger.fatal("Interrupted while downloading MC libs !");
            return true;
        }

    }
}
