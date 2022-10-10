package io.github.meltwin.scyblaster.starter.http;

import io.github.meltwin.scyblaster.commons.Pair;
import io.github.meltwin.scyblaster.commons.gui.BaseJSONObject;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * HTTP Request config
 *  @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 *  @author Meltwin
 *  @since 0.1-SNAPSHOT
 */
public class SyncConfig extends BaseJSONObject {

    public static final String SYNC_INDEX = "sync";

    /*
        =========================
               JSON Config
        =========================
     */
    public SyncConfig(final @NotNull JSONObject parent, final @NotNull String index) {
        super(parent, index);
    }

    @Override
    protected void setDefaultParam() {
        def_param.put("href", new Pair<>(Level.ERROR, "http://localhost/Scyblaster/latest_launcher.json"));
        def_param.put("type", new Pair<>(Level.ERROR, "HTTP"));
    }

    private URL dist_file = null;

    /**
     * Return a URL pointing to the last_launcher.json file
     * @return the URL, or null if an error occurred
     */
    @Nullable
    public URL getDistFile() {
        try {
            if (dist_file==null)
                dist_file = new URL(this.getString("href"));
        }
        catch (MalformedURLException e) {
            logger.fatal("Error during making URL");
            e.printStackTrace();
        }
        return dist_file;
    }
}
