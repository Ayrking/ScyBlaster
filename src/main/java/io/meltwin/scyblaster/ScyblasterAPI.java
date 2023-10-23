package io.meltwin.scyblaster;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.exceptions.*;
import io.meltwin.scyblaster.common.resources.ResourceHandler;
import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.config.project.ProjectConfiguration;
import io.meltwin.scyblaster.minecraft.AssetsHub;
import io.meltwin.scyblaster.minecraft.version.VersionDescriptor;

/**
 * API Entrypoint for launching any MC Versions
 */
public class ScyblasterAPI implements Logging, AutoCloseable {

    private AssetsHub hub = null;
    private final ProjectConfiguration projectConfig;

    public ScyblasterAPI(final @NotNull ProjectConfiguration projectConfig) {
        this.projectConfig = projectConfig;
    }

    @Override
    public void close() throws Exception {
        finfo("Shutting down Scyblaster API !");
        ResourceHandler.destroy();
    }

    // ====================================================================
    // Mutators
    // ====================================================================
    /**
     * Return the ProjectConfiguration instance used for this launcher
     */
    public final @NotNull ProjectConfiguration getProjectConfiguration() {
        return this.projectConfig;
    }

    /**
     * Return an instantiated AssetsHub object
     */
    public final @NotNull AssetsHub getAssetsHub() {
        if (hub == null)
            hub = new AssetsHub(this.projectConfig);
        return hub;
    }

    // ====================================================================
    // Launcher STEPS
    // ====================================================================

    /**
     * STEP 1 :
     * Download and prepare the version descriptor for the given version
     * 
     * @param version the version name to launch
     */
    public final @Nullable VersionDescriptor getVersion(@NotNull String version)
            throws UnavailableResourceException, NullManifestException, InvalidWrapperException {
        return getAssetsHub().getVersionsDescriptor(version);
    }

    /**
     * STEP 1 :
     * Download and prepare the version descriptor for the given version
     * 
     * @param version the version index to launch
     */
    public final @Nullable VersionDescriptor getVersion(int version)
            throws UnavailableResourceException, NullManifestException, InvalidWrapperException {
        return getAssetsHub().getVersionsDescriptor(version);
    }

    /**
     * STEP 2 :
     * Prepare all the resources needed for the given version
     * 
     * @param version the version descriptor to use to prepare the assets
     */
    public final void prepareResource(@NotNull VersionDescriptor version)
            throws NullDescriptorException, NullIndexException, UnavailableResourceException, InvalidWrapperException {
        getAssetsHub().prepareVersion(version);
    }

    // ====================================================================
    // Complete launching protocol
    // ====================================================================

    /**
     * Launch the given minecraft version
     */
    public final void launchMC(@NotNull String version) {
        try {
            log().debug(projectConfig);
            VersionDescriptor desc = getVersion(version);
            prepareResource(desc);
        } catch (ScyblasterException e) {
            e.printStackTrace(log());
        }

    }

}
