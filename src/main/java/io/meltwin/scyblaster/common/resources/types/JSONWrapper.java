package io.meltwin.scyblaster.common.resources.types;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;

import io.meltwin.scyblaster.common.types.Logging;

/**
 * JSON Wrapper class for processing the parsing of JSON files and make it into
 * a Java Object.
 */
public class JSONWrapper<T> implements Logging {
    /**
     * Meta class for the map of the DTO adapter
     */
    public class AdapterList extends HashMap<Class, JsonDeserializer> {
    }

    protected final T jsonObject;

    /**
     * Make a wrapper for the given class
     * 
     * @param file   the resource file corresponding to the local json file
     * @param classT the class of the DTO Java object we are making
     */
    protected JSONWrapper(@NotNull ResourceFile file, Class<T> classT) {
        jsonObject = this.parseDTO(file, classT);
    }

    /**
     * Return the list of the adapters used for this DTO file
     */
    protected @NotNull AdapterList getAdapters() {
        return new AdapterList();
    }

    /**
     * Parse a JSON DTO for further purpose
     * 
     * @param assetIndex the resource file to parse
     * @param cT         the class of the object to make
     * @return a Java DTO object or null if there was an error
     */
    private final @Nullable T parseDTO(@NotNull ResourceFile assetIndex, Class<T> cT) {
        try {
            try {
                if (assetIndex.status == ResourceStatus.READY) {
                    // Constructing builder
                    GsonBuilder builder = new GsonBuilder();
                    for (Entry<Class, JsonDeserializer> adapter : getAdapters().entrySet()) {
                        builder.registerTypeAdapter(adapter.getKey(), adapter.getValue());
                    }

                    // Parse JSON DTO
                    return builder.create().fromJson(new String(Files.readAllBytes(assetIndex.localPath)), cT);
                }
                log().error(String.format("The resource %s was not ready !", cT.getSimpleName()));
            } catch (JsonSyntaxException e) {
                log().fatal(String.format("The json file is not corresponding to the Java object representation %s!",
                        cT.getSimpleName()));
            } catch (IOException e) {
                log().fatal(String.format("Could not parse the local file for %s!", cT.getSimpleName()));
            }
            return cT.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | SecurityException | NoSuchMethodException e) {
            log().fatal(String.format(
                    "Could not initialise default %s object. Please check the constructors and make sure they are public and one of them don't take any arguments.",
                    cT.getName()));
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return The Java object that we constructed. May return a null value if there
     *         was an error.
     */
    public final @Nullable T getObject() {
        return this.jsonObject;
    }
}