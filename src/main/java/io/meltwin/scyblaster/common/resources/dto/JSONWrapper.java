package io.meltwin.scyblaster.common.resources.dto;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;

import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceStatus;

/**
 * JSON Wrapper class for processing the parsing of JSON files and make it into
 * a Java Object.
 */
public class JSONWrapper<T> extends DTOWrapper<T> {
    /**
     * Meta class for the map of the DTO adapter
     */
    public class AdapterList extends HashMap<Class<?>, JsonDeserializer<?>> {
    }

    // ====================================================================
    // Wrapper Properties
    // ====================================================================
    /**
     * Make a wrapper for the given class
     * 
     * @param file   the resource file corresponding to the local json file
     * @param classT the class of the DTO Java object we are making
     */
    protected JSONWrapper(@NotNull ResourceFile file, Class<T> classT) {
        super(file, classT);
    }

    /**
     * Return the list of the adapters used for this DTO file
     */
    protected @NotNull AdapterList getAdapters() {
        return new AdapterList();
    }

    // ====================================================================
    // Loading Logic
    // ====================================================================
    private static final String RES_NOT_READY = "The resource %s was not ready !";
    private static final String JSON_INVALID = "The json file is not corresponding to the Java object representation %s!";
    private static final String IO_EXCEPTION = "Could not parse the local file for %s!";

    @Override
    protected final @Nullable T makeJavaDTOObject(@NotNull ResourceFile assetIndex, Class<T> cT) {
        try {
            if (assetIndex.status == ResourceStatus.READY) {
                // Constructing builder
                GsonBuilder builder = new GsonBuilder();
                for (Entry<Class<?>, JsonDeserializer<?>> adapter : getAdapters().entrySet())
                    builder.registerTypeAdapter(adapter.getKey(), adapter.getValue());

                // Parse JSON DTO
                return builder.create().fromJson(new String(Files.readAllBytes(assetIndex.localPath)), cT);
            }
            ferror(RES_NOT_READY, cT.getSimpleName());
        } catch (JsonSyntaxException e) {
            ffatal(JSON_INVALID, cT.getSimpleName());
        } catch (IOException e) {
            ffatal(IO_EXCEPTION, cT.getSimpleName());
        }
        return null;
    }
}