package io.meltwin.scyblaster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher {

    public static final Logger logger = LogManager.getLogger("Scyblaster");

    /*
     * private Launcher() {
     * // PHASE 1 : Prepare general purpose data
     * if (!MCVersions.fetch())
     * System.exit(-1);
     * 
     * logger.debug(String.format("Found %d versions for Minecraft",
     * MCVersions.getVersionsNumber()));
     * 
     * // PHASE 2 : GUI Launching
     * launchGUI();
     * 
     * // TEMP PHASE : Launching game
     * //
     * launchGame(configs.getMinecraftConfigs().getVersion().getFirst().intValue());
     * System.exit(0);
     * }
     * 
     * public final void launchGame(int mcVersionID) {
     * // TODO: Check java version
     * 
     * // Debug messages
     * logger.info("Launching Game");
     * logger.debug(String.format("Minecraft target: %s ",
     * MCVersions.getVersion(mcVersionID)));
     * 
     * ClassPath cp = new ClassPath();
     * 
     * // Manage assets
     * AssetsHub manager = new AssetsHub(mcVersionID, cp);
     * if (manager.isErrored()) {
     * System.exit(-1);
     * }
     * DTOVersion version = manager.getMetaData();
     * logger.debug(String.format("Got minecraft version %s", version.id));
     * 
     * // new Runner(configs, version, null, cp);
     * 
     * }
     * 
     * public final void launchGUI() {
     * // TODO making the gui later
     * }
     * 
     * public static void main(String[] args) {
     * new Launcher();
     * }
     */
}
