package io.github.meltwin.scyblaster.starter.gui.basic;

import io.github.meltwin.scyblaster.commons.gui.BaseGUI;
import io.github.meltwin.scyblaster.commons.io.ResourceIMG;

import io.github.meltwin.scyblaster.starter.Starter;
import io.github.meltwin.scyblaster.starter.event.ProgressBarMaxEvent;
import io.github.meltwin.scyblaster.starter.event.ProgressBarValueEvent;
import io.github.meltwin.scyblaster.starter.event.TextEvent;
import io.github.meltwin.scyblaster.starter.event.listener.GUIEventListener;
import io.github.meltwin.scyblaster.starter.gui.components.LogText;
import io.github.meltwin.scyblaster.starter.gui.components.ProgressBar;
import io.github.meltwin.scyblaster.starter.gui.components.config.LogTextConfig;
import io.github.meltwin.scyblaster.starter.gui.components.config.ProgressBarConfig;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The Default GUI of the starter
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class DefaultGUI extends BaseGUI<DefaultGUIConfig> implements GUIEventListener {

    protected final JLayeredPane panel;
    protected final JLabel background;
    protected final JLabel text;
    protected final JProgressBar progress;

    public DefaultGUI(final @NotNull DefaultGUIConfig config) {
        super(config);
        this.setBackground(Color.DARK_GRAY);

        // GUI Making
        panel = new JLayeredPane();
        //panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        background = setBackground();
        progress = setProgressBar();
        text = setText();

        // Event Listening
        this.register();
        this.add(panel);
        this.revalidate();
        this.repaint();
        panel.setVisible(true);
    }

    /*
     *  #########################
     *      Components Making
     *           methods
     *  #########################
     */
    private @NotNull JLabel setBackground() {
        JLabel back = new JLabel(new ImageIcon(this.getBackgroundIMG()));
        back.setBounds(0, 0, this.config.width(), this.config.height());
        panel.add(back, 0, 0);
        return back;
    }
    private @NotNull ProgressBar setProgressBar() {
        ProgressBar pro = new ProgressBar(new ProgressBarConfig(config.getJSONObject("comp"), ProgressBar.CONFIG_INDEX));
        pro.setValue(0);
        pro.setMaximum(100);
        pro.setMinimum(0);
        panel.add(pro, 1, 1);
        return pro;
    }
    private @NotNull JLabel setText() {
        LogText text = new LogText(new LogTextConfig(config.getJSONObject("comp"), LogText.CONFIG_INDEX));
        text.setText("Default value");
        panel.add(text, 2, 2);
        return text;
    }

    /*
     *  #########################
     *      Background related
     *           methods
     *  #########################
     */

    /**
     * Load background image from filepath
     * @param filepath the path to the image (ex: custom/background.png)
     * @return the image in a BufferedImage
     */
    private BufferedImage getBackgroundIMG(final @NotNull String filepath) {
        BufferedImage img;
        try {
            img = ResourceIMG.getResourceIMG(filepath);
        } catch (IOException e) {
            Starter.log.warn("Error during custom background image loading !");
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            // If problem during custom load, load default background
            Starter.log.warn("The background image path in custom/GUI_config.json doesn't exit !\n\tLoading default background...");
            img = getBackgroundIMG(config.getBackground());
        }

        return img;
    }

    /**
     * Load background image from the configuration file
     * @return the image in a BufferedImage
     */
    private BufferedImage getBackgroundIMG() { return getBackgroundIMG(config.getBackground()); }

    /*
     *  #########################
     *        Event Handling
     *           methods
     *  #########################
     */
    @Override public void onTextEvent(@NotNull TextEvent event) { text.setText(event.getText()); }
    @Override public void onProgressValueEvent(final @NotNull ProgressBarValueEvent event) { progress.setValue(event.getValue()); }
    @Override public void onProgressMaxEvent(final @NotNull ProgressBarMaxEvent event) { progress.setMaximum(event.getValue()); }
}
