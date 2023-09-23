package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

public class ResourceConfig {
    private String baseDir = "";

    @XmlAttribute(name = "base-dir")
    public void setBaseDir(String path) {
        this.baseDir = path;
    }

    @NotNull
    public String getBaseDir() {
        return this.baseDir;
    }
}
