package io.github.meltwin.scyblaster.starter.http;


import io.github.meltwin.scyblaster.commons.Pair;
import io.github.meltwin.scyblaster.commons.io.file.FileType;
import io.github.meltwin.scyblaster.commons.io.file.ProjectFileHandler;
import io.github.meltwin.scyblaster.commons.io.file.ProjectFileUser;
import io.github.meltwin.scyblaster.commons.io.json.JSONFile;
import io.github.meltwin.scyblaster.commons.io.file.FileUtils;
import io.github.meltwin.scyblaster.commons.io.http.HTTPJSONFile;
import io.github.meltwin.scyblaster.starter.event.TextEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Launcher checker
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class LauncherChecker implements ProjectFileUser {
    private final Logger logger = LogManager.getRootLogger();

    static final String CONFIG_FILE = "config.json";

    public LauncherChecker() {
        registerFiles();
    }
    private void registerFiles() {
        files.add(new Pair<>(FileType.RESOURCE, CONFIG_FILE));
        files.add(new Pair<>(FileType.JAR, "launcher.jar"));
    }

    /*
        =========================
                   MSG
        =========================
     */
    static final String USE_DIR = "Using user directory %s";
    static final String CHECKING_LATEST = "Checking for launcher update ...";
    static final String MSG_NO_LOCAL_JAR = "No local jar found.";
    static final String MSG_CANT_OPEN_JAR = "Can't open local launcher.jar.";
    static final String MSG_DOWNLOAD_NEW = "More recent version found online (%s).";
    static final String MSG_UP_TO_DATE = "Launcher is up to date (%s)";
    static final String MSG_DOWNLOADING_LAUNCHER = "Downloading latest launcher";
    static final String MSG_DIST_LAUNCHER_NOT_FOUND = "Distant launcher file not found. Please contact your administrator";
    static final String MSG_DOWNLOADED_LAUNCHER = "New launcher downloaded (%s) !";

    /*
        =========================
               Task Method
        =========================
     */
    public void run(@NotNull final ProjectFileHandler handler) {
        logger.info(String.format(USE_DIR, FileUtils.getUserDir()));
        this.loadConfigs(handler);
        this.checkLatest();
    }

    /*
        =========================
                 Configs
        =========================
     */
    SyncConfig SYNC_CONF = null;
    void loadConfigs(@NotNull final ProjectFileHandler handler) {
        JSONFile conf_file = new JSONFile(handler.getFile(FileType.RESOURCE, CONFIG_FILE));
        SYNC_CONF = new SyncConfig(conf_file, SyncConfig.SYNC_INDEX);
    }



    /*
        =========================
               Jar Checking
        =========================
     */
    HTTPJSONFile latest_launcher = null;
    private void checkLatest() {
        logger.info(CHECKING_LATEST);
        new TextEvent(CHECKING_LATEST).launchEvent();

        // Get Latest JSON Launcher file
        if (SYNC_CONF.getDistFile() != null) {
            latest_launcher = new HTTPJSONFile(SYNC_CONF.getDistFile());
        }
        else System.exit(-1);

        // Compare with local file
        String local_version = getLocalLauncherVersion();
        if (moreRecent(local_version, latest_launcher.getString("version"))) {
            logger.info(String.format(MSG_DOWNLOAD_NEW, latest_launcher.getString("version")));
            this.downloadLauncher();
        }
        else
            logger.info(String.format(MSG_UP_TO_DATE, local_version));
    }

    static final String LAUNCHER_FILE = "launcher.jar";
    static final String LAUNCHER_OLD_FILE = "launcher.jar.old";
    static final String NO_VERSION = "0.0.0";
    String getLocalLauncherVersion() {
        // Check if file exist
        File f = new File(Strings.concat(FileUtils.getUserDir(), LAUNCHER_FILE));
        if (!f.exists()) {
            logger.info(MSG_NO_LOCAL_JAR);
            return NO_VERSION;
        }

        // If file exist, read his version from his manifest
        String version = "";
        try {
            JarFile l_jar = new JarFile(Strings.concat(FileUtils.getUserDir(), LAUNCHER_FILE));
            Manifest mf = l_jar.getManifest();
            version = mf.getMainAttributes().getValue("Version");
            version = (version == null) ? NO_VERSION : version;

            l_jar.close();
        }
        catch (Exception e) {
            logger.fatal(MSG_CANT_OPEN_JAR);
            e.printStackTrace();
            System.exit(-1);
        }
        return version;
    }
    static final String REGEX_VERSION = "[.\\-]";
    boolean moreRecent(final @NotNull String local_version, final @NotNull String dist_version) {
        String[] local_parse, dist_parse;
        local_parse = local_version.split(REGEX_VERSION);
        dist_parse = dist_version.split(REGEX_VERSION);
        for (int i = 0; i<3; i++) {
            if (Integer.parseInt(local_parse[i]) < Integer.parseInt(dist_parse[i]))
                return true;
        }
        return false;
    }
    void downloadLauncher() {
        logger.info(MSG_DOWNLOADING_LAUNCHER);
        File new_file = new File(Strings.concat(FileUtils.getUserDir(), LAUNCHER_FILE));
        File old_file = new File(Strings.concat(FileUtils.getUserDir(), LAUNCHER_OLD_FILE));
        try {
            URL url = new URL(latest_launcher.getString("file"));
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());

            if (new_file.exists())
                new_file.renameTo(old_file);
            createFile(new_file);

            FileOutputStream fileOutputStream = new FileOutputStream(Strings.concat(FileUtils.getUserDir(),LAUNCHER_FILE));
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();

            logger.info(String.format(MSG_DOWNLOADED_LAUNCHER, new_file.getAbsolutePath()));

        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                logger.error(MSG_DIST_LAUNCHER_NOT_FOUND);
                e.printStackTrace();
            }
            else {
                e.printStackTrace();
            }

            // Restoring old launcher
            if (new_file.exists())
                new_file.delete();
            if (old_file.exists())
                old_file.renameTo(new_file);
        }
        if (old_file.exists())
            old_file.delete();
    }
    void createFile(final @NotNull File file) throws Exception {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        file.createNewFile();
    }
}
