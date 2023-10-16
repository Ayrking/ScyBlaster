package io.meltwin.scyblaster.common.resources.types;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.types.Logging;

public class ResourceFile implements Logging {
    public final URL distPath;
    public final ResourceType type;
    public final Path localPath;
    public final String sha1;
    public final boolean forceFownload;
    public ResourceStatus status = ResourceStatus.WAITING;

    public ResourceFile(@NotNull String distPath, ResourceType type, @NotNull String localPath, @NotNull String sha1,
            boolean forceDownload) {
        // Setting the dist URL
        URL distURL;
        try {
            distURL = new URL(distPath);
        } catch (MalformedURLException e) {
            log().error(String.format("The ResourceFile's distant URL (%s) is malformed !", distPath));
            distURL = null;
        }

        this.distPath = distURL;
        this.type = type;
        this.localPath = Paths.get(localPath);
        this.sha1 = sha1;
        this.forceFownload = forceDownload;

    }

    public ResourceFile(@NotNull String distPath, ResourceType type, @NotNull String localPath, boolean forceDownload) {
        URL distURL;
        try {
            distURL = new URL(distPath);
        } catch (MalformedURLException e) {
            log().error(String.format("The ResourceFile's distant URL (%s) is malformed !", distPath));
            distURL = null;
        }

        this.distPath = distURL;
        this.type = type;
        this.localPath = Paths.get(localPath);
        this.sha1 = null;
        this.forceFownload = forceDownload;
    }
}
