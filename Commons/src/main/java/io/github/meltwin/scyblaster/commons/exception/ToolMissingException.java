package io.github.meltwin.scyblaster.commons.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception for when a tool is missing
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class ToolMissingException extends BaseException {

    final String TOOL_CLASS;

    public ToolMissingException(@NotNull String class_path) {
        TOOL_CLASS = class_path;
    }

    @Override
    public String getMessage() {
        return String.format("\nTool %s missing.\nPlease contact a developer of the launcher.", TOOL_CLASS);
    }
}
