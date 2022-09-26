package io.github.meltwin.scyblaster.starter.gui.components.config;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.*;

/**
 * Object containing the main ProgressBar configuration
 * @author meltwin
 * @since 0.1-SNAPSHOT
 */
public class ProgressBarConfig extends JSONObject {

    public ProgressBarConfig(final @NotNull String json) {
        super(json);

        this.checkConfig();
    }

    private void checkConfig() {
        assert this.keySet().contains("x");
        assert this.keySet().contains("y");
        assert this.keySet().contains("width");
        assert this.keySet().contains("height");
    }

    private Rectangle bounds;
    public final Rectangle getBounds() {
        if (bounds == null)
            bounds = new Rectangle(this.getInt("x"), this.getInt("y"), this.getInt("width"), this.getInt("height"));
        return bounds;
    }
}
