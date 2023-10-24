package io.meltwin.scyblaster.minecraft.version;

/**
 * MC Version DTO descriptor
 * Type : Version (Root)
 * 
 * @author Meltwin
 */
class DTOVersion {
    // Version details
    public final String id = null; // Version Name (e.g. 1.12.2)
    public final int minimumLauncherVersion = -1;
    public final String releaseTime = null;
    public final String time = null;
    /**
     * Type of the release
     * 
     * @see https://meltwin.github.io/Scyblaster-Data/formats/version_list/#version-type
     *      for more informations
     */
    public final String type = null;

    public final int complianceLevel = -1;
    public final DTOJavaVersion javaVersion = null;

    // Arguments
    public final String mainClass = null; // The main Java class to launch
    public final DTOArgumentsList arguments = null; // Post F10 (MC 1.12 -> latest)
    public final String minecraftArguments = null; // Pre F10 (MC Alpha -> 1.12)

    // Assets Index (MC Alpha -> latest)
    public final DTOAssetIndex assetIndex = null;
    public final String assets = null;

    // Libraries / Client / Server / logging configuration
    public final DTODownloads downloads = null; // Client / Server URI
    public final DTOLibrary[] libraries = null;
    public final DTOLogging logging = null;
}
