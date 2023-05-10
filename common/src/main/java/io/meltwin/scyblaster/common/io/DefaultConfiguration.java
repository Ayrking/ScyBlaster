/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.io;

/**
 * Default configuration for resource management
 * Under a singleton pattern for memory simplicity
 */
public class DefaultConfiguration implements ResourceConfiguration {

    /*  ==============================================================
                                  Singleton
        ==============================================================
     */
    private static DefaultConfiguration INSTANCE = null;
    private DefaultConfiguration() {}

    /**
     * Get an instance of the default configuration
     * @return an un instance of the default configuration
     */
    public static DefaultConfiguration getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DefaultConfiguration();
        return INSTANCE;
    }

    @Override
    public String getGameFolder() {
        return ".scyblaster";
    }
}
