package io.meltwin.scyblaster.common.files.types;

import org.jetbrains.annotations.NotNull;

public class ResourceFile {
    public final String distPath;
    public final FileType type;
    public final String localPath;
    public final String sha1;
    public final boolean forceFownload;
    public ResourceStatus status = ResourceStatus.WAITING;

    public ResourceFile(@NotNull String distPath, FileType type, @NotNull String localPath, @NotNull String sha1,
            boolean forceDownload) {
        this.distPath = distPath;
        this.type = type;
        this.localPath = localPath;
        this.sha1 = sha1;
        this.forceFownload = forceDownload;
    }

    public ResourceFile(@NotNull String distPath, FileType type, @NotNull String localPath, boolean forceDownload) {
        this.distPath = distPath;
        this.type = type;
        this.localPath = localPath;
        this.sha1 = null;
        this.forceFownload = forceDownload;
    }
}
