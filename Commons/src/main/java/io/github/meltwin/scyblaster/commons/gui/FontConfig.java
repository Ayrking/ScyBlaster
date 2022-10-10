package io.github.meltwin.scyblaster.commons.gui;

import io.github.meltwin.scyblaster.commons.Pair;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.*;

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
        def_param.put("color", new Pair<>(Level.TRACE, "#aaaaaa"));
    }

    /*
        =========================
            Data Restitution
        =========================
     */
    public final Font getFont() {
        return new Font(this.getString("font"), Font.PLAIN, this.getInt("size"));
    }
    public final Color getColor() {
        String color = this.getString("color");
        if (color.length() != 7) {
            logger.error(String.format("Color format %s incorrect, please use \"#RRGGBB\"", color));
            color = (String) def_param.get("color").getSecond();
            this.put("color", color);
        }

        int r, g, b;
        r = Integer.decode("0x"+color.substring(1, 3));
        g = Integer.decode("0x"+color.substring(3, 5));
        b = Integer.decode("0x"+color.substring(5, 7));
        float[] hsb = Color.RGBtoHSB(r,g,b, null);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
}
