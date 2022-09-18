package io.github.meltwin.scyblaster.starter.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Basic class for the GUI
 * Should be extended.
 * @author meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class BaseGUI<T extends GUIConfig> extends JFrame {
    protected final T conf;
    protected final Logger logger = LogManager.getLogger();

    protected BaseGUI(final @NotNull T config) {
        this.conf = config;
        logger.info(setLaunchingMessage());
        logger.trace("Making JFrame.");


        // Frame
        this.setFrame();
        this.setVisible(true);
    }

    private void setFrame() {
        logger.trace("Setting JFrame Properties.");
        this.setSize(conf.width(), conf.height());
        this.setTitle(conf.title());
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Set the launching message based on configuration "credits" section
     * @return the formatted launching message
     */
    private String setLaunchingMessage() {
        return String.format("Loading %s (%s) by %s - %d",
                conf.GUIName(),
                conf.GUIVersion(),
                conf.GUIAuthor(),
                conf.GUIDate());
    }

}
