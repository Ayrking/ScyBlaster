package io.github.meltwin.scyblaster.commons.io.file;

import io.github.meltwin.scyblaster.commons.Pair;
import io.github.meltwin.scyblaster.commons.ProjectConfiguration;
import io.github.meltwin.scyblaster.commons.io.LogUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project file handler
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class ProjectFileHandler {
    public ProjectFileHandler() {}
    /*
        =========================
              Checking Data
        =========================
     */
    boolean RESOURCE_DIR = true, LOCAL_DIR = true;
    final ArrayList<Pair<FileType,String>> CONFIG_FILE = new ArrayList<>();
    protected final HashMap<String, Boolean> EXIST = new HashMap<>();

    final Logger log = LogManager.getRootLogger();

    /*
        =========================
                 Messages
        =========================
     */
    static final String MSG_CHECK_STARTING = "Checking configuration files.";
    static final String MSG_NO_CUSTOM = "No custom resource directory declared !";
    static final String MSG_NO_LOCAL = "No local directory was found. Let's create a new one !";
    static final String MSG_NO_RES = "No resource file %s found in the %s resource directory";
    static final String MSG_RES_FOUND = "Resource file %s was successfully found !";
    static final String MSG_NO_FILE = "No file %s found in the local directory";
    static final String MSG_FILE_FOUND = "File %s was found !";

    /*
        =========================
               Adding files
        =========================
     */
    /**
     * Add a config file to be checked
     * @param file_path the path to the file to be checked
     */
    public void addResFile(@NotNull final String file_path) {
        CONFIG_FILE.add(new Pair<>(FileType.RESOURCE, file_path));
        EXIST.put(file_path,false);
    }
    public void addLauncherFile(FileType type, @NotNull final String file_path) {
        CONFIG_FILE.add(new Pair<>(type, file_path));
        EXIST.put(file_path, false);
    }

    /*
        =========================
              Checking File
        =========================
     */

    /**
     * Check for all the files previously registered with addXXXFile()
     */
    public void checkFiles() {
        LogUtils.logTitle("Checking files");
        log.info(MSG_CHECK_STARTING);
        checkCustomDir();
        checkLocalDir();
        HashMap<String, String> file_path = new HashMap<>();
        for (Pair<FileType, String> p : CONFIG_FILE) {
            switch (p.getFirst()) {
                // Check resources files
                case RESOURCE:
                    if (RESOURCE_DIR) checkResFile(p.getSecond());
                    // Log
                    file_path.put(p.getSecond(), (EXIST.get(p.getSecond()))  ? resPath(p.getSecond()): defResPath(p.getSecond()));
                    break;
                // Check local files
                case JAR:
                    if (LOCAL_DIR) checkLocalFile(p.getSecond());
                    // Log
                    file_path.put(p.getSecond(), (EXIST.get(p.getSecond()))  ? localPath(p.getSecond()): "X");
                    break;
            }
        }

        LogUtils.traceList("Summary: Using checked files : ", CONFIG_FILE, file_path);
    }
    private void checkCustomDir() {
        RESOURCE_DIR = (ProjectConfiguration.getProjectLocalDir() == null);
        if (!RESOURCE_DIR) log.warn(MSG_NO_CUSTOM);
    }
    private void checkLocalDir() {
        LOCAL_DIR = Files.isDirectory(Paths.get(ProjectConfiguration.getProjectLocalDir()));
        if (!LOCAL_DIR) log.info(MSG_NO_LOCAL);
    }
    private void checkResFile(@NotNull final String file) {
        URL res_file = getClass().getClassLoader().getResource(resPath(file));
        EXIST.put(file, (res_file != null));
        if (!EXIST.get(file))
            log.warn(String.format(MSG_NO_RES,
                    file,
                    ProjectConfiguration.getProjectResDir()
            ));
        else log.trace(String.format(MSG_RES_FOUND, file));
    }
    private void checkLocalFile(@NotNull final String file) {
        EXIST.put(file, Files.exists(Paths.get(localPath(file))));
        if (!EXIST.get(file))
            log.info(String.format(MSG_NO_FILE,
                    localPath(file)
                ));
        else log.trace(String.format(MSG_FILE_FOUND, file));
    }

    /*
        =========================
                  METHODS
        =========================
     */
    public final boolean exist(@NotNull final String file) {return EXIST.get(file) != null && EXIST.get(file);}

    /*
        =========================
                  PATHS
        =========================
     */
    private String path(final String dir, final String file) {
        return String.format("%s%s", dir, file);
    }
    private String resPath(final String file_path) {
        return path(ProjectConfiguration.getProjectResDir()+"/", file_path);
    }
    private String defResPath(final String file_path) {
        return path(ProjectConfiguration.getProjectDefaultResDir(), file_path);
    }
    private String localPath(final String file_path) {
        return path(ProjectConfiguration.getProjectLocalDir(), file_path);
    }
    @NotNull public String getFile(final FileType type, final String file_path) {
        if (type == FileType.RESOURCE)
            return (EXIST.get(file_path))
                    ? resPath(file_path)
                    : defResPath(file_path);
        return "";
    }

}
