package io.meltwin.scyblaster.common.resources.types;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.types.Logging;

public class ResourceFile implements Logging, Serializable {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResourceFile{");
        builder.append(String.format("type=%s", this.status));
        builder.append(",dist=" + ((this.distPath == null) ? "null" : this.distPath.toString()));
        builder.append(",local=" + ((this.localPath == null) ? "null" : this.localPath.toString()));
        builder.append(",sha1=" + ((this.sha1 == null) ? "" : this.sha1));
        builder.append(String.format(",force=%b", this.forceFownload));
        builder.append("}");

        return builder.toString();
    }
}
