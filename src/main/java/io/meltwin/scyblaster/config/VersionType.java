package io.meltwin.scyblaster.config;

public enum VersionType {
    SINGLE,
    RANGE,
    USER_CHOICE;

    public String value() {
        return name();
    }

    public static VersionType fromValue(String v) {
        return valueOf(v);
    }
}
