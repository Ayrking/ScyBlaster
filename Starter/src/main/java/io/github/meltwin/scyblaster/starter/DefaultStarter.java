package io.github.meltwin.scyblaster.starter;

import io.github.meltwin.scyblaster.starter.gui.DefaultGUILoader;
import io.github.meltwin.scyblaster.starter.http.LauncherChecker;

/**
 * Default Starter App for testing
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class DefaultStarter extends StarterApp {

    static final String NAME = "SDL (Scyblaster Default Launcher)";
    static final String VERSION = "0.0.1";

    private DefaultStarter() {
        this.setProjectName(NAME);
        this.setProjectVersion(VERSION);
        this.setLocalDir("scyblaster");
        this.setResDir(null);

        this.setGuiLoader(new DefaultGUILoader());
        this.setChecker(new LauncherChecker());

        this.launch();
    }

    public static void main(String[] args) {new DefaultStarter();}

}
