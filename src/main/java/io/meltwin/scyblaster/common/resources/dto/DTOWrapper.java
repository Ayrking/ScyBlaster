package io.meltwin.scyblaster.common.resources.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.types.Logging;

public abstract class DTOWrapper<T> implements Logging {

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
    protected abstract @Nullable T parseDTO(@NotNull ResourceFile assetIndex, Class<T> cT);

    /**
     * @return The Java object that we constructed. May return a null value if there
     *         was an error.
     */
    public final @Nullable T getObject() {
        return this.object;
    }

}
