/**
 * MC Version DTO descriptor
 * Type : Version (Root)
 * Author: Meltwin
 */
package io.meltwin.scyblaster.minecraft.dto.version;

public class Version {
    public MCArguments arguments;
    public AssetIndex assetIndex;
    public int assets;
    public int complianceLevel;
    public Downloads downloads;
    public String id;
    public JavaVersion javaVersion;
    public Library[] libraries;
    public Logging logging;
    public String mainClass;
    public int minimumLauncherVersion;
    public String releaseTime;
    public String time;
    public String type;
}
