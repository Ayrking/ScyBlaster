package io.meltwin.scyblaster.minecraft.version;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.resources.dto.JSONWrapper;
import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceType;
import io.meltwin.scyblaster.common.types.ClassPath;
import io.meltwin.scyblaster.common.types.LaunchArguments;
import io.meltwin.scyblaster.config.ConfigHolder;
import io.meltwin.scyblaster.config.project.ProjectConfiguration;

/**
 * Class to manage the Minecraft version descriptor file.
 */
public final class VersionDescriptor extends JSONWrapper<DTOVersion> {

    public VersionDescriptor(@NotNull ResourceFile versionDescriptor) {
        super(versionDescriptor, DTOVersion.class);
    }

    // ====================================================================
    // JSON Loader properties
    // ====================================================================
    @Override
    protected final @NotNull AdapterList getAdapters() {
        AdapterList adapters = new AdapterList();
        adapters.put(DTOArgument.class, new ArgumentAdapter());
        adapters.put(DTORule.class, new RuleAdapter());
        return adapters;
    }

    // ====================================================================
    // Global Getters
    // ====================================================================
    /**
     * Return the version name
     */
    public final @NotNull String getVersionName() {
        return this.object.id;
    }

    /**
     * Return the asset index name
     * 
     * @return
     */
    public final @NotNull String getAssetIndexName() {
        return this.object.assets;
    }

    // ====================================================================
    // ResourceFile making
    // ====================================================================
    /**
     * Make a ResourceFile for the assets index of this version
     * 
     * @param projectConfig the project configuration for getting the assets
     *                      directory
     */
    public final @NotNull ResourceFile getAssetIndexResource(@NotNull ProjectConfiguration projectConfig) {
        return new ResourceFile(this.object.assetIndex.url, ResourceType.HTTP,
                projectConfig.getAssetsPath().resolve("indexes").resolve(this.object.assetIndex.id + ".json")
                        .toString(),
                this.object.assetIndex.sha1, false);
    }

    /**
     * Make a ResourceFile for the JAR client of this version
     * 
     * @param projectConfig the project configuration for getting the assets
     *                      directory
     */
    public final @NotNull ResourceFile getClientJARResource(@NotNull ProjectConfiguration projectConfig) {
        return new ResourceFile(this.object.downloads.client.url, ResourceType.HTTP,
                projectConfig.getVersionsPath().resolve(this.object.id).resolve(this.object.id + ".jar")
                        .toString(),
                this.object.downloads.client.sha1, false);
    }

    /**
     * Make a list of ResourceFile for libs needed for this version
     * 
     * @param projectConfig the project configuration for getting the libraries
     *                      directory
     */
    public final @NotNull List<ResourceFile> getLibsFiles(@NotNull ProjectConfiguration projectConfig) {
        ArrayList<ResourceFile> libList = new ArrayList<>();

        for (DTOLibrary lib : this.object.libraries) {
            // TODO: Rule checking
            // Check if the download field exist (else it's a native)
            if (false || lib.downloads.artifact == null)
                continue;

            libList.add(new ResourceFile(lib.downloads.artifact.url, ResourceType.HTTP,
                    projectConfig.getLibsPath().resolve(lib.downloads.artifact.path).toString(),
                    lib.downloads.artifact.sha1, false));
        }

        return libList;
    }

    // ====================================================================
    // Classpath manipulation
    // ====================================================================
    /**
     * Make a new ClassPath object filled with the needed files from the vanilla
     * version
     * 
     * @param projectConfig the project configuration for getting the libraries and
     *                      version directory
     * @return the classpath object
     */
    public final @NotNull ClassPath getNewClassPath(@NotNull ProjectConfiguration projectConfig) {
        ClassPath cp = new ClassPath();
        addToClassPath(projectConfig, cp);
        return cp;
    }

    /**
     * Append the files for the vanilla version
     * 
     * @param projectConfig the project configuration for getting the libraries and
     *                      version directory
     * @param cp            the classpath object to modify
     */
    public final void addToClassPath(@NotNull ProjectConfiguration projectConfig, ClassPath cp) {
        cp.append(getClientJARResource(projectConfig).localPath.toString());
        for (ResourceFile file : getLibsFiles(projectConfig)) {
            cp.append(file.localPath.toString());
        }
    }

    // ====================================================================
    // Game arguments
    // ====================================================================
    /**
     * Make a new LaunchArguments object filled with the needed args from the
     * vanilla version
     * 
     * @param configHolder the configuration holder for the whole launcher
     * 
     * @return the launch arguments object
     */
    public final LaunchArguments getNewLaunchArguments(@NotNull ConfigHolder configHolder) {
        LaunchArguments lArgs = new LaunchArguments();
        addToLaunchArgs(configHolder, lArgs);
        return lArgs;
    }

    /**
     * Append the arguments for the vanilla version
     * 
     * @param configHolder the configuration holder for the whole launcher
     * @param launchArgs   the launch arguments object to modify
     */
    public final void addToLaunchArgs(@NotNull ConfigHolder configHolder, @NotNull LaunchArguments launchArgs) {
        // Test whether we are post F10 (~ >= MC 1.12)
        if (this.object.minecraftArguments == null)
            addLaunchArgsPostF10(configHolder, launchArgs);
        addLaunchArgsPreF10(launchArgs);
    }

    /**
     * Append the arguments for the vanilla version for descriptor format later (or
     * equal) than F10 (MC 1.12 -> Actual)
     * 
     * @see https://meltwin.github.io/Scyblaster-Data/formats/version_descriptor/
     *      for more informations on the descriptors format
     * @param launchArgs the launch arguments object to modify
     */
    private final void addLaunchArgsPreF10(@NotNull LaunchArguments launchArgs) {
        launchArgs.addArg(this.object.minecraftArguments);
    }

    /**
     * Append the arguments for the vanilla version for descriptor format strictly
     * earlier than F10 (MC Alpha -> 1.11)
     * 
     * @see https://meltwin.github.io/Scyblaster-Data/formats/version_descriptor/
     *      for more informations on the descriptors format
     * @param configHolder the configuration holder for the whole launcher
     * @param launchArgs   the launch arguments object to modify
     */
    private final void addLaunchArgsPostF10(@NotNull ConfigHolder configHolder, @NotNull LaunchArguments launchArgs) {
        // Game arguments
        for (DTOArgument arg : this.object.arguments.game) {
            // TODO: Check rules

            for (String s : arg.values)
                launchArgs.stackString(s);
        }

        // JVM
        for (DTOArgument arg : this.object.arguments.jvm) {
            // TODO: Check rules

            for (String s : arg.values)
                launchArgs.stackString(s);
        }

        launchArgs.processStringStack();
    }
}
