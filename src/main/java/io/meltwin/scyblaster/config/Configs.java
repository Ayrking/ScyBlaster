package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.config.dto.Minecraft;
import io.meltwin.scyblaster.config.dto.ResourceConfig;

@XmlRootElement(name = "project")
public class Configs {

    private ResourceConfig resources = new ResourceConfig();
    private Minecraft minecraft = new Minecraft();

    @XmlElement(name = "resources", required = true)
    public void setResourcesConfig(ResourceConfig config) {
        this.resources = config;
    }

    @NotNull
    public ResourceConfig getResourceConfig() {
        return resources;
    }

    @XmlElement(name = "minecraft", required = true)
    public void setMinecraftConfig(Minecraft mc) {
        this.minecraft = mc;
    }

    @NotNull
    public Minecraft getMinecraftConfigs() {
        return this.minecraft;
    }
}
