package io.github.meltwin.scyblaster.commons.gui;

import io.github.meltwin.scyblaster.commons.Pair;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;


/**
 * Configuration object of GUI components
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class BaseJSONObject extends JSONObject {

    protected final Logger logger = LogManager.getRootLogger();
    protected final String MISSING_PARAMETER;

    /*
        =========================
               Constructor
        =========================
     */
    public BaseJSONObject(final @NotNull JSONObject parent, final @NotNull String index) {
        super(readJSONObject(parent, index));

        // MSG Configurations
        MISSING_PARAMETER = String.format("Missing %s parameter", this.getClass().getSimpleName())+" %s";

        this.checkConfig();
    }

    /**
     * Read the object from the parent
     * @param parent the parent JSONObject that contain the config object
     * @param index the index in the JSONObject
     * @return the NotNull JSON String
     */
    @NotNull private static String readJSONObject(final @NotNull JSONObject parent, final @NotNull String index) {
        try {
            JSONObject obj = parent.getJSONObject(index);
            return obj.toString();
        }
        catch (JSONException e) {
            return "{}";
        }
    }

    /*
        =========================
            Data Verification
        =========================
     */
    protected final HashMap<String, Pair<Level,Object>> def_param = new HashMap<>();
    protected abstract void setDefaultParam();
    protected final void checkConfig() {
        setDefaultParam();
        for (String s : def_param.keySet()) {
            if (!this.keySet().contains(s)) {
                logger.log(def_param.get(s).getFirst(), String.format(MISSING_PARAMETER, s));
                this.put(s, def_param.get(s).getSecond());
            }
        }
    }
}
