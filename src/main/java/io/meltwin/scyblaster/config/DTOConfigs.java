package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jetbrains.annotations.NotNull;

@XmlRootElement(name = "project")
class DTOConfigs {

    private DTOResourceConfig resources = new DTOResourceConfig();
    private DTOMinecraft minecraft = new DTOMinecraft();
    private DTOEndpoints endpoints = new DTOEndpoints();

    @XmlElement(name = "resources", required = true)
    public void setResourcesConfig(DTOResourceConfig config) {
        this.resources = config;
    }

    @NotNull
    public DTOResourceConfig getResourceConfig() {
        return resources;
    }

    @XmlElement(name = "minecraft", required = true)
    public void setMinecraftConfig(DTOMinecraft mc) {
        this.minecraft = mc;
    }

    @NotNull
    public DTOMinecraft getMinecraftConfigs() {
        return this.minecraft;
    }

    @XmlElement(name = "endpoints", required = true)
    public void setEndpointsConfig(DTOEndpoints config) {
        this.endpoints = config;
    }

    @NotNull
    public DTOEndpoints getEndpointsConfig() {
        return endpoints;
    }
}
