package io.meltwin.scyblaster;

import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.config.ProjectConfiguration;
import io.meltwin.scyblaster.config.ProjectConfigurationBuilder;

public class Test implements Logging {
    public static void main(String[] args) {
        ProjectConfiguration configs = new ProjectConfigurationBuilder("project.xml").make();
        try (ScyblasterAPI api = new ScyblasterAPI(configs)) {
            api.launchMC("1.4.7");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
