package io.meltwin.scyblaster.common;

import org.jetbrains.annotations.NotNull;

import io.meltwin.scyblaster.common.io.LocalFileHandler;
import io.meltwin.scyblaster.common.io.ResourceInterface;

public class HTTPHandler extends LocalFileHandler<String> {

    public HTTPHandler(@NotNull final ResourceInterface resource) {
        super(resource);
    }

    @Override
    public String get_data() {
        return "";
    }

}
