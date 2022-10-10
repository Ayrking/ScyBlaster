package io.github.meltwin.scyblaster.starter.gui.components;

import io.github.meltwin.scyblaster.starter.gui.components.config.ProgressBarConfig;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * ProgressBar for the default gui
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class ProgressBar extends JProgressBar {

    public static final String CONFIG_INDEX = "progress";
    protected final ProgressBarConfig conf;

    public ProgressBar(final @NotNull ProgressBarConfig config) {
        super();

        this.conf = config;

        // Set properties
        this.setBounds(this.conf.getBounds());
    }
}
