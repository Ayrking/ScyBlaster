package io.github.meltwin.scyblaster.commons;

import io.github.meltwin.scyblaster.commons.io.file.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Project configuration
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class ProjectConfiguration {
    private ProjectConfiguration() {}

    public static @NotNull String getProjectName() {return PROJECT_NAME;}
    public static void setProjectName(@NotNull final String name) {PROJECT_NAME = name;}

    public static @NotNull String getProjectVersion() {return PROJECT_VERSION;}
    public static void setProjectVersion(@NotNull final String version) {PROJECT_VERSION = version;}

    public static @NotNull String getProjectLocalDir() {return FileUtils.getUserDir();}
    public static void setProjectLocalDir(@NotNull final String local) {FileUtils.setUserDir(local);}

    public static @Nullable String getProjectResDir() {return PROJECT_RES_DIR;}
    public static void setProjectResDir(@Nullable final String res_dir) {PROJECT_RES_DIR = res_dir+"/";}

    public static @NotNull String getProjectDefaultResDir() {return PROJECT_DEFAULT_RES_DIR;}

    static @NotNull String PROJECT_NAME = "Scyblaster";
    static @NotNull String PROJECT_VERSION = "0.0.1";
    static @Nullable String PROJECT_RES_DIR;
    static @NotNull String PROJECT_DEFAULT_RES_DIR = "default/";

}
