package io.meltwin.scyblaster.config.project;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.types.Pair;

class DTOMinecraft {

    // ========================================================================
    // Version Type
    // ========================================================================
    private VersionType versionType = VersionType.ALL;

    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(VersionTypeAdapter.class)
    public void setType(VersionType type) {
        this.versionType = type;
    }

    @NotNull
    public VersionType getType() {
        return versionType;
    }

    // ========================================================================
    // Online authentification
    // ========================================================================
    private boolean isOnline = true;

    @XmlAttribute(name = "online", required = true)
    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    @NotNull
    public boolean isOnlineGame() {
        return this.isOnline;
    }

    // ========================================================================
    // Version Range
    // ========================================================================
    private Pair<String, String> versions = new Pair<>("", "");

    @XmlAttribute(name = "version")
    @XmlJavaTypeAdapter(VersionAdapter.class)
    public void setVersions(Pair<String, String> versions) {
        this.versions = versions;
    }

    @NotNull
    public Pair<String, String> getVersion() {
        return versions;
    }

    // ========================================================================
    // Demo
    // ========================================================================
    private boolean isDemo = false;

    @XmlElement(name = "isDemo")
    public void setDemo(boolean isDemo) {
        this.isDemo = isDemo;
    }

    public boolean isDemo() {
        return this.isDemo;
    }

    // ========================================================================
    // QuickPlay (Multiplayer)
    // ========================================================================
    private String quickplayM = null;

    @XmlElement(name = "quickplay-online")
    public void setQuickPlayM(String quickplay) {
        this.quickplayM = quickplay;
    }

    public String getQuickPlayM() {
        return this.quickplayM;
    }

    // ========================================================================
    // QuickPlay (Singleplayer)
    // ========================================================================
    private String quickplayS = null;

    @XmlElement(name = "quickplay-single")
    public void setQuickPlayS(String quickplayS) {
        this.quickplayS = quickplayS;
    }

    public String getQuickPlayS() {
        return this.quickplayS;
    }

    // ========================================================================
    // QuickPlay (Realms)
    // ========================================================================
    private String quickplayR = null;

    @XmlElement(name = "quickplay-realms")
    public void setQuickPlayR(String quickplayR) {
        this.quickplayR = quickplayR;
    }

    public String getQuickPlayR() {
        return this.quickplayR;
    }
}