package io.meltwin.scyblaster.minecraft.adapter;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import io.meltwin.scyblaster.minecraft.dto.version.Rule;
import io.meltwin.scyblaster.minecraft.dto.version.Rule.OS;

public class RuleAdapter implements JsonDeserializer<Rule> {

    @Override
    public Rule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Rule out = new Rule();
        JsonObject obj = json.getAsJsonObject();

        // Action extraction
        if (obj.has("action"))
            out.action = obj.get("action").getAsString();

        // Features extraction
        if (obj.has("features")) {
            JsonObject features = obj.get("features").getAsJsonObject();

            for (Entry<String, JsonElement> entry : features.entrySet())
                out.features.put(entry.getKey(), entry.getValue().getAsString());
        }

        // OS Extraction
        if (obj.has("os")) {
            Gson gson = new Gson();
            out.os = gson.fromJson(obj.get("os").getAsJsonObject(), OS.class);
        }

        return out;
    }

}
