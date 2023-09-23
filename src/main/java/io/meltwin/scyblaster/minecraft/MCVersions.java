package io.meltwin.scyblaster.minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import io.meltwin.scyblaster.files.FileType;
import io.meltwin.scyblaster.files.ResourceFile;
import io.meltwin.scyblaster.files.ResourceHandler;
import io.meltwin.scyblaster.files.ResourceStatus;

public class MCVersions {

    private static final Logger logger = LogManager.getLogger("MCVersions");
    public static MCVersionsList versionList;

    private MCVersions() {
    }

    public static boolean fetch() {
        logger.info("Retrieving Minecraft versions from distant.");
        try {
            Path tempFile = Files.createTempFile(null, ".json");
            Future<ResourceFile> versionFuture = ResourceHandler.prepareFile(new ResourceFile(
                    "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json",
                    FileType.HTTP, tempFile.toString()));
            ResourceFile mcVersions = versionFuture.get();

            // If we couldn't retrieve the versions
            if (mcVersions.status == ResourceStatus.ERROR) {
                logger.fatal("Could not retrieve distant minecraft versions !");
                return false;
            }

            // Parse the file
            Gson gson = new Gson();
            versionList = gson.fromJson(new String(Files.readAllBytes(tempFile)), MCVersionsList.class);

            return true;
        } catch (IOException e) {
            logger.fatal("Could not create temporary file for minecraft version !");
        } catch (InterruptedException | ExecutionException e) {
            logger.fatal("Interrupted while retrieving MC versions list !");
        }
        return false;
    }

    public static int getVersionID(String version) {
        for (int i = 0; i < versionList.versions.length; i++) {
            if (versionList.versions[i].id.equals(version))
                return i;
        }
        logger.error(String.format("Could not find any version corresponding to %s. Using latest version.", version));
        return 0;
    }
}
