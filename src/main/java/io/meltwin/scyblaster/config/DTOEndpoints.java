package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jetbrains.annotations.NotNull;

@XmlRootElement(name = "endpoints")
public class DTOEndpoints {

    private String assetRootEndpoint = "https://resources.download.minecraft.net/";
    private String versionManifestEndpoint = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";

    // ====================================================================
    // Assets endpoint
    // ====================================================================
    @XmlElement(name = "versions")
    public void setVersionsManifestEndpoint(String endpoint) {
        this.versionManifestEndpoint = endpoint;
    }

    public @NotNull String getVersionsManifestEndpoint() {
        return this.versionManifestEndpoint;
    }

    // ====================================================================
    // Assets endpoint
    // ====================================================================
    @XmlElement(name = "assets")
    public void setAssetRootEndpoint(String endpoint) {
        this.assetRootEndpoint = endpoint;
    }

    public @NotNull String getAssetRootEndpoint() {
        return this.assetRootEndpoint;
    }

}
