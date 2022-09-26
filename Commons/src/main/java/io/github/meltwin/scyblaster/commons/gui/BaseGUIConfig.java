package io.github.meltwin.scyblaster.commons.gui;


import io.github.meltwin.scyblaster.commons.io.JSONFile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Class wrapping GUI Configurations
 * @<code>Copyright: GNU APGLv3 - (C) 2022 Meltwin</code>
 * @author meltwin
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
    public final JSONObject credits() { return this.getJSONObject("credits"); }
    public final String GUIName() { return credits().getString("name"); }
    public final String GUIAuthor() { return credits().getString("author"); }
    public final int GUIDate() { return credits().getInt("date"); }
    public final String GUIVersion() { return credits().getString("version"); }
}
