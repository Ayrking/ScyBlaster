package io.github.meltwin.scyblaster.starter.data;

import io.github.meltwin.scyblaster.starter.Starter;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.System.exit;

/**
 * Small class for opening JSON resource file
 * @author meltwin
 * @since 0.1-SNAPSHOT
 */
public class JsonFile extends JSONObject {

    private static final Logger logger = Starter.log;

    public JsonFile(final @NotNull String filePath) {
        super(readFile(filePath));
    }

    /**
     * Read data from a JSON resource File then return it as a String
     * @param filepath the path to the resource file
     * @return the data in the file as String
     */
    private static String readFile(@NotNull final String filepath) {
        logger.trace(String.format("Reading JSON File : %s", filepath));
        InputStream handle = JsonFile.class.getClassLoader().getResourceAsStream(filepath);
        String out = "";

        try {
            // Read all bytes then make it a string
            assert handle != null;
            byte[] data = new byte[handle.available()];
            handle.read(data, 0, handle.available());
            out = new String(data);
            logger.trace("Read successfully !");
        }
        catch (IOException | NullPointerException e) {
            logger.fatal("Error during loading !");
            e.printStackTrace();
            exit(-1);
        }

        return out;
    }

}
