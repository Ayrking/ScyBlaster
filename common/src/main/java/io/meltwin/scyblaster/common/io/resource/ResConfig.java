/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.io.resource;

/**
 * Configs of the resource, parameters should not be changed
 */
public class ResConfig {
    /*  ==============================================================
                              Builder Object
        ==============================================================
     */
    private ResConfig() {}
    public static ResConfig make() { return new ResConfig(); }

    /*  ==============================================================
                              Config Parameters
        ==============================================================
     */
    protected boolean FORCE_DOWNLOAD = false;
    public final boolean should_force_download() { return FORCE_DOWNLOAD; }

    protected OnErrorPolicy ON_ERROR = OnErrorPolicy.SKIP;
    public final OnErrorPolicy error_policy() { return ON_ERROR; }

    /*  ==============================================================
                                Builder methods
        ==============================================================
     */
    public final ResConfig force_download(boolean force) {
        FORCE_DOWNLOAD = force;
        return this;
    }
    public final ResConfig error_policy(OnErrorPolicy policy) {
        ON_ERROR = policy;
        return this;
    }
}
