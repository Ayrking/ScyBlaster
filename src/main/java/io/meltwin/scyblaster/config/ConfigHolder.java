package io.meltwin.scyblaster.config;

import io.meltwin.scyblaster.config.project.ProjectConfiguration;

public class ConfigHolder {
    public ProjectConfiguration projectConfig;

    public final void clear() {
        projectConfig = null;
    }
}
