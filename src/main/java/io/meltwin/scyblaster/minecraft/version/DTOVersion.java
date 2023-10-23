/**
 * MC Version DTO descriptor
 * Type : Version (Root)
 * Author: Meltwin
 */
package io.meltwin.scyblaster.minecraft.version;

class DTOVersion {
    // Arguments
    public DTOArgumentsList arguments; // Post F10 (>~ MC 1.12)
    public String minecraftArguments; // Pre F10 (<~ MC 1.12)

    public DTOAssetIndex assetIndex;
    public String assets;
    public int complianceLevel;
    public DTODownloads downloads;
    public String id;
    public DTOJavaVersion javaVersion;
    public DTOLibrary[] libraries;
    public DTOLogging logging;
    public String mainClass;
    public int minimumLauncherVersion;
    public String releaseTime;
    public String time;
    public String type;
}
