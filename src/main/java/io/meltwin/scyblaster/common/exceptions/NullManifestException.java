package io.meltwin.scyblaster.common.exceptions;

public class NullManifestException extends ScyblasterException {

    private static final String EXCEPTION_MSG = "Couldn't proceed to the operation as the Version Manifest is null !";

    public NullManifestException() {
        super(EXCEPTION_MSG);
    }
}
