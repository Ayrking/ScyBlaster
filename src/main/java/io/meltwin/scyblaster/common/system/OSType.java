package io.meltwin.scyblaster.common.system;

import org.jetbrains.annotations.NotNull;

public enum OSType {
    WINDOWS("windows"),
    LINUX("linux"),
    MACOS("osx"),
    SOLARIS("sun"),
    UNKNOWN("unknown");

    private final @NotNull String name;

    private OSType(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String os() {
        return this.name;
    }

}
