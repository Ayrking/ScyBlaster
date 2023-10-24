package io.meltwin.scyblaster.minecraft.version;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import io.meltwin.scyblaster.minecraft.version.DTORule.OS;

public class RuleAdapter implements JsonDeserializer<DTORule> {

    @Override
    public DTORule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        // Action extraction
        DTORule out = null;
        {
            String action = null;
            if (obj.has("action"))
                action = obj.get("action").getAsString();

            // OS Extraction
            OS os = null;
            if (obj.has("os")) {
                Gson gson = new Gson();
                os = gson.fromJson(obj.get("os").getAsJsonObject(), OS.class);
            }

            out = new DTORule(action, os);
        }

        // Features extraction
        if (obj.has("features")) {
            JsonObject features = obj.get("features").getAsJsonObject();

            for (Entry<String, JsonElement> entry : features.entrySet())
                out.features.put(entry.getKey(), entry.getValue().getAsString());
        }

        return out;
    }

}
