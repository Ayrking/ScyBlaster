package io.meltwin.scyblaster;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.config.ProjectConfiguration;
import io.meltwin.scyblaster.minecraft.AssetsHub;
import io.meltwin.scyblaster.minecraft.version.VersionDescriptor;

/**
 * API Entrypoint for launching any MC Versions
 */
public class ScyblasterAPI implements Logging {

    /**
     * Launch the given minecraft version
     */
    public final void launchMC(@NotNull ProjectConfiguration configuration) {
        log().debug(configuration);
        AssetsHub hub = new AssetsHub(configuration);
        VersionDescriptor version = hub.getVersionsDescriptor("1.4.7");
        hub.prepareVersion(version);
    }
}
