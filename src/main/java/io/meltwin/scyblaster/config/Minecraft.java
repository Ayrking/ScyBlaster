package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.Pair;

public class Minecraft {
    @XmlAttribute(name = "type", required = true)
    private VersionType type = VersionType.SINGLE;

    @XmlAttribute(name = "version")
    @XmlJavaTypeAdapter(VersionAdapter.class)
    private Pair<Integer, Integer> versions = new Pair<>(0, 0);

    @NotNull
    public VersionType getType() {
        return type;
    }

    @NotNull
    public Pair<Integer, Integer> getVersion() {
        return versions;
    }
}