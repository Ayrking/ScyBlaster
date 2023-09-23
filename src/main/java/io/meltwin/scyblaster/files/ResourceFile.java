package io.meltwin.scyblaster.files;

import org.jetbrains.annotations.NotNull;

public class ResourceFile {
    public final String distPath;
    public final FileType type;
    public final String localPath;
    public ResourceStatus status = ResourceStatus.WAITING;

    public ResourceFile(@NotNull String distPath, FileType type, @NotNull String localPath) {
        this.distPath = distPath;
        this.type = type;
        this.localPath = localPath;
    }
}
