/**
 * MC Version DTO descriptor
 * Type : Logging configuration
 * Author: Meltwin
 */
package io.meltwin.scyblaster.minecraft.version;

class DTOLogging {
    class LogProxy {
        public String argument;
        public DTOFile file;
        public String type;
    }

    public LogProxy client;
}
