package io.meltwin.scyblaster.common.resources.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.exceptions.InvalidWrapperException;
import io.meltwin.scyblaster.common.exceptions.UnavailableResourceException;
import io.meltwin.scyblaster.common.resources.ResourceHandler;
import io.meltwin.scyblaster.common.resources.types.ResourceFile;
import io.meltwin.scyblaster.common.resources.types.ResourceStatus;
import io.meltwin.scyblaster.common.types.Logging;

/**
 * Interface for loading a DTO Wrapper
 */
public interface DTOLoader extends Logging {
    static final String ERROR_RES_UNAVAILABLE = "Couldn't get the wanted resource";
    static final String ERROR_DTO_WRAPPER_INIT = "Couldn't instantiate DTO Wrapper %s. Please check that there's a constructor with a single ResourceFile parameter !";

    /**
     * Download and load a JSON DTO
     * 
     * @param <T>  the Java DTO Wrapper
     * @param cT   the class of the wrapper
     * @param file the resource to fetch
     * @return the wanted Java DTO Wrapper, or null in case of error
     */
    public default <T extends DTOWrapper> @Nullable T loadDTO(final Class<T> cT,
            final @NotNull ResourceFile file)
            throws InvalidWrapperException, UnavailableResourceException {
        try {
            // Prepare file
            ResourceFile fileResult;
            {
                long start = System.nanoTime();
                fileResult = ResourceHandler.prepareFile(file).get();
                printDelta(start);
            }

            // And load it
            if (fileResult.status == ResourceStatus.READY)
                return cT.getConstructor(ResourceFile.class).newInstance(file);
            else
                throw new UnavailableResourceException(file);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new InvalidWrapperException(String.format(ERROR_DTO_WRAPPER_INIT, cT.getSimpleName()));
        } catch (InterruptedException | ExecutionException e) {
            log().fatal(ERROR_RES_UNAVAILABLE);
            e.printStackTrace();
            return null;
        }
    }
}
