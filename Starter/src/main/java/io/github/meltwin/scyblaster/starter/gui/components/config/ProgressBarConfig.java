package io.github.meltwin.scyblaster.starter.gui.components.config;

import io.github.meltwin.scyblaster.commons.Pair;
import io.github.meltwin.scyblaster.commons.gui.BaseJSONObject;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.*;

/**
 * Object containing the main ProgressBar configuration
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class ProgressBarConfig extends BaseJSONObject {

    public ProgressBarConfig(final @NotNull JSONObject parent, final @NotNull String index) {
        super(parent, index);
        logger.info("Loading ProgressBar configs");
    }

    /*
        =========================
            Data Verification
        =========================
     */
    protected void setDefaultParam() {
        def_param.put("x", new Pair<>(Level.ERROR, 46));
        def_param.put("y", new Pair<>(Level.ERROR, 234));
        def_param.put("width", new Pair<>(Level.ERROR, 406));
        def_param.put("height", new Pair<>(Level.ERROR, 50));
    }

    /*
        =========================
            Data Restitution
        =========================
     */
    private Rectangle bounds;
    public final Rectangle getBounds() {
        if (bounds == null)
            bounds = new Rectangle(this.getInt("x"), this.getInt("y"), this.getInt("width"), this.getInt("height"));
        return bounds;
    }
}
