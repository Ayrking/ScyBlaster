package io.meltwin.scyblaster;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.meltwin.scyblaster.config.Configs;
import io.meltwin.scyblaster.files.ResourceHandler;
import io.meltwin.scyblaster.minecraft.MCVersions;
import io.meltwin.scyblaster.minecraft.AssetsManager;
import io.meltwin.scyblaster.minecraft.dto.version.Version;

public class Launcher {

    public static final Logger logger = LogManager.getLogger("Scyblaster");
    public static Configs configs;

    private Launcher() {
        // PHASE 1 : Prepare general purpose data
        if (!MCVersions.fetch() || !loadConfigs())
            System.exit(-1);
        logger.debug(String.format("Using directories BASE='%s', ASSETS='%s', LIBS='%s'",
                configs.getResourceConfig().getBaseDir(), configs.getResourceConfig().getAssetsDir(),
                configs.getResourceConfig().getLibsDir()));
        logger.trace(String.format("Assets location : %s", ResourceHandler.getAssetsDir()));
        logger.trace(String.format("Libs location : %s", ResourceHandler.getLibsDir()));

        logger.debug(String.format("Found %d versions for Minecraft", MCVersions.getVersionsNumber()));
        logger.debug(String.format("Launcher configured for accepting versions from %1s to %2s (%3s)",
                configs.getMinecraftConfigs().getVersion().a,
                configs.getMinecraftConfigs().getVersion().b,
                configs.getMinecraftConfigs().getType().toString()));

        // PHASE 2 : GUI Launching
        launchGUI();

        // TEMP PHASE : Launching game
        launchGame(configs.getMinecraftConfigs().getVersion().getFirst().intValue());
        System.exit(0);
    }

    private final boolean loadConfigs() {
        try {
            InputStream istr = getClass().getClassLoader().getResourceAsStream("project.xml");
            JAXBContext context = JAXBContext.newInstance(Configs.class);
            configs = (Configs) context.createUnmarshaller().unmarshal(istr);

            return true;
        } catch (JAXBException e) {
            logger.fatal("Could not parse project config XML file.");
            e.printStackTrace();
            return false;
        }
    }

    public final void launchGame(int mcVersionID) {
        // Debug messages
        logger.info("Launching Game");
        logger.debug(String.format("Minecraft target: %s ", MCVersions.getVersion(mcVersionID)));

        AssetsManager manager = new AssetsManager(mcVersionID);
        if (manager.errored) {
            System.exit(-1);
        }
        Version version = manager.getMetaData();

        logger.debug(String.format("Got minecraft version %s", version.id));

    }

    public final void launchGUI() {
        // TODO making the gui later
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
