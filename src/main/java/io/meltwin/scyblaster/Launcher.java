package io.meltwin.scyblaster;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.meltwin.scyblaster.config.Configs;
import io.meltwin.scyblaster.minecraft.MCVersions;

public class Launcher {

    public static final Logger logger = LogManager.getLogger("Scyblaster");
    private Configs configs;

    private Launcher() {
        if (MCVersions.fetch() && loadConfigs()) {
            launchGame();
        }
        System.exit(0);
    }

    private final boolean loadConfigs() {
        try {
            InputStream istr = getClass().getClassLoader().getResourceAsStream("project.xml");
            JAXBContext context = JAXBContext.newInstance(Configs.class);
            configs = (Configs) context.createUnmarshaller().unmarshal(istr);

            return true;
        } catch (JAXBException e) {
            logger.fatal("Could not parse project XML file.");
            e.printStackTrace();
            return false;
        }
    }

    public final void launchGame() {
        logger.info("Launching Game");
        logger.debug(String.format("Using base directory %s", configs.getResourceConfig().getBaseDir()));
        logger.debug(String.format("Minecraft target: %s", configs.getMinecraftConfigs().getType()));
        logger.debug(String.format("Found %d versions for Minecraft", MCVersions.versionList.versions.length));
        logger.debug(String.format("Launcher running for version %1s to %2s",
                configs.getMinecraftConfigs().getVersion().a, configs.getMinecraftConfigs().getVersion().b));
    }

    public final void launchGUI() {
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
