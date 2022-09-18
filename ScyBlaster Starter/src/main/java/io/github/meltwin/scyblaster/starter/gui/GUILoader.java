package io.github.meltwin.scyblaster.starter.gui;

import io.github.meltwin.scyblaster.starter.gui.base.DefaultGUI;
import io.github.meltwin.scyblaster.starter.gui.base.DefaultGUIConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * Loader for GUI configurations
 * @author meltwin
 * @since 0.1-SNAPSHOT
 */
public final class GUILoader {

    private boolean CUSTOM = false;
    private final Logger logger = LogManager.getLogger();

    public GUILoader() {
        logger.info("Loading GUI !");
        this.checkCustomFiles();
    }


    /**
     * Check for custom configuration files.
     */
    private void checkCustomFiles() {
        logger.info("Check GUI configuration files.");
        URL custom_conf = getClass().getClassLoader().getResource("custom/GUI_config.json");
        CUSTOM = (custom_conf != null);
    }

    /**
     * Start the making of the GUI.
     */
    public void makeGUI() {
        if (CUSTOM) {
            logger.info("Found custom GUI. Proceeding to the loading !");
            DefaultGUIConfig conf = new DefaultGUIConfig("custom/GUI_config.json");
            new DefaultGUI(conf);
        }
        else {
            logger.info("Continuing default GUI.");
            new DefaultGUI(GUILoader.DEFAULT_GUI_CONFIG);
        }
    }

    /*
     *  #########################
     *           Statics
     *           methods
     *  #########################
     */
    private static final DefaultGUIConfig DEFAULT_GUI_CONFIG = new DefaultGUIConfig("base/GUI_config.json");

    /**
     * @return The default configuration file for the GUI.
     */
    @NotNull
    public static DefaultGUIConfig getDefaultGuiConfig() {
        return DEFAULT_GUI_CONFIG;
    }
}
