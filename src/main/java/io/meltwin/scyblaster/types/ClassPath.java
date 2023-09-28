package io.meltwin.scyblaster.types;

import org.jetbrains.annotations.NotNull;

public class ClassPath {
    private static final String PATH_SEPARATOR = ":";
    private StringBuilder builder = new StringBuilder();

    public void append(@NotNull String str) {
        builder.append(str + PATH_SEPARATOR);
    }

    public void prepend(@NotNull String str) {
        builder = builder.reverse().append(new StringBuilder(str + PATH_SEPARATOR).reverse()).reverse();
    }

    public String getString() {
        return builder.reverse().deleteCharAt(0).reverse().toString();
    }
}