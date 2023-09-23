package io.meltwin.scyblaster.minecraft.adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import io.meltwin.scyblaster.minecraft.dto.version.Argument;
import io.meltwin.scyblaster.minecraft.dto.version.Rule;

public class ArgumentAdapter implements JsonDeserializer<Argument> {

    @Override
    public Argument deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Argument out = new Argument();

        // Process differently whether the argument is a simple string or a structure
        if (!json.isJsonObject()) {
            out.values = new String[1];
            out.values[0] = json.getAsString();
            out.rules = new Rule[0];
        } else {
            JsonObject object = json.getAsJsonObject();

            if (object.get("value").isJsonArray()) {
                JsonArray valuesT = object.get("value").getAsJsonArray();
                out.values = new String[valuesT.size()];
                for (int i = 0; i < valuesT.size(); i++) {
                    out.values[i] = valuesT.get(i).getAsString();
                }
            } else {
                out.values = new String[1];
                out.values[0] = object.get("value").getAsString();
            }

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Rule.class, new RuleAdapter());
            Gson gson = builder.create();

            ArrayList<Rule> ruleT = new ArrayList<>();
            JsonArray rulesList = object.get("rules").getAsJsonArray();
            for (JsonElement elem : rulesList) {
                ruleT.add(gson.fromJson(elem, Rule.class));
            }

            out.rules = new Rule[ruleT.size()];
            out.rules = ruleT.toArray(out.rules);
        }

        return out;
    }

}
