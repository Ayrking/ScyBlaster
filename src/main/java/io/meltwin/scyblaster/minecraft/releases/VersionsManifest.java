package io.meltwin.scyblaster.minecraft.releases;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.resources.types.JSONWrapper;
import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceType;
import io.meltwin.scyblaster.config.ProjectConfiguration;
import io.meltwin.scyblaster.minecraft.releases.DTOVersionsManifest.VersionItem;

/**
 * Class to manage the Minecraft ReleasesIndex file.
 */
public final class VersionsManifest extends JSONWrapper<DTOVersionsManifest> {

    public VersionsManifest(@NotNull ResourceFile releasesIndex) {
        super(releasesIndex, DTOVersionsManifest.class);
    }

    /**
     * Return the resource file for getting the versions manifest
     * 
     * @param projectConfig the project configuration (for getting the root
     *                      directory)
     */
    public static final @NotNull ResourceFile getManifestResource(@NotNull ProjectConfiguration projectConfig) {
        return new ResourceFile(projectConfig.getVersionsManifestEndpoints(), ResourceType.HTTP,
                projectConfig.getVersionsPath().resolve("version_manifest_v2.json").toString(), true);
    }

    /**
     * Get the MC version name from the version id
     * 
     * @param versionNumber the number index of the version
     */
    public final @NotNull String getVersionName(int versionNumber) {
        return (versionNumber < this.jsonObject.versions.length && versionNumber >= 0)
                ? this.jsonObject.versions[versionNumber].id
                : "Unknown";
    }

    /**
     * Get the MC version idx from its name
     * 
     * @param versionName the name of the version
     */
    public final int getVersionID(@NotNull String versionName) {
        // If null or empty
        if (versionName != null && !versionName.isEmpty()) {
            for (int idx = 0; idx < this.jsonObject.versions.length; idx++) {
                if (this.jsonObject.versions[idx].id.equals(versionName))
                    return idx;
            }
        }
        return -1;
    }

    /**
     * Get the total number of versions
     */
    public final int getNumberOfVersions() {
        return this.jsonObject.versions.length;
    }

    /**
     * Make a ResourceFile for the given Minecraft version descriptor
     * 
     * @param projectConfig the project configuration for getting the version dir
     * @param versionIdx    the version ID
     */
    public final @Nullable ResourceFile getVersionResource(@NotNull ProjectConfiguration projectConfig,
            int versionIdx) {
        // Check if version exist
        if (versionIdx >= this.jsonObject.versions.length || versionIdx < 0)
            return null;

        VersionItem item = this.jsonObject.versions[versionIdx];

        return new ResourceFile(item.url, ResourceType.HTTP,
                projectConfig.getVersionsPath().resolve(item.id).resolve(item.id + ".json").toString(), item.sha1,
                false);
    }

    /**
     * Make a ResourceFile for the given Minecraft version descriptor
     * 
     * @param projectConfig the project configuration for getting the version dir
     * @param versionName   the version name
     */
    public final @Nullable ResourceFile getVersionResource(@NotNull ProjectConfiguration projectConfig,
            @NotNull String versionName) {
        return this.getVersionResource(projectConfig, this.getVersionID(versionName));
    }

}
