package io.github.meltwin.scyblaster.commons.gui;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Basic class for the GUI
 * Should be extended.
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class BaseGUI<T extends BaseGUIConfig> extends JFrame {
    /*
        =========================
                 MEMBERS
        =========================
     */
    protected final T config;
    protected final Logger logger = LogManager.getLogger();

    /*
        =========================
                 MESSAGES
        =========================
     */
    /**
     * Set the launching message based on configuration "credits" section
     * @return the formatted launching message
     */
    private String setLaunchingMessage() {
        return String.format("Loading %s (%s) by %s - %d",
                config.GUIName(),
                config.GUIVersion(),
                config.GUIAuthor(),
                config.GUIDate());
    }
    static final String MSG_STARTUP = "Making JFrame.";
    static final String MSG_SETTINGS = "Setting JFrame Properties.";

    /*
        =========================
                 METHODS
        =========================
     */
    protected BaseGUI(final @NotNull T config) {
        this.config = config;
        logger.info(setLaunchingMessage());
        logger.trace(MSG_STARTUP);

        // Frame
        this.setFrame();
        this.setVisible(true);
    }

    private void setFrame() {
        logger.trace(MSG_SETTINGS);
        this.setSize(config.width(), config.height());
        this.setTitle(config.title());
        //this.setResizable(false);
        //this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
