package io.github.meltwin.scyblaster.commons.gui;

import io.github.meltwin.scyblaster.commons.Pair;
import io.github.meltwin.scyblaster.commons.io.LogUtils;
import io.github.meltwin.scyblaster.commons.io.file.FileType;
import io.github.meltwin.scyblaster.commons.io.file.ProjectFileHandler;
import io.github.meltwin.scyblaster.commons.io.file.ProjectFileUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * Loader for GUI configurations
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public abstract class GUILoader<BGUIConf extends BaseGUIConfig, CGUIConf extends BaseGUIConfig>
        implements ProjectFileUser {
    /*
        =========================
                 MEMBERS
        =========================
     */
    protected final Logger logger = LogManager.getLogger();
    static final String GUI_CONFIG_FILE = "GUI_config.json";

    protected BaseGUI gui;

    /*
        =========================
                 MESSAGES
        =========================
     */
    static final String MSG_STARTING = "Loading GUI !";
    static final String MSG_MAKING_CUSTOM = "Found custom GUI. Proceeding to the loading !";
    static final String MSG_MAKING_DEFAULT = "Continuing with default GUI.";

    /*
        =========================
              Configuration
        =========================
     */

    protected GUILoader() {
        logger.info(MSG_STARTING);
        this.saveConfigs();
    }
    void saveConfigs() {
        files.add(new Pair<>(FileType.RESOURCE, GUI_CONFIG_FILE));
    }

    /*
        =========================
                  Making
        =========================
     */
    /**
     * Start the making of the GUI.
     */
    public final void make(final @NotNull ProjectFileHandler handler) {
        LogUtils.logTitle("GUI Making");
        if (handler.exist(GUI_CONFIG_FILE)) {
            logger.info(MSG_MAKING_CUSTOM);
            CGUIConf conf = makeCustomConfig(handler.getFile(FileType.RESOURCE, GUI_CONFIG_FILE));
            logger.info(conf);
            this.makeCustomGUI(conf);
        }
        else {
            logger.info(MSG_MAKING_DEFAULT);
            BGUIConf conf = makeDefaultConfig(handler.getFile(FileType.RESOURCE, GUI_CONFIG_FILE));
            logger.info(conf);
            this.makeDefaultGUI(conf);
        }
    }

    protected abstract BGUIConf makeDefaultConfig(@NotNull final String file);
    protected abstract CGUIConf makeCustomConfig(@NotNull final String file);

    /**
     * Launch the making of the custom GUI.
     */
    protected abstract void makeDefaultGUI(@NotNull final BGUIConf conf);
    /**
     * Launch the making of the default GUI.
     */
    protected abstract void makeCustomGUI(@NotNull final CGUIConf conf);

}
