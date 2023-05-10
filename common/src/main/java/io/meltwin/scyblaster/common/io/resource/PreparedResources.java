/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.io.resource;

import java.util.ArrayList;

/**
 * Response to a resource preparation request
 */
public class PreparedResources {
    public final ArrayList<Resource> ready_res = new ArrayList<>();
    public final ArrayList<Resource> error_res = new ArrayList<>();

    public int number_of_ready() { return ready_res.size(); }
    public int number_of_error() { return error_res.size(); }
    public int number_of_resources() { return number_of_error()+number_of_ready(); }

}
