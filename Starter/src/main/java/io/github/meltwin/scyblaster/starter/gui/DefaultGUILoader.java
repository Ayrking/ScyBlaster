package io.github.meltwin.scyblaster.starter.gui;

import io.github.meltwin.scyblaster.commons.gui.BaseGUIConfig;
import io.github.meltwin.scyblaster.commons.gui.GUILoader;
import io.github.meltwin.scyblaster.starter.gui.basic.DefaultGUI;
import io.github.meltwin.scyblaster.starter.gui.basic.DefaultGUIConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Loader for Default Starter GUI
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public final class DefaultGUILoader extends GUILoader<DefaultGUIConfig, DefaultGUIConfig> {

    @Override
    protected DefaultGUIConfig makeDefaultConfig(@NotNull String file) {
        return new DefaultGUIConfig(file);
    }

    @Override
    protected DefaultGUIConfig makeCustomConfig(@NotNull String file) {
        return new DefaultGUIConfig(file);
    }

    @Override
    protected void makeDefaultGUI(@NotNull DefaultGUIConfig baseGUIConfig) {
    new DefaultGUI(baseGUIConfig);
    }

    @Override
    protected void makeCustomGUI(@NotNull DefaultGUIConfig baseGUIConfig) {
        new DefaultGUI(baseGUIConfig);
    }
}
