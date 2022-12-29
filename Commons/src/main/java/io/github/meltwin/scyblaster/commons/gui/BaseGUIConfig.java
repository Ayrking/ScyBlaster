package io.github.meltwin.scyblaster.commons.gui;


import io.github.meltwin.scyblaster.commons.io.json.JSONFile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Class wrapping GUI Configurations
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class BaseGUIConfig extends JSONFile {

    public BaseGUIConfig(final @NotNull String filepath) {
        super(filepath);
    }

    /*
     *  #########################
     *       Basics Parameters
     *  #########################
     */
    public final String title() { return this.getString("title"); }
    public final int width() { return this.getJSONObject("dimensions").getInt("width"); }
    public final int height() { return this.getJSONObject("dimensions").getInt("height"); }

    /*
     *  #########################
     *           Credits
     *  #########################
     */
    JSONObject credits() { return this.getJSONObject("credits"); }
    public final String GUIName() { return credits().getString("name"); }
    public final String GUIAuthor() { return credits().getString("author"); }
    public final int GUIDate() { return credits().getInt("date"); }
    public final String GUIVersion() { return credits().getString("version"); }

    @Override
    public final String toString() {
        return String.format("GUI %s (v%s) by %s (%d)", GUIName(), GUIVersion(), GUIAuthor(), GUIDate());
    }
}
