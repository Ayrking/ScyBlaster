package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.Pair;

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

    @XmlAttribute(name = "version")
    @XmlJavaTypeAdapter(VersionAdapter.class)
    private Pair<Integer, Integer> versions = new Pair<>(0, 0);

    @NotNull
    public Pair<Integer, Integer> getVersion() {
        return versions;
    }
}