package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jetbrains.annotations.NotNull;

@XmlRootElement(name = "project")
public class Configs {

    private ResourceConfig resources;

    @XmlElement(name = "resources")
    public void setResourcesConfig(ResourceConfig config) {
        this.resources = config;
    }

    @NotNull
    public ResourceConfig getResourceConfig() {
        return resources;
    }
}
