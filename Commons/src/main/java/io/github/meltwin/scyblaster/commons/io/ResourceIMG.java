package io.github.meltwin.scyblaster.commons.io;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Methods for resource images interactions
 * @author meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class ResourceIMG {
    /**
     * Make a BufferedImage from a resource image
     * @param filepath the path to the image (ex: custom/image.png)
     * @throws IOException if something wrong happened during opening
     * @throws IllegalArgumentException if the file doesn't exist
     */
    public static BufferedImage getResourceIMG(@NotNull String filepath) throws IOException, IllegalArgumentException {
        InputStream img = ResourceIMG.class.getClassLoader().getResourceAsStream(filepath);
        return ImageIO.read(Objects.requireNonNull(img));
    }
}