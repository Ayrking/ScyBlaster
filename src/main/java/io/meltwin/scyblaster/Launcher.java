package io.meltwin.scyblaster;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.meltwin.scyblaster.config.Configs;

public class Launcher {

    public static final Logger logger = LogManager.getLogger("Scyblaster");
    private Configs configs;

    private final void loadConfigs() throws JAXBException {
        InputStream istr = getClass().getClassLoader().getResourceAsStream("project.xml");
        JAXBContext context = JAXBContext.newInstance(Configs.class);
        configs = (Configs) context.createUnmarshaller().unmarshal(istr);
    }

    public final void launchGame() {
        logger.info("Launching Game");
        logger.debug(String.format("Using base directory %s", configs.getResourceConfig().getBaseDir()));

    }

    public final void launchGUI() {
    }

    public static void main(String[] args) {
        Launcher l = new Launcher();
        try {
            l.loadConfigs();
            l.launchGame();
        } catch (JAXBException e) {
            logger.fatal("Could not parse project XML file.");
        }

    }
}
