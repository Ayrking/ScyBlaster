package io.github.meltwin.scyblaster.starter.gui.basic;

import io.github.meltwin.scyblaster.commons.gui.BaseGUI;
import io.github.meltwin.scyblaster.commons.io.ResourceIMG;

import io.github.meltwin.scyblaster.starter.Starter;
import io.github.meltwin.scyblaster.starter.gui.components.ProgressBar;
import io.github.meltwin.scyblaster.starter.gui.components.config.ProgressBarConfig;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The Default GUI of the starter
 * @author meltwin
 * @since 0.1-SNAPSHOT
 */
public class DefaultGUI extends BaseGUI<DefaultGUIConfig> {

    protected final JLabel background;
    protected final JProgressBar progress;

    public DefaultGUI(final @NotNull DefaultGUIConfig config) {
        super(config);
        this.setBackground(Color.CYAN);

        JLayeredPane panel = new JLayeredPane();
        //panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Adding new components
        background = new JLabel(new ImageIcon(this.getBackgroundIMG()));
        background.setBounds(0, 0, 500, 350);
        panel.add(background, 0, 0);

        progress = new ProgressBar(new ProgressBarConfig(config.getJSONObject("comp").getJSONObject("progress").toString()));
        progress.setValue(50);
        panel.add(progress, 1, 1);

        panel.setVisible(true);
        this.add(panel);
        this.revalidate();
        this.repaint();
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
    private BufferedImage getBackgroundIMG() {
        return getBackgroundIMG(config.getBackground());
    }
}
