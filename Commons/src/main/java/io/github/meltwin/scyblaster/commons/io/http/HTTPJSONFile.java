package io.github.meltwin.scyblaster.commons.io.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Small class for opening JSON HTTP File
 *  @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 *  @author Meltwin
 *  @since 0.1-SNAPSHOT
 */
public class HTTPJSONFile extends JSONObject {

    final static Logger logger = LogManager.getRootLogger();
    final static String MSG_FILE_GET = "Downloading file %s";
    final static String MSG_REPONSE_CODE = "Response %d (%s)";
    final static String MSG_ERROR_FILE_NOT_FOUND = "File not found or not accessible. Please check your server configuration.";
    final static String MSG_ERROR_SERVER = "Server error. Please contact your server administrator.";
    final static String MSG_UNKNOWN_ERROR = "Unknown error. Please contact your server administrator and/or the developer.";

    public HTTPJSONFile(final @NotNull URL file_url) { super(getResponse(file_url)); }

    private static String getResponse(final @NotNull URL file_url) {
        logger.info(String.format(MSG_FILE_GET, file_url));

        String out = "{}";
        try {
            HttpURLConnection con = (HttpURLConnection) file_url.openConnection();
            con.setRequestMethod("GET");

            logger.trace(String.format(MSG_REPONSE_CODE, con.getResponseCode(), con.getResponseMessage()));

            int code = con.getResponseCode();
            // Reading data if request is good
            if (code == 200) {
                out = readInputStream(con.getInputStream());
            }
            else {
                switch (code) {
                    case 403:
                    case 404:
                        logger.error(MSG_ERROR_FILE_NOT_FOUND);
                        break;
                    case 500:
                    case 501:
                    case 502:
                    case 503:
                        logger.error(MSG_ERROR_SERVER);
                        break;
                    default:
                        logger.error(MSG_UNKNOWN_ERROR);
                        logger.error(readInputStream(con.getErrorStream()));
                        break;
                }
            }

            con.disconnect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    static String readInputStream(final @NotNull InputStream stream) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuilder buffer = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            buffer.append(inputLine.trim());
        in.close();
        return buffer.toString();
    }
}
