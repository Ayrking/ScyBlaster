package io.meltwin.scyblaster.config.project;

import java.io.InputStream;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.types.Pair;

// ====================================================================
// --------------------------------------------------------------------
//
// ------------------------ BUILDER DESCRIPTION -----------------------
//
// --------------------------------------------------------------------
// ====================================================================

/**
 * Project configuration builder
 */
public class ProjectConfigurationBuilder {
    private final ProjectConfiguration projectConfig;

    public ProjectConfigurationBuilder() {
        projectConfig = new ProjectConfiguration();
    }

    /**
     * Create a builder based on a project file (project.xml)
     * 
     * @param pkgFile the path of the config file inside the jar
     */
    public ProjectConfigurationBuilder(@NotNull String pkgFile) {
        projectConfig = new ProjectConfiguration(pkgFile);
    }

    /**
     * Create a builder based on an InputStream
     * 
     * @param istream the InputStream
     */
    public ProjectConfigurationBuilder(@NotNull InputStream istream) {
        projectConfig = new ProjectConfiguration(istream);
    }

    /**
     * Make the project configuration based on the previous instructions
     */
    public ProjectConfiguration make() {
        return projectConfig;
    }

    // ====================================================================
    // Local Directories
    // ====================================================================
    /**
     * Set the base directory for the whole launcher installer as .minecraft for the
     * default minecraft launcher (default ".scyblaster")
     * 
     * @param name the name of the directory (e.g. ".minecraft")
     */
    public ProjectConfigurationBuilder setRootDirName(@NotNull String name) {
        projectConfig.getConfigs().getResourceConfig().setBaseDir(name);
        return this;
    }

    /**
     * Set the assets directory name (default "assets")
     * 
     * @param name the name of the directory (e.g. "assets")
     */
    public ProjectConfigurationBuilder setAssetsDirName(@NotNull String name) {
        projectConfig.getConfigs().getResourceConfig().setAssetsDir(name);
        return this;
    }

    /**
     * Set the versions directory name (default "versions")
     * 
     * @param name the name of the directory (e.g. "versions")
     */
    public ProjectConfigurationBuilder setVersionsDirName(@NotNull String name) {
        projectConfig.getConfigs().getResourceConfig().setVersionsDir(name);
        return this;
    }

    /**
     * Set the libraries directory name (default "libs")
     * 
     * @param name the name of the directory (e.g. "libs")
     */
    public ProjectConfigurationBuilder setLibsDirName(@NotNull String name) {
        projectConfig.getConfigs().getResourceConfig().setLibsDir(name);
        return this;
    }

    /**
     * Set the natives directory name (default "bin")
     * 
     * @param name the name of the directory (e.g. "bin")
     */
    public ProjectConfigurationBuilder setNativesDirName(@NotNull String name) {
        projectConfig.getConfigs().getResourceConfig().setNativesDir(name);
        return this;
    }

    // ====================================================================
    // Minecraft Version
    // ====================================================================
    /**
     * Set the allowed Minecraft version range to be launched
     * 
     * @param versions the range of versions allowed
     */
    public ProjectConfigurationBuilder setAllowedMCVersion(Pair<String, String> versions) {
        projectConfig.getConfigs().getMinecraftConfigs().setType(VersionType.RANGE);
        projectConfig.getConfigs().getMinecraftConfigs().setVersions(versions);
        return this;
    }

    /**
     * Set the Minecraft version to launch
     * 
     * @param versions the version allowed
     */
    public ProjectConfigurationBuilder setAllowedMCVersion(@NotNull String version) {
        projectConfig.getConfigs().getMinecraftConfigs().setType(VersionType.SINGLE);
        projectConfig.getConfigs().getMinecraftConfigs().setVersions(new Pair<>(version, version));
        return this;
    }

    /**
     * Set whether Minecraft is launched in demo mode or not
     * 
     * @param isDemoOn if true, Minecraft is launched in demo mode
     */
    public ProjectConfigurationBuilder setDemo(boolean isDemoOn) {
        projectConfig.getConfigs().getMinecraftConfigs().setDemo(isDemoOn);
        return this;
    }

    /**
     * Set whether the client will be authenticated or not
     * 
     * @param isOnline true if the client will be authenticated
     */
    public ProjectConfigurationBuilder setOnlineGame(boolean isOnline) {
        projectConfig.getConfigs().getMinecraftConfigs().setOnline(isOnline);
        return this;
    }

    // ====================================================================
    // Quickplay
    // ====================================================================
    /**
     * Set the quickplay url for singleplayer
     * 
     * @param url the url of the singleplayer world
     */
    public ProjectConfigurationBuilder setQuickplaySingleplayer(@NotNull String url) {
        projectConfig.getConfigs().getMinecraftConfigs().setQuickPlayS(url);
        return this;
    }

    /**
     * Set the quickplay url for multiplayer
     * 
     * @param url the url of the multiplayer server
     */
    public ProjectConfigurationBuilder setQuickplayMultiplayer(@NotNull String url) {
        projectConfig.getConfigs().getMinecraftConfigs().setQuickPlayM(url);
        return this;
    }

    /**
     * Set the quickplay url for reamls
     * 
     * @param url the url of the realm server
     */
    public ProjectConfigurationBuilder setQuickplayRealms(@NotNull String url) {
        projectConfig.getConfigs().getMinecraftConfigs().setQuickPlayR(url);
        return this;
    }

    // ====================================================================
    // Endpoint
    // ====================================================================

    /**
     * Set the endpoint for getting the versions manifest (list of all versions)
     */
    public @NotNull ProjectConfigurationBuilder setVersionsManifestEndpoints(@NotNull String endpoint) {
        projectConfig.getConfigs().getEndpointsConfig().setVersionsManifestEndpoint(endpoint);
        return this;
    }

    /**
     * Set the root endpoint for download all assets
     */
    public @NotNull ProjectConfigurationBuilder setAssetsRootEndpoints(@NotNull String endpoint) {
        projectConfig.getConfigs().getEndpointsConfig().setAssetRootEndpoint(endpoint);
        return this;
    }
}
