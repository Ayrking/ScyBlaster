package io.meltwin.scyblaster.config.project;

public enum VersionType {
    SINGLE,
    RANGE,
    ALL;

    public String value() {
        return name();
    }

    public static VersionType fromValue(String v) {
        return valueOf(v);
    }
}
