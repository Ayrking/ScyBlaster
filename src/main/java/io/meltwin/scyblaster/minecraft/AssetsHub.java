package io.meltwin.scyblaster.minecraft;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.resources.ResourceHandler;
import io.meltwin.scyblaster.common.resources.types.JSONWrapper;
import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceStatus;
import io.meltwin.scyblaster.common.types.FutureCluster;
import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.config.ProjectConfiguration;
import io.meltwin.scyblaster.minecraft.assets.AssetIndex;
import io.meltwin.scyblaster.minecraft.releases.VersionsManifest;
import io.meltwin.scyblaster.minecraft.version.VersionDescriptor;

/**
 * Main class for getting Minecraft assets list
 */
public final class AssetsHub implements Logging {

    private static final String DEBUG_TIME_TAKEN = "File preparation took %d ms";
    private static final String DEBUG_TIME_TAKEN_MULT = "Files preparation took %d ms";

    private final ProjectConfiguration prjCfgs;

    public AssetsHub(@NotNull ProjectConfiguration projectConfigs) {
        this.prjCfgs = projectConfigs;
    }

    // ====================================================================
    // Versions Manifest V2
    // ====================================================================
    private VersionsManifest manifest = null;

    /**
     * Return the downloaded and loaded versions manifest for Minecraft
     */
    public final @Nullable VersionsManifest getVersionsManifest() {
        if (manifest == null) {
            log().debug("Preparing the version manifest !");
            manifest = loadDTO(VersionsManifest.class, VersionsManifest.getManifestResource(prjCfgs));
        }
        return manifest;
    }

    // ====================================================================
    // Versions Manifest V2
    // ====================================================================

    /**
     * Return the downloaded and loaded versions manifest for Minecraft
     * 
     * @param versionName the name of the version (e.g. "1.6.2")
     */
    public final @Nullable VersionDescriptor getVersionsDescriptor(@NotNull String versionName) {
        VersionsManifest localManifest = getVersionsManifest();
        if (localManifest == null) {
            log().error(String.format(
                    "Couldn't fetch version descriptor %s because the version manifest couldn't been downloaded !",
                    versionName));
            return null;
        }
        return this.getVersionsDescriptor(localManifest.getVersionID(versionName));
    }

    /**
     * Return the downloaded and loaded versions manifest for Minecraft
     * 
     * @param versionIdx the index of the version (0 is the latest release)
     */
    public final @Nullable VersionDescriptor getVersionsDescriptor(int versionIdx) {
        VersionsManifest localManifest = getVersionsManifest();
        if (localManifest == null) {
            log().error(String.format(
                    "Couldn't fetch version descriptor %d because the version manifest couldn't been downloaded !",
                    versionIdx));
            return null;
        }
        log().debug("Preparing version descriptor for version %d (%s)", versionIdx,
                localManifest.getVersionName(versionIdx));
        return loadDTO(VersionDescriptor.class,
                localManifest.getVersionResource(prjCfgs, versionIdx));
    }

    // ====================================================================
    // Asset Index
    // ====================================================================

    /**
     * 
     * @param version
     * @return
     */
    public final @Nullable AssetIndex getAssetIndex(@NotNull VersionDescriptor version) {
        // Sanity check
        if (version == null) {
            log().error("Couldn't load asset index as the given version descriptor is null !");
            return null;
        }

        // Get the file
        log().debug("Prepapring asset index %s", version.getAssetIndexName());
        return loadDTO(AssetIndex.class, version.getAssetIndexResource(prjCfgs));
    }

    // ====================================================================
    // Assets Download
    // ====================================================================

    public final void prepareVersion(@NotNull VersionDescriptor descriptor) {
        // Sanity check
        if (descriptor == null) {
            log().error("Couldn't load asset index as the given version descriptor is null !");
            return;
        }

        // Download all the assets
        AssetIndex assets = getAssetIndex(descriptor);
        if (assets == null) {
            log().error("Can't keep on preparing the version: the asset index is null !");
            return;
        }
        log().debug(String.format("Preparing all the assets for Minecraft %s", descriptor.getVersionName()));
        prepareAssets(assets.getAssetsList(prjCfgs));

        // Download the libs
        log().debug(String.format("Preparing all the libraries for Minecraft %s", descriptor.getVersionName()));
        prepareAssets(descriptor.getLibsFiles(prjCfgs));

        // Download the client
        log().debug(String.format("Preparing the client for Minecraft %s", descriptor.getVersionName()));
        prepareAsset(descriptor.getClientJARResource(prjCfgs));
    }

    // ====================================================================
    // Logic
    // ====================================================================
    /**
     * Download and load a JSON DTO
     * 
     * @param <T>  the Java DTO Wrapper
     * @param cT   the class of the wrapper
     * @param file the resource to fetch
     * @return the wanted Java DTO Wrapper, or null in case of error
     */
    private final <T extends JSONWrapper> @Nullable T loadDTO(Class<T> cT, @NotNull ResourceFile file) {
        try {
            // Prepare file
            long start = System.nanoTime();
            ResourceFile fileResult = ResourceHandler.prepareFile(file).get();
            log().debug(String.format(DEBUG_TIME_TAKEN, (int) ((System.nanoTime() - start) / 1E6)));

            // And load it
            if (fileResult.status == ResourceStatus.READY)
                return cT.getConstructor(ResourceFile.class).newInstance(file);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            log().fatal(String.format(
                    "Couldn't instantiate DTO Wrapper %s. Please check that there's a constructor with a single ResourceFile parameter !",
                    cT.getSimpleName()));
            e.printStackTrace();
        } catch (InterruptedException | ExecutionException e) {
            log().fatal("Couldn't get the wanted resource file !");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Prepare a list of resource files
     * 
     * @param fileList the list of files to prepare
     */
    private final boolean prepareAssets(@NotNull List<ResourceFile> fileList) {
        // Sanity Check
        if (fileList == null) {
            log().error("Couldn't prepare the resources, the list is a null object");
            return false;
        }

        // Try to download all the files
        try {
            long start = System.nanoTime();
            FutureCluster<ResourceFile> fileCluster = ResourceHandler.prepareFiles(fileList);
            for (Future<ResourceFile> fut : fileCluster) {
                if (fut.get().status != ResourceStatus.READY)
                    return false;
            }
            log().debug(String.format(DEBUG_TIME_TAKEN_MULT, (int) ((System.nanoTime() - start) / 1E6)));

            return true;
        } catch (InterruptedException | ExecutionException e) {
            log().fatal("Couldn't get the wanted resource file !");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Prepare a single resource file
     * 
     * @param file the file to prepare
     */
    private final boolean prepareAsset(@NotNull ResourceFile file) {
        // Sanity Check
        if (file == null) {
            log().error("Couldn't prepare the resources, the ResourceFile is a null object");
            return false;
        }

        // Try to download all the files
        try {
            long start = System.nanoTime();
            ResourceFile fileResource = ResourceHandler.prepareFile(file).get();
            log().debug(String.format(DEBUG_TIME_TAKEN, (int) ((System.nanoTime() - start) / 1E6)));

            return (fileResource.status == ResourceStatus.READY);
        } catch (InterruptedException | ExecutionException e) {
            log().fatal("Couldn't get the wanted resource file !");
            e.printStackTrace();
        }
        return false;
    }

}
