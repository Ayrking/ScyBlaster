package io.meltwin.scyblaster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher {

    private Logger logger = LogManager.getLogger("Scyblaster");

    public final void launchGame() {
        logger.info("Launching Game");
    }

    public final void launchGUI() {
    }

    public static void main(String[] args) {
        Launcher l = new Launcher();
        l.launchGame();
    }
}
