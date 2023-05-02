/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.files;


import java.util.ArrayList;

/**
 * File Manager for ScyBlaster apps
 */
public class FilesManager {
    /*  ==============================================================
                                 SINGLETON
        ==============================================================
     */
    private static FilesManager instance = null;
    private FilesManager() { }
    public static FilesManager get_instance() {
        if (instance == null)
            instance = new FilesManager();
        return instance;
    }

    /*  ==============================================================
                                Internal Work
        ==============================================================
     */
    protected void check_file_exist(ArrayList<String> file_list) {}

    /*  ==============================================================
                                Static Interface
        ==============================================================
     */
    public static void checkFileExist(ArrayList<String> file_list) { get_instance().check_file_exist(file_list); }

}
