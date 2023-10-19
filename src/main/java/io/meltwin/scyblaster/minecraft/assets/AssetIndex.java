package io.meltwin.scyblaster.minecraft.assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.resources.dto.JSONWrapper;
import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceType;
import io.meltwin.scyblaster.config.ProjectConfiguration;

/**
 * Class to manage the Minecraft AssetIndex file.
 */
public final class AssetIndex extends JSONWrapper<DTOAssetsIndex> {

    public AssetIndex(@NotNull ResourceFile assetIndex) {
        super(assetIndex, DTOAssetsIndex.class);
    }

    /**
     * Make a ResourceFile object for the given resource hash
     * 
     * @param projectConfig the project configuration
     * @param hash          the hash of the resource
     * @return a ResourceFile object for manipulating it
     */
    public static final @NotNull ResourceFile makeAssetFile(@NotNull ProjectConfiguration projectConfig,
            @NotNull String hash) {
        return new ResourceFile(projectConfig.getAssetsRootEndpoints() + hash.substring(0, 2) + "/" + hash,
                ResourceType.HTTP,
                projectConfig.getAssetsPath().resolve("objects").resolve(hash.substring(0, 2)).resolve(hash)
                        .toAbsolutePath().toString(),
                hash,
                false);
    }

    /**
     * Return a list of all the assets to be checked for this version
     * 
     * @param projectConfig the project configuration
     * @return a list of all the assets to check or download
     */
    public final @NotNull List<ResourceFile> getAssetsList(@NotNull ProjectConfiguration projectConfig) {
        ArrayList<ResourceFile> resourceList = new ArrayList<>();

        for (Entry<String, DTOAssetsIndex.Asset> entry : object.objects.entrySet())
            resourceList.add(makeAssetFile(projectConfig, entry.getValue().hash));

        return resourceList;
    }

}
