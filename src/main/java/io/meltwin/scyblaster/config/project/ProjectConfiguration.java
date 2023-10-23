package io.meltwin.scyblaster.config.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Path;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.system.OSInfos;
import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.common.types.Pair;

/**
 * Project configs wrapper class.
 * Contains every property needed for the API
 */
public class ProjectConfiguration implements Logging, Serializable {

    private transient DTOConfigs configs;

    // ====================================================================
    // --------------------------------------------------------------------
    //
    // ------------------------ OBJECT Constructor ------------------------
    //
    // --------------------------------------------------------------------
    // ====================================================================

    /**
     * Create a new project configuration with default values.
     */
    public ProjectConfiguration() {
        configs = new DTOConfigs();
    }

    /**
     * Parse and fill the project configuration from an XML file
     * 
     * @param istream the InputStream of the file
     */
    private final void fromInputStream(@NotNull InputStream istream) {
        try {
            JAXBContext context = JAXBContext.newInstance(DTOConfigs.class);
            configs = (DTOConfigs) context.createUnmarshaller().unmarshal(istream);
        } catch (JAXBException e) {
            configs = new DTOConfigs();
            log().fatal("Could'nt load project configs from input stream !");
            e.printStackTrace();
        }
    }

    /**
     * Create a new project configuration from the given XML InputStream
     * 
     * @param istream the InputStream corresponding to the XML project
     *                configuration.
     */
    public ProjectConfiguration(@NotNull InputStream istream) {
        fromInputStream(istream);
    }

    /**
     * Create a new project configuration from the given package XML file.
     * 
     * @param pkgfile the path to the package file starting from the root of the
     *                jar achive.
     */
    public ProjectConfiguration(@NotNull String pkgfile) {
        try (InputStream istream = getClass().getClassLoader().getResourceAsStream(pkgfile)) {
            if (istream == null) {
                log().fatal(String.format("File %s doesn't exist inside the jar archive !", pkgfile));
                System.exit(-1);
            }
            fromInputStream(istream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ====================================================================
    // --------------------------------------------------------------------
    //
    // --------------------------- OBJECT GETTER --------------------------
    //
    // --------------------------------------------------------------------
    // ====================================================================

    public DTOConfigs getConfigs() {
        return configs;
    }

    // ====================================================================
    // Local Directories
    // ====================================================================
    /**
     * Return the root directory for the whole launcher (e.g. ".scyblaster")
     */
    public @NotNull String getRootDirName() {
        return configs.getResourceConfig().getBaseDir();
    }

    /**
     * Return the path to the root directory
     */
    public @NotNull Path getRootPath() {
        return OSInfos.getAppDataDir().resolve(getRootDirName());
    }

    /**
     * Return the assets directory name (e.g. "assets")
     */
    public @NotNull String getAssetsDirName() {
        return this.configs.getResourceConfig().getAssetsDir();
    }

    /**
     * Return the path to the assets directory
     */
    public @NotNull Path getAssetsPath() {
        return getRootPath().resolve(getAssetsDirName());
    }

    /**
     * Return the versions directory name (e.g. "versions")
     */
    public @NotNull String getVersionsDirName() {
        return this.configs.getResourceConfig().getVersionsDir();
    }

    /**
     * Return the path to the versions directory
     */
    public @NotNull Path getVersionsPath() {
        return getRootPath().resolve(getVersionsDirName());
    }

    /**
     * Return the libraries directory name (e.g. "libs")
     */
    public @NotNull String getLibsDirName() {
        return this.configs.getResourceConfig().getLibsDir();
    }

    /**
     * Return the path to the libs directory
     */
    public @NotNull Path getLibsPath() {
        return getRootPath().resolve(getLibsDirName());
    }

    /**
     * Return the natives directory name (e.g. "bin")
     */
    public @NotNull String getNativesDirName() {
        return this.configs.getResourceConfig().getNativesDir();
    }

    /**
     * Return the path to the natives directory
     */
    public @NotNull Path getNativesPath() {
        return getRootPath().resolve(getNativesDirName());
    }

    // ====================================================================
    // Minecraft Version
    // ====================================================================
    /**
     * Return the type of selector for the project allowed versions
     */
    public VersionType getAllowedMCVersionNumber() {
        return this.configs.getMinecraftConfigs().getType();
    }

    /**
     * Return the allowed version range
     */
    public @NotNull Pair<String, String> getAllowedMCVersions() {
        return this.configs.getMinecraftConfigs().getVersion();
    }

    /**
     * Return whether the client should be in online mode (authenticated) or not
     */
    public boolean isOnline() {
        return this.configs.getMinecraftConfigs().isOnlineGame();
    }

    /**
     * Return whether the client should be in demo mode or not
     */
    public boolean isInDemoMode() {
        return this.configs.getMinecraftConfigs().isDemo();
    }

    // ====================================================================
    // Quickplay
    // ====================================================================
    /**
     * Return the world for a quick connect to a singleplayer world
     */
    public @Nullable String getQuickplaySingleplayer() {
        return this.configs.getMinecraftConfigs().getQuickPlayS();
    }

    /**
     * Return the URL for a quick connect to a multiplayer server
     */
    public @Nullable String getQuickplayMultiplayer() {
        return this.configs.getMinecraftConfigs().getQuickPlayM();
    }

    /**
     * Return the URL for a quick connect to a realm server
     */
    public @Nullable String getQuickplayRealms() {
        return this.configs.getMinecraftConfigs().getQuickPlayR();
    }

    // ====================================================================
    // Endpoint
    // ====================================================================

    /**
     * Return the endpoint for getting the versions manifest (list of all versions)
     */
    public @NotNull String getVersionsManifestEndpoints() {
        return this.configs.getEndpointsConfig().getVersionsManifestEndpoint();
    }

    /**
     * Return the root endpoint for download all assets
     */
    public @NotNull String getAssetsRootEndpoints() {
        return this.configs.getEndpointsConfig().getAssetRootEndpoint();
    }

    // ====================================================================
    // --------------------------------------------------------------------
    //
    // ------------------------- OBJECT SERIALIZER ------------------------
    //
    // --------------------------------------------------------------------
    // ====================================================================
    static final String DEBUG_ROOT = "\tROOT   : %1$-16s - %2$s\n";
    static final String DEBUG_ASSETS = "\tASSETS : %1$-16s - %2$s\n";
    static final String DEBUG_LIBS = "\tLIBS   : %1$-16s - %2$s\n";
    static final String DEBUG_NATIVES = "\tNATIVES: %1$-16s - %2$s\n";
    static final String DEBUG_VERSION = "\tVERSION: %1$-16s - %2$s\n";
    static final String DEBUG_ALLOWED_VERSION = "\n\tMC Versions: %1$s | %2$s -> %3$s\n";
    static final String DEBUG_IS_DEMO = "\tDemo Mode : %b\n";
    static final String DEBUG_IS_ONLINE = "\tOnline : %b\n";
    static final String DEBUG_QUICKPLAY = "\n\tQuickplay:\n\t\tSingleplayer: %1$s\n\t\tMultiplayer: %2$s\n\t\tRealms: %3$s\n";

    static final String DEBUG_ENDPOINTS = "\n\tEndpoints roots:\n\t\tMANIFEST: %1$s\n\t\tASSETS:   %2$s";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\nLoaded launcher configuration:\n");
        builder.append(String.format(DEBUG_ROOT, getRootDirName(), getRootPath()));
        builder.append(String.format(DEBUG_ASSETS, getAssetsDirName(), getAssetsPath()));
        builder.append(String.format(DEBUG_LIBS, getLibsDirName(), getLibsPath()));
        builder.append(
                String.format(DEBUG_NATIVES, getNativesDirName(), getNativesPath()));
        builder.append(
                String.format(DEBUG_VERSION, getVersionsDirName(), getVersionsPath()));
        builder.append(String.format(DEBUG_ALLOWED_VERSION, getAllowedMCVersionNumber().toString(),
                getAllowedMCVersions().getFirst(), getAllowedMCVersions().getSecond()));
        builder.append(String.format(DEBUG_IS_DEMO, isInDemoMode()));
        builder.append(String.format(DEBUG_IS_ONLINE, isOnline()));
        builder.append(String.format(DEBUG_QUICKPLAY, getQuickplaySingleplayer(), getQuickplayMultiplayer(),
                getQuickplayRealms()));
        builder.append(String.format(DEBUG_ENDPOINTS, getVersionsManifestEndpoints(), getAssetsRootEndpoints()));
        return builder.toString();
    }
}