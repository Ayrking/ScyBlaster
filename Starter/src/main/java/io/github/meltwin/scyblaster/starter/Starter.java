package io.github.meltwin.scyblaster.starter;

import io.github.meltwin.scyblaster.starter.gui.DefaultGUILoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Starter App
 * @<code>Copyright: GNU APGLv3 - (C) 2022 Meltwin</code>
 * @author meltwin
 * @since 0.1-SNAPSHOT
 */
public class Starter {
    private final DefaultGUILoader gui_loader = new DefaultGUILoader();
    static public final Logger log = LogManager.getRootLogger();

    public Starter() {
        log.info("Starting Scyblaster 0.1-SNAPSHOT");
        getGuiLoader().makeGUI();
    }

    public DefaultGUILoader getGuiLoader() { return gui_loader; }

    // Starting function
    public static void main(String[] args) {
        new Starter();
    }

}
