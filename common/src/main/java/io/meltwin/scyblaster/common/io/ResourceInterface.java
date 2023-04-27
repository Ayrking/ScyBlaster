/*
Author: Meltwin (meltwin158@gmail.com)
ResourceInterface.java (c) 2023
Desc: Resource interface 
Created:  2023-04-26T11:50:49.078Z
Modified: 2023-04-26
*/

package io.meltwin.scyblaster.common.io;

import java.nio.file.Path;

public interface ResourceInterface {
    /**
     * @return Whether the resource is ready to be used or not
     */
    boolean is_resource_ready();

    /**
     * @return The path to the local temp file
     */
    Path get_resource_path();
}