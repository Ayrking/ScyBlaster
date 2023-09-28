package io.meltwin.scyblaster.common.files.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.files.types.ResourceFile;

public interface IBaseHandler {

    @NotNull
    Logger getLogger();

    @NotNull
    Future<ResourceFile> getFile(@NotNull ResourceFile file);

    @Nullable
    ExecutorService getExecutor();

    /**
     * Check if the file is present locally
     * 
     * @param file the resource file to check
     * @return true if the file is present on disc
     */
    default boolean isSameLocally(@NotNull ResourceFile file) {
        if (file.localPath == null || file.localPath.equals(""))
            return true;

        // Check presence
        File f = new File(file.localPath);
        if (!f.exists() && !f.isDirectory())
            return false;

        // Check SHA1
        if (file.sha1 == null)
            return !file.forceFownload;
        try {
            String sha1 = DigestUtils.sha1Hex(Files.readAllBytes(f.toPath()));
            getLogger().trace(
                    String.format("SHA1[%s]=%s<>%s (%b)", file.localPath, sha1, file.sha1, file.sha1.equals(sha1)));
            return file.sha1.equals(sha1);
        } catch (IOException e) {
            getLogger().error(String.format("Could not compute SHA1 for file %s", file.localPath));
            return false;
        }
    }

}
