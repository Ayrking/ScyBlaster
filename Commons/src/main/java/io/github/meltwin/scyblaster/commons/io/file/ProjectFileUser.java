package io.github.meltwin.scyblaster.commons.io.file;

import io.github.meltwin.scyblaster.commons.Pair;
import io.github.meltwin.scyblaster.commons.io.LogUtils;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public interface ProjectFileUser {

    ArrayList<Pair<FileType, String>> files = new ArrayList<>();
    static void registerFiles(ProjectFileHandler handler) {
        for (Pair<FileType, String> p : files)
            handler.addLauncherFile(p.getFirst(), p.getSecond());
    }
    static void logFiles() {LogUtils.traceList("List of resources files :", files);}
}
