package io.meltwin.scyblaster;

import io.meltwin.scyblaster.config.ProjectConfiguration;
import io.meltwin.scyblaster.config.ProjectConfigurationBuilder;

public class Test {
    public static void main(String[] args) {
        ProjectConfiguration configs = new ProjectConfigurationBuilder("project.xml").make();
        ScyblasterAPI api = new ScyblasterAPI();
        api.launchMC(configs);
    }

}
