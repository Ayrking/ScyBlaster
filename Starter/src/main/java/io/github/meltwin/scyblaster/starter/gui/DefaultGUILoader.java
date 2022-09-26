package io.github.meltwin.scyblaster.starter.gui;

import io.github.meltwin.scyblaster.commons.gui.GUILoader;
import io.github.meltwin.scyblaster.starter.gui.basic.DefaultGUI;
import io.github.meltwin.scyblaster.starter.gui.basic.DefaultGUIConfig;

/**
 * Loader for Default Starter GUI
 * @<code>Copyright: GNU APGLv3 - (C) 2022 Meltwin</code>
 * @author meltwin
 * @since 0.1-SNAPSHOT
 */
public final class DefaultGUILoader extends GUILoader {

    private final DefaultGUIConfig DEFAULT_GUI_CONFIG = new DefaultGUIConfig(DEFAULT_GUI_FILE);

    @Override
    protected void makeCustomGUI() {
        DefaultGUIConfig conf = new DefaultGUIConfig(CUSTOM_GUI_FILE);
        new DefaultGUI(conf);
    }
    @Override
    protected void makeDefaultGUI() { new DefaultGUI(DEFAULT_GUI_CONFIG); }
}
