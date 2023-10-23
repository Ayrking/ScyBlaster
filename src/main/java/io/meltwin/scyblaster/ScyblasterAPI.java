package io.meltwin.scyblaster;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.exceptions.*;
import io.meltwin.scyblaster.common.resources.ResourceHandler;
import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.config.ConfigHolder;
import io.meltwin.scyblaster.config.project.ProjectConfiguration;
import io.meltwin.scyblaster.minecraft.AssetsHub;
import io.meltwin.scyblaster.minecraft.version.VersionDescriptor;

/**
 * API Entrypoint for launching any MC Versions
 */
public class ScyblasterAPI implements Logging, AutoCloseable {

    private AssetsHub hub = null;
    private final ConfigHolder configs = new ConfigHolder();

    public ScyblasterAPI(final @NotNull ProjectConfiguration projectConfig) {
        configs.clear();
        configs.projectConfig = projectConfig;
        hub = new AssetsHub(this.configs.projectConfig);
    }

    @Override
    public void close() throws Exception {
        finfo("Shutting down Scyblaster API !");
        ResourceHandler.destroy();
        configs.clear();
    }

    // ====================================================================
    // Mutators
    // ====================================================================
    /**
     * Return the ProjectConfiguration instance used for this launcher
     */
    public final @NotNull ProjectConfiguration getProjectConfiguration() {
        return this.configs.projectConfig;
    }

    /**
     * Return an instantiated AssetsHub object
     */
    public final @NotNull AssetsHub getAssetsHub() {
        return hub;
    }

    // ====================================================================
    // PART A - Resource Preparation
    // ====================================================================

    /**
     * STEP 1 :
     * Download and prepare the version descriptor for the given version
     * 
     * @param version the version name to launch
     */
    public final @Nullable VersionDescriptor getVersion(@NotNull String version)
            throws UnavailableResourceException, NullManifestException, InvalidWrapperException {
        return hub.getVersionsDescriptor(version);
    }

    /**
     * STEP 1 :
     * Download and prepare the version descriptor for the given version
     * 
     * @param version the version index to launch
     */
    public final @Nullable VersionDescriptor getVersion(int version)
            throws UnavailableResourceException, NullManifestException, InvalidWrapperException {
        return hub.getVersionsDescriptor(version);
    }

    /**
     * STEP 2 :
     * Prepare all the resources needed for the given version
     * 
     * @param version the version descriptor to use to prepare the assets
     */
    public final void prepareResource(@NotNull VersionDescriptor version)
            throws NullDescriptorException, NullIndexException, UnavailableResourceException, InvalidWrapperException {
        hub.prepareVersion(version);
    }

    // ====================================================================
    // Complete launching protocol
    // ====================================================================

    /**
     * Launch the given minecraft version
     */
    public final void launchMC(@NotNull String version) {
        try {
            // Prepare Vanilla
            log().debug(this.configs.projectConfig);
            VersionDescriptor desc = getVersion(version);
            prepareResource(desc);
        } catch (ScyblasterException e) {
            e.printStackTrace(log());
        }

    }

}
