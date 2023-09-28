package io.meltwin.scyblaster.config.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.Pair;
import io.meltwin.scyblaster.config.adapter.VersionAdapter;
import io.meltwin.scyblaster.config.adapter.VersionType;

public class Minecraft {
    /*
     * ========================================================================
     * Version Type
     * ========================================================================
     */
    private VersionType type = VersionType.SINGLE;

    @XmlAttribute(name = "type", required = true)
    public void setType(String type) {
        this.type = VersionType.fromValue(type);
    }

    @NotNull
    public VersionType getType() {
        return type;
    }

    /*
     * ========================================================================
     * Version Range
     * ========================================================================
     */
    private Pair<Integer, Integer> versions = new Pair<>(0, 0);

    @XmlAttribute(name = "version")
    @XmlJavaTypeAdapter(VersionAdapter.class)
    public void setVersions(Pair<Integer, Integer> versions) {
        this.versions = versions;
    }

    @NotNull
    public Pair<Integer, Integer> getVersion() {
        return versions;
    }

    /*
     * ========================================================================
     * Demo
     * ========================================================================
     */

    private boolean _isDemo = false;

    @XmlElement(name = "isDemo")
    public void setDemo(boolean _isDemo) {
        this._isDemo = _isDemo;
    }

    public boolean isDemo() {
        return this._isDemo;
    }

    /*
     * ========================================================================
     * QuickPlay (Multiplayer)
     * ========================================================================
     */
    private String _quickplayM = null;

    @XmlElement(name = "quickplay-online")
    public void setQuickPlayM(String _quickplay) {
        this._quickplayM = _quickplay;
    }

    public String getQuickPlayM() {
        return this._quickplayM;
    }

    /*
     * ========================================================================
     * QuickPlay (Singleplayer)
     * ========================================================================
     */
    private String _quickplayS = null;

    @XmlElement(name = "quickplay-single")
    public void setQuickPlayS(String _quickplayS) {
        this._quickplayS = _quickplayS;
    }

    public String getQuickPlayS() {
        return this._quickplayS;
    }

    /*
     * ========================================================================
     * QuickPlay (Realms)
     * ========================================================================
     */
    private String _quickplayR = null;

    @XmlElement(name = "quickplay-realms")
    public void setQuickPlayR(String _quickplayR) {
        this._quickplayR = _quickplayR;
    }

    public String getQuickPlayR() {
        return this._quickplayR;
    }
}