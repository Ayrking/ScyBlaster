package io.github.meltwin.scyblaster.commons.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

/**
 * Loader for GUI configurations
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class GUILoader {
    /*
        =========================
                 MEMBERS
        =========================
     */
    protected boolean CUSTOM = false;
    protected final Logger logger = LogManager.getLogger();

    protected static final String DEFAULT_GUI_FILE = "default/GUI_config.json";
    protected static final String CUSTOM_GUI_FILE = "custom/GUI_config.json";

    protected BaseGUI gui;

    /*
        =========================
                 MESSAGES
        =========================
     */
    static final String MSG_STARTING = "Loading GUI !";
    static final String MSG_CHECKING = "Check GUI configuration files.";

    static final String MSG_MAKING_CUSTOM = "Found custom GUI. Proceeding to the loading !";
    static final String MSG_MAKING_DEFAULT = "Continuing default GUI.";

    /*
        =========================
                 METHODS
        =========================
     */

    protected GUILoader() {
        logger.info(MSG_STARTING);
        this.checkCustomFiles();
    }

    /**
     * Check for custom configuration files.
     */
    private void checkCustomFiles() {
        logger.info(MSG_CHECKING);
        URL custom_conf = getClass().getClassLoader().getResource(CUSTOM_GUI_FILE);
        CUSTOM = (custom_conf != null);
    }

    /**
     * Start the making of the GUI.
     */
    public final void makeGUI() {
        if (CUSTOM) {
            logger.info(MSG_MAKING_CUSTOM);
            this.makeCustomGUI();
        }
        else {
            logger.info(MSG_MAKING_DEFAULT);
            this.makeDefaultGUI();
        }
    }

    /**
     * Launch the making of the custom GUI.
     */
    protected abstract void makeDefaultGUI();

    /**
     * Launch the making of the default GUI.
     */
    protected abstract void makeCustomGUI();

}
