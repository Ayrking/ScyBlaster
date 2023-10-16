package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.jetbrains.annotations.NotNull;

class DTOResourceConfig {
    private String baseDir = ".scyblaster";
    private String assetsDir = "assets";
    private String libDir = "lib";
    private String versionsDir = "versions";
    private String nativesDir = "bin";

    @XmlAttribute(name = "base-dir", required = true)
    public void setBaseDir(String path) {
        this.baseDir = path;
    }

    @NotNull
    public String getBaseDir() {
        return this.baseDir;
    }

    @XmlElement(name = "assets", required = true)
    public void setAssetsDir(String path) {
        this.assetsDir = path;
    }

    @NotNull
    public String getAssetsDir() {
        return this.assetsDir;
    }

    @XmlElement(name = "libs", required = true)
    public void setLibsDir(String path) {
        this.libDir = path;
    }

    @NotNull
    public String getLibsDir() {
        return this.libDir;
    }

    @XmlElement(name = "versions", required = true)
    public void setVersionsDir(String path) {
        this.versionsDir = path;
    }

    @NotNull
    public String getVersionsDir() {
        return this.versionsDir;
    }

    @XmlElement(name = "natives", required = true)
    public void setNativesDir(String path) {
        this.nativesDir = path;
    }

    @NotNull
    public String getNativesDir() {
        return this.nativesDir;
    }
}
