package io.meltwin.scyblaster.config.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.Pair;
import io.meltwin.scyblaster.config.adapter.VersionAdapter;
import io.meltwin.scyblaster.config.adapter.VersionType;

public class Minecraft {

    private VersionType type = VersionType.SINGLE;

    @XmlAttribute(name = "type", required = true)
    public void setType(String type) {
        this.type = VersionType.fromValue(type);
    }

    @NotNull
    public VersionType getType() {
        return type;
    }

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
}