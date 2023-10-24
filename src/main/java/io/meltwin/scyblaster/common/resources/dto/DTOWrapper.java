package io.meltwin.scyblaster.common.resources.dto;

import java.lang.reflect.InvocationTargetException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.types.Logging;

public abstract class DTOWrapper<T> implements Logging {

    private static final String ERROR_INIT_CONSTRUCTOR = "Could not initialise default %s object. Please check the constructors and make sure they are public and one of them don't take any arguments.";

    protected final T object;

    protected DTOWrapper(@NotNull ResourceFile file, Class<T> classT) {
        this.object = parseDTO(file, classT);
    }

    /**
     * Parse a DTO for further purpose
     * 
     * @param assetIndex the resource file to parse
     * @param cT         the class of the object to make
     * @return a Java DTO object or null if there was an error
     */
    protected final @Nullable T parseDTO(@NotNull ResourceFile assetIndex, Class<T> cT) {
        try {
            T obj = makeJavaDTOObject(assetIndex, cT);
            return (obj != null) ? obj : cT.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | SecurityException | NoSuchMethodException e) {
            ffatal(ERROR_INIT_CONSTRUCTOR, cT.getName());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Make a Java Object based on the given resource file
     * 
     * @param assetIndex
     * @param cT
     * @return an instance of the object, else null
     */
    protected abstract @Nullable T makeJavaDTOObject(@NotNull ResourceFile assetIndex, Class<T> cT);

    /**
     * @return The Java object that we constructed. May return a null value if there
     *         was an error.
     */
    public final @Nullable T getObject() {
        return this.object;
    }

}
