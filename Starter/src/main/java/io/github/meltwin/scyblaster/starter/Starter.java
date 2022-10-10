package io.github.meltwin.scyblaster.starter;

import io.github.meltwin.scyblaster.starter.gui.DefaultGUILoader;
import io.github.meltwin.scyblaster.starter.http.LauncherChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Starter App
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class Starter {
    static public final Logger log = LogManager.getRootLogger();

    public Starter() {
        log.info("Starting Scyblaster 0.1-SNAPSHOT");
        DefaultGUILoader gui_loader = new DefaultGUILoader();
        gui_loader.makeGUI();
        LauncherChecker checker = new LauncherChecker();
        checker.run();
    }


    // Starting function
    public static void main(String[] args) {
        new Starter();
    }

}