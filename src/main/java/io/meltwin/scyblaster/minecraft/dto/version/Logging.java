/**
 * MC Version DTO descriptor
 * Type : Logging configuration
 * Author: Meltwin
 */
package io.meltwin.scyblaster.minecraft.dto.version;

public class Logging {
    class LogProxy {
        public String argument;
        public File file;
        public String type;
    }

    public LogProxy client;
}
