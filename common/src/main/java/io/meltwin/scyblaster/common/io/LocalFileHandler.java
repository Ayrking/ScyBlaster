/*
Author: Meltwin (meltwin158@gmail.com)
LocalFileHandler.java (c) 2023
Desc: File handler for opening files
Created:  2023-04-27T05:48:26.845Z
Modified: 2023-04-27
*/

package io.meltwin.scyblaster.common.io;

import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract public class LocalFileHandler<T> {

    protected ResourceInterface resource = null;

    protected LocalFileHandler(@NotNull final ResourceInterface resource) {
        this.resource = resource;
    }

    /**
     * Return the path to the local file on the computer
     * 
     * @return Path the path to the resource if it exists, else null
     */
    @Nullable
    public final Path get_path() {
        if (resource != null)
            return resource.get_resource_path();
        return null;
    }

    /**
     * Read a file and get the raw data
     * 
     * @return a String instance containing the raw data
     */
    @NotNull
    public final String get_raw_data() {
        return "";
    }

    /**
     * Read the file and structure it in an Object
     * 
     * @return the data in a structure format
     */
    public abstract T get_data();

}
