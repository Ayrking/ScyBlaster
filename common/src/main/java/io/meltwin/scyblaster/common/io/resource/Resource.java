/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

/*
Author: Meltwin (meltwin158@gmail.com)
ResourceInterface.java (c) 2023
Desc: Resource interface 
Created:  2023-04-26T11:50:49.078Z
Modified: 2023-04-26
*/

package io.meltwin.scyblaster.common.io.resource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Resource {

    /*  ==============================================================
                            Resource Properties
        ==============================================================
     */
    protected ResourceStatus status = ResourceStatus.TO_PROCESS;
    protected ResConfig config = ResConfig.make();
    protected Exception error = null;
    protected final ResourceSource source;
    protected final String dist_path;
    protected final String local_path;

    /**
     * Create a custom resource
     * @param source the source containing the file
     * @param dist_path where to search for the file on the source
     * @param local_path where to search for the file locally
     */
    public Resource(@NotNull ResourceSource source, @NotNull String dist_path, @NotNull String local_path) {
        this.source = source;
        this.dist_path = dist_path;
        this.local_path = local_path;
    }

    /*  ==============================================================
                                     Status
        ==============================================================
     */
    public void set_status(ResourceStatus new_status)  { this.status = new_status; }
    /**
     * @return Whether the resource is ready to be used or not
     */
    public boolean is_resource_ready() { return this.status == ResourceStatus.READY; }
    /**
     * @return Whether the resource is in an error state
     */
    public boolean is_in_error_state() { return this.status == ResourceStatus.ERROR; }
    /**
     * @return The current status of the resource
     */
    public ResourceStatus get_status() { return this.status; }

    /*  ==============================================================
                                     Config
        ==============================================================
     */
    /**
     * Set the resource config
     */
    public void set_config(@NotNull ResConfig conf) { this.config = conf; }
    /**
     * @return The config of the current resource
     */
    public ResConfig get_config() { return config; }

    /*  ==============================================================
                                     Errors
        ==============================================================
     */
    public void set_error(@NotNull Exception e) { this.error = e; }
    @Nullable public Exception get_error() { return this.error; }

    /*  ==============================================================
                                    Other
        ==============================================================
     */
    public ResourceSource source() { return source; }


    /**
     * @return The path to the local temp file
     */
    @NotNull public String get_resource_path() { return dist_path; }
    /**
     * @return The path to the local file
     */
    @NotNull public String get_local_path() { return local_path; }
}