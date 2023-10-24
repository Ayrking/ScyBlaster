package io.meltwin.scyblaster;

import io.meltwin.scyblaster.common.types.LaunchArguments;
import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.config.project.ProjectConfiguration;
import io.meltwin.scyblaster.config.project.ProjectConfigurationBuilder;

public class Test implements Logging {
    public static void main(String[] args) {
        // testLaunchArguments();
        launchVersion();
    }

    private static final void launchVersion() {
        ProjectConfiguration configs = new ProjectConfigurationBuilder("project.xml").make();
        try (ScyblasterAPI api = new ScyblasterAPI(configs)) {
            api.launchMC("1.4.7");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final void testLaunchArguments() {
        LaunchArguments lArgs = new LaunchArguments();
        lArgs.addArg(
                "${auth_player_name} ${auth_session} --demo  --gameDir ${game_directory} --assetsDir ${game_assets} -cp ${classpath}");
        System.out.println(String.format("Arguments is : %s", lArgs));
    }
}
