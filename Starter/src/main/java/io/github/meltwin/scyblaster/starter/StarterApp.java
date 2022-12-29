package io.github.meltwin.scyblaster.starter;

import io.github.meltwin.scyblaster.commons.ProjectConfiguration;
import io.github.meltwin.scyblaster.commons.exception.ToolMissingException;
import io.github.meltwin.scyblaster.commons.gui.GUILoader;
import io.github.meltwin.scyblaster.commons.io.file.ProjectFileUser;
import io.github.meltwin.scyblaster.starter.http.LauncherChecker;
import io.github.meltwin.scyblaster.starter.io.StarterFileHandler;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Starter App
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class StarterApp {
    /**
     * Launch the app
     */
    protected final void launch() {
        LogManager.getRootLogger().info(startingMsg());

        try {
            // Checking if there are a GUILoader and a FileChecker
            if (GUI_LOADER == null)
                throw new ToolMissingException(GUILoader.class.getName());
            if (CHECKER == null)
                throw new ToolMissingException(LauncherChecker.class.getName());



            // File Checking
            ProjectFileUser.registerFiles(FILE_HANDLER);
            ProjectFileUser.logFiles();
            FILE_HANDLER.checkFiles();

            // Making
            GUI_LOADER.make(FILE_HANDLER);
            CHECKER.run(FILE_HANDLER);
        }
        catch (ToolMissingException e) {
            e.printStackTrace();
        }
    }

    /*
        =========================
              Project Data
        =========================
     */
    protected final void setProjectName(final @NotNull String name) {ProjectConfiguration.setProjectName(name);}
    protected final void setProjectVersion(final @NotNull String version) {ProjectConfiguration.setProjectVersion(version);}
    /**
     * Set the local directory for your launcher. Should be the name of your launcher, it will result as a directory
     * as os_local_dir/.projectname/
     * @param local the name of your launcher
     */
    protected final void setLocalDir(final @NotNull String local) {ProjectConfiguration.setProjectLocalDir(local);}
    protected final void setResDir(final @Nullable String local) {ProjectConfiguration.setProjectResDir(local);}

    // Starter utils
    final StarterFileHandler FILE_HANDLER = new StarterFileHandler();
    GUILoader GUI_LOADER;
    LauncherChecker CHECKER;
    protected final void setGuiLoader(final @NotNull GUILoader loader) {GUI_LOADER = loader;}
    protected final void setChecker(final @NotNull LauncherChecker checker) {CHECKER = checker;}


    /*
        =========================
             Private Methods
        =========================
     */
    final String startingMsg() {
        return String.format("Starting %s v%s",
                ProjectConfiguration.getProjectName(),
                ProjectConfiguration.getProjectVersion());
    }
}