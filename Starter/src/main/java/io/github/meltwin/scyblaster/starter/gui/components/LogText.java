package io.github.meltwin.scyblaster.starter.gui.components;

import io.github.meltwin.scyblaster.starter.gui.components.config.LogTextConfig;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Logging Text for the default gui
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class LogText extends JLabel {

    public static final String CONFIG_INDEX = "logtext";
    public final LogTextConfig conf;

    public LogText(final @NotNull LogTextConfig config) {
        super();

        this.conf = config;

        this.setBounds(this.conf.getBounds());
        this.setFont(this.conf.getFont());
        this.setForeground(this.conf.getColor());
    }
}
