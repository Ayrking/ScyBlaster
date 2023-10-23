package io.meltwin.scyblaster.minecraft.version;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.resources.dto.JSONWrapper;
import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceType;
import io.meltwin.scyblaster.common.types.ClassPath;
import io.meltwin.scyblaster.config.project.ProjectConfiguration;

/**
 * Class to manage the Minecraft version descriptor file.
 */
public final class VersionDescriptor extends JSONWrapper<DTOVersion> {

    public VersionDescriptor(@NotNull ResourceFile versionDescriptor) {
        super(versionDescriptor, DTOVersion.class);
    }

    @Override
    protected final @NotNull AdapterList getAdapters() {
        AdapterList adapters = new AdapterList();
        adapters.put(DTOArgument.class, new ArgumentAdapter());
        adapters.put(DTORule.class, new RuleAdapter());
        return adapters;
    }

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

    /**
     * Make the classpath needed for launching the Minecraft client
     * 
     * @param projectConfig the project configuration for getting the libraries and
     *                      version directory
     * @return the classpath under a string form
     */
    public final @NotNull String getClassPath(@NotNull ProjectConfiguration projectConfig) {
        ClassPath cp = new ClassPath();
        cp.append(getClientJARResource(projectConfig).localPath.toString());
        for (ResourceFile file : getLibsFiles(projectConfig)) {
            cp.append(file.localPath.toString());
        }
        return cp.getString();
    }

}
