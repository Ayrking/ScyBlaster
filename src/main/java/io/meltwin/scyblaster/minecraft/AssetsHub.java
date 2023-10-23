package io.meltwin.scyblaster.minecraft;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.exceptions.*;
import io.meltwin.scyblaster.common.resources.ResourceLoader;
import io.meltwin.scyblaster.common.resources.dto.DTOLoader;
import io.meltwin.scyblaster.config.project.ProjectConfiguration;
import io.meltwin.scyblaster.minecraft.assets.AssetIndex;
import io.meltwin.scyblaster.minecraft.releases.VersionsManifest;
import io.meltwin.scyblaster.minecraft.version.VersionDescriptor;

/**
 * Main class for getting Minecraft assets list
 */
public final class AssetsHub implements DTOLoader, ResourceLoader {

    private final ProjectConfiguration prjCfgs;

    public AssetsHub(@NotNull ProjectConfiguration projectConfigs) {
        this.prjCfgs = projectConfigs;
    }

    // ====================================================================
    // Versions Manifest V2
    // ====================================================================
    private static final String PREPARING_MANIFEST = "Preparing the version manifest !";
    private VersionsManifest manifest = null;

    /**
     * Return the downloaded and loaded versions manifest for Minecraft
     */
    public final @Nullable VersionsManifest getVersionsManifest()
            throws UnavailableResourceException, InvalidWrapperException {
        if (manifest == null) {
            log().debug(PREPARING_MANIFEST);
            manifest = loadDTO(VersionsManifest.class, VersionsManifest.getManifestResource(prjCfgs));
        }
        return manifest;
    }

    // ====================================================================
    // Versions Manifest V2
    // ====================================================================
    private static final String PREPARING_VERSION = "Preparing version descriptor for version %d (%s)";

    /**
     * Return the downloaded and loaded versions manifest for Minecraft
     * 
     * @param versionName the name of the version (e.g. "1.6.2")
     */
    public final @Nullable VersionDescriptor getVersionsDescriptor(final @NotNull String versionName)
            throws UnavailableResourceException, NullManifestException, InvalidWrapperException {
        VersionsManifest localManifest = getVersionsManifest();
        if (localManifest == null)
            throw new NullManifestException();
        return this.getVersionsDescriptor(localManifest.getVersionID(versionName));
    }

    /**
     * Return the downloaded and loaded versions manifest for Minecraft
     * 
     * @param versionIdx the index of the version (0 is the latest release)
     */
    public final @Nullable VersionDescriptor getVersionsDescriptor(int versionIdx)
            throws UnavailableResourceException, NullManifestException, InvalidWrapperException {
        // Checking for manifest
        VersionsManifest localManifest = getVersionsManifest();
        if (localManifest == null)
            throw new NullManifestException();

        // Loading DTO into the Java class
        log().debug(String.format(PREPARING_VERSION, versionIdx, localManifest.getVersionName(versionIdx)));
        return loadDTO(VersionDescriptor.class, localManifest.getVersionResource(prjCfgs, versionIdx));
    }

    // ====================================================================
    // Asset Index
    // ====================================================================
    private static final String PREPARING_ASSETS_INDEX = "Prepapring asset index %s";

    /**
     * Return the downloaded and loaded asset index for the given version
     * 
     * @param version the version descriptor of the wanted version
     */
    public final @Nullable AssetIndex getAssetIndex(final @NotNull VersionDescriptor version)
            throws NullDescriptorException, UnavailableResourceException, InvalidWrapperException {
        // Sanity check
        if (version == null)
            throw new NullDescriptorException();

        // Get the file
        log().debug(String.format(PREPARING_ASSETS_INDEX, version.getAssetIndexName()));
        return loadDTO(AssetIndex.class, version.getAssetIndexResource(prjCfgs));
    }

    // ====================================================================
    // Resources preparations
    // ====================================================================
    private static final String PREPARE_ASSETS = "Preparing all the assets for Minecraft %s";
    private static final String PREPARE_LIBS = "Preparing all the libraries for Minecraft %s";
    private static final String PREPARE_CLIENT = "Preparing the client for Minecraft %s";

    /**
     * Prepare all the assets (assets, libs, native, client) for the given version.
     * 
     * @param descriptor the version descriptor for the wanted version
     */
    public final void prepareVersion(final @NotNull VersionDescriptor descriptor)
            throws NullDescriptorException, NullIndexException, UnavailableResourceException, InvalidWrapperException {
        // Download all the assets
        AssetIndex assets = getAssetIndex(descriptor);
        if (assets == null)
            throw new NullIndexException();

        // Prepare the assets
        fdebug(PREPARE_ASSETS, descriptor.getVersionName());
        prepareAssets(assets.getAssetsList(prjCfgs));

        // Download the libs
        fdebug(PREPARE_LIBS, descriptor.getVersionName());
        prepareAssets(descriptor.getLibsFiles(prjCfgs));

        // Download the client
        fdebug(PREPARE_CLIENT, descriptor.getVersionName());
        prepareAsset(descriptor.getClientJARResource(prjCfgs));
    }
}
