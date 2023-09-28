package io.meltwin.scyblaster.minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import io.meltwin.scyblaster.common.FutureCluster;
import io.meltwin.scyblaster.common.OSInfos;
import io.meltwin.scyblaster.common.files.ResourceHandler;
import io.meltwin.scyblaster.common.files.types.FileType;
import io.meltwin.scyblaster.common.files.types.ResourceFile;
import io.meltwin.scyblaster.common.files.types.ResourceStatus;
import io.meltwin.scyblaster.minecraft.adapter.ArgumentAdapter;
import io.meltwin.scyblaster.minecraft.adapter.RuleAdapter;
import io.meltwin.scyblaster.minecraft.dto.assets.AssetsIndex;
import io.meltwin.scyblaster.minecraft.dto.assets.AssetsIndex.Asset;
import io.meltwin.scyblaster.minecraft.dto.version.Argument;
import io.meltwin.scyblaster.minecraft.dto.version.Library;
import io.meltwin.scyblaster.minecraft.dto.version.Rule;
import io.meltwin.scyblaster.minecraft.dto.version.Version;
import io.meltwin.scyblaster.types.ClassPath;

/**
 * 
 */
public class AssetsManager {
    private Logger logger = LogManager.getLogger("ResManager");
    private int versionID;
    private boolean errored = true;

    private static final String TIME_TAKEN = "Took %s ms";

    public AssetsManager(int mcVersionID, @NotNull ClassPath cp) {
        this.versionID = mcVersionID;
        errored = getVersionMetaData() || downloadClient() || downloadLibs(cp) || getAssetsIndex() || downloadAssets();
    }

    /**
     * @return true is the MC assets management phase has raised an error
     */
    public boolean isErrored() {
        return this.errored;
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
    private boolean getVersionMetaData() {
        logger.info("Retrieving Minecraft versions from distant.");

        try {
            ResourceFile versionRes = ResourceHandler
                    .prepareFile(new ResourceFile(MCVersions.getDistVersionFile(versionID), FileType.HTTP,
                            ResourceHandler.getVersionsDir() + MCVersions.getVersion(versionID)
                                    + OSInfos.SEPARATOR + MCVersions.getVersion(versionID) + ".json",
                            MCVersions.getVersionSHA1(versionID),
                            false))
                    .get();

            if (versionRes.status == ResourceStatus.ERROR) {
                logger.fatal("Could not fetch distant version resources descriptor !");
                return true;
            }

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Rule.class, new RuleAdapter());
            builder.registerTypeAdapter(Argument.class, new ArgumentAdapter());
            Gson gson = builder.create();
            versionMetaData = gson.fromJson(new String(Files.readAllBytes(Paths.get(versionRes.localPath))),
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

    /**
     * @return the metadata of the selected version
     */
    public Version getMetaData() {
        return versionMetaData;
    }

    private boolean downloadClient() {
        logger.info("Retrieving Minecraft client from distant.");
        try {
            String clientPath = ResourceHandler.getVersionsDir() + versionMetaData.id + OSInfos.SEPARATOR
                    + versionMetaData.id + ".jar";
            long start = System.nanoTime();
            ResourceFile client = ResourceHandler.prepareFile(new ResourceFile(versionMetaData.downloads.client.url,
                    FileType.HTTP, clientPath, versionMetaData.downloads.client.sha1, false)).get();

            if (client.status == ResourceStatus.ERROR) {
                logger.fatal("Could not fetch distant client executable !");
                return true;
            }
            logger.info(String.format(TIME_TAKEN, Math.round((System.nanoTime() - start) / 1E6)));
            return false;

        } catch (InterruptedException | ExecutionException e) {
            logger.fatal("Interrupted while downloading client !");
            return true;
        }
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
    private List<ResourceFile> getLibsFiles() {
        ArrayList<ResourceFile> libs = new ArrayList<>();

        // Computing each lib file
        int i = 0;
        for (Library lib : versionMetaData.libraries) {
            if (lib == null)
                logger.warn(String.format("Lib #%d: Null lib object", i));
            else if (lib.downloads == null)
                logger.warn(String.format("Lib #%d: Null lib>download attribute", i));
            else if (lib.downloads.artifact == null)
                logger.warn(String.format("Lib #%d: Null lib>download>artifact attribute", i));
            else if (lib.downloads.artifact.url == null)
                logger.warn(String.format("Lib #%d: Null lib>download>artifact>url attribute", i));
            else if (lib.downloads.artifact.path == null)
                logger.warn(String.format("Lib #%d: Null lib>download>artifact>path attribute", i));
            else {
                // TODO: Verify rules
                ResourceFile resFile = new ResourceFile(lib.downloads.artifact.url, FileType.HTTP,
                        ResourceHandler.getLibsDir() + lib.downloads.artifact.path, lib.downloads.artifact.sha1, false);
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
    private boolean downloadLibs(@NotNull ClassPath cp) {
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
                cp.append(resultFile.localPath);
            }
            logger.info(String.format(TIME_TAKEN, Math.round((System.nanoTime() - start) / 1E6)));
            return false;

        } catch (InterruptedException | ExecutionException e) {
            logger.fatal("Interrupted while downloading MC libs !");
            return true;
        }
    }

    /*
     * ========================================================================
     * Assets
     * ========================================================================
     */
    private static final String RESOURCE_ENDPOINT = "https://resources.download.minecraft.net/";
    private AssetsIndex assetIndex;

    /**
     * Get the asset index for the desired version
     * 
     * @return true if an error has been raised
     */
    private boolean getAssetsIndex() {
        logger.info("Retrieving asset index from distant.");
        try {

            // Test for file existance & is the file has the same SHA1
            // Else, download it
            ResourceFile assetsIndexFile = ResourceHandler
                    .prepareFile(
                            new ResourceFile(versionMetaData.assetIndex.url, FileType.HTTP,
                                    ResourceHandler.getAssetsDir() + "indexes" + OSInfos.SEPARATOR
                                            + versionMetaData.assets
                                            + ".json",
                                    versionMetaData.assetIndex.sha1,
                                    false))
                    .get();
            if (assetsIndexFile.status == ResourceStatus.ERROR)
                return true;

            Gson gson = new Gson();
            assetIndex = gson.fromJson(new String(Files.readAllBytes(Paths.get(assetsIndexFile.localPath))),
                    AssetsIndex.class);

            return false;

        } catch (InterruptedException | ExecutionException e) {
            logger.fatal("Interrupted while downloading asset index !");
            return true;
        } catch (IOException e) {
            logger.fatal("There was an IO error during the version metadata file parsing !");
            e.printStackTrace();
            return true;
        }
    }

    @NotNull
    private String getAssetPath(@NotNull AssetsIndex.Asset asset, @NotNull String separator) {
        return asset.hash.substring(0, 2) + separator + asset.hash;
    }

    @NotNull
    private String getAssetPath(@NotNull AssetsIndex.Asset asset) {
        return this.getAssetPath(asset, OSInfos.SEPARATOR);
    }

    /**
     * Make a list of ResourceFile object for the file handler
     * 
     * @return the list of the assets to download
     */
    private List<ResourceFile> getAssetsFiles() {
        ArrayList<ResourceFile> assets = new ArrayList<>();

        // Computing each asset file
        int i = 0;
        for (Entry<String, Asset> asset : assetIndex.objects.entrySet()) {
            if (asset.getValue() == null)
                logger.warn(String.format("Asset #%d: Null asset object", i));
            else if (asset.getValue().hash == null)
                logger.warn(String.format("Asset #%d: Null lib>download attribute", i));
            else {
                // TODO: Verify rules

                // Make resource file
                ResourceFile resFile = new ResourceFile(RESOURCE_ENDPOINT + getAssetPath(asset.getValue(), "/"),
                        FileType.HTTP,
                        String.format("%sobjects%s%s", ResourceHandler.getAssetsDir(), OSInfos.SEPARATOR,
                                getAssetPath(asset.getValue())),
                        asset.getValue().hash, false);
                assets.add(resFile);
            }
            i++;
        }

        return assets;
    }

    /**
     * Download all the libs needed by MC
     * 
     * @return true if an error occured
     */
    private boolean downloadAssets() {
        logger.info("Retrieving Minecraft assets from distant.");
        try {
            List<ResourceFile> filesList = getAssetsFiles();
            logger.debug(String.format("Preparing %d assets files.", filesList.size()));
            long start = System.nanoTime();
            FutureCluster<ResourceFile> filesCluster = ResourceHandler.prepareFiles(filesList);

            for (Future<ResourceFile> future : filesCluster) {
                ResourceFile resultFile = future.get();
                if (resultFile.status == ResourceStatus.ERROR)
                    return true;
            }
            logger.info(String.format(TIME_TAKEN, Math.round((System.nanoTime() - start) / 1E6)));
            return false;

        } catch (InterruptedException | ExecutionException e) {
            logger.fatal("Interrupted while downloading MC assets !");
            return true;
        }
    }
}
