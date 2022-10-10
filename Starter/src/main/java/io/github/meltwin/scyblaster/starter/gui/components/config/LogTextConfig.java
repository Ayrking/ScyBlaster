package io.github.meltwin.scyblaster.starter.gui.components.config;

import io.github.meltwin.scyblaster.commons.Pair;
import io.github.meltwin.scyblaster.commons.gui.BaseComponentConfig;
import io.github.meltwin.scyblaster.commons.gui.FontConfig;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.*;

/**
 * Object containing the main LogText configuration
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class LogTextConfig extends BaseComponentConfig {
    public LogTextConfig(final @NotNull JSONObject parent, final @NotNull String index) {
        super(parent, index);
        logger.info("Loaded LogText configs");

        f_config = new FontConfig(this, "font");
    }

    /*
        =========================
            Data Verification
        =========================
     */
    protected void setDefaultParam() {
        def_param.put("x", new Pair<>(Level.ERROR,50));
        def_param.put("y", new Pair<>(Level.ERROR,140));
        def_param.put("width", new Pair<>(Level.ERROR,398));
        def_param.put("height", new Pair<>(Level.ERROR,50));
    }

    /*
        =========================
            Data Restitution
        =========================
     */
    // Bounds
    private Rectangle bounds;
    public final Rectangle getBounds() {
        if (bounds == null)
            bounds = new Rectangle(this.getInt("x"), this.getInt("y"), this.getInt("width"), this.getInt("height"));
        return bounds;
    }

    // Font
    private final FontConfig f_config;
    public final Font getFont() {return f_config.getFont();}
    public final Color getColor() {return f_config.getColor();}
}
