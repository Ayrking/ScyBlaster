package io.github.meltwin.scyblaster.commons.gui;

import io.github.meltwin.scyblaster.commons.Pair;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.Font;

/**
 * Configuration object of Font
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class FontConfig extends BaseComponentConfig {
    public FontConfig(final @NotNull JSONObject parent, final @NotNull String index) {
        super(parent, index);
        logger.info("Loaded Font configs");
    }

    /*
        =========================
            Data Verification
        =========================
     */
    @Override
    protected void setDefaultParam() {
        def_param.put("font", new Pair<>(Level.TRACE, Font.MONOSPACED));
        def_param.put("size", new Pair<>(Level.TRACE, 12));
    }

    /*
        =========================
            Data Restitution
        =========================
     */
    public final Font getFont() {
        return new Font(this.getString("font"), Font.PLAIN, this.getInt("size"));
    }
}
