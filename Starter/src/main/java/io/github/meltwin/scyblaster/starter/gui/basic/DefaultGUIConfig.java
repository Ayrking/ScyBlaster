package io.github.meltwin.scyblaster.starter.gui.basic;

import io.github.meltwin.scyblaster.commons.gui.BaseGUIConfig;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Extension of GUIConfig for the Default GUI
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class DefaultGUIConfig extends BaseGUIConfig {

    public DefaultGUIConfig(final @NotNull String filepath) {
        super(filepath);
    }
    /*
     *  #########################
     *       Default related
     *           object
     *  #########################
     */
    private JSONObject background;

    /**
     * Give the background configs. Create an instance if it doesn't exist.
     * @return the config in the shape of a JSONObject
     */
    private JSONObject getBackgroundConfig() {
        if (background == null) background = this.getJSONObject("background");
        return background;
    }

    /*
     *  #########################
     *       Default related
     *          properties
     *  #########################
     */

    /**
     * @return the path to the background image
     */
    public String getBackground() { return this.getBackgroundConfig().getString("img"); }
}
