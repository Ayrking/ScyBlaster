package io.meltwin.scyblaster.minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import io.meltwin.scyblaster.files.FileType;
import io.meltwin.scyblaster.files.ResourceFile;
import io.meltwin.scyblaster.files.ResourceHandler;
import io.meltwin.scyblaster.files.ResourceStatus;
import io.meltwin.scyblaster.minecraft.adapter.ArgumentAdapter;
import io.meltwin.scyblaster.minecraft.adapter.RuleAdapter;
import io.meltwin.scyblaster.minecraft.dto.version.Argument;
import io.meltwin.scyblaster.minecraft.dto.version.Rule;
import io.meltwin.scyblaster.minecraft.dto.version.Version;

public class ResManager {
    private Logger logger = LogManager.getLogger("ResManager");
    private int versionID;

    public boolean errored = true;

    private Version versionMetaData;

    public ResManager(int mcVersionID) {
        this.versionID = mcVersionID;
        errored = prepare();
    }

    public boolean prepare() {
        logger.info("Retrieving Minecraft versions from distant.");

        // TODO: Check locally for the file

        try {
            // TODO: Determine real file place
            Path tempFile = Files.createTempFile(null, ".json");
            ResourceFile version_file = ResourceHandler
                    .prepareFile(new ResourceFile(MCVersions.getDistVersionFile(versionID), FileType.HTTP,
                            tempFile.toString()))
                    .get();

            if (version_file.status == ResourceStatus.ERROR) {
                logger.fatal("Could not fetch distant version resources descriptor !");
                return true;
            }

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Rule.class, new RuleAdapter());
            builder.registerTypeAdapter(Argument.class, new ArgumentAdapter());
            Gson gson = builder.create();
            versionMetaData = gson.fromJson(new String(Files.readAllBytes(Paths.get(version_file.localPath))),
                    Version.class);

            return false;

        } catch (InterruptedException | ExecutionException e) {
            logger.fatal("Interrupted while retrieving MC versions list !");
            return true;
        } catch (JsonSyntaxException e) {
            logger.fatal("There was an error during version metadata file parsing !");
            e.printStackTrace();
            return true;
        } catch (IOException e) {
            logger.fatal("There was an IO error during the version metadata file parsing !");
            e.printStackTrace();
            return true;
        }
    }

    public Version getMetaData() {
        return versionMetaData;
    }
}
