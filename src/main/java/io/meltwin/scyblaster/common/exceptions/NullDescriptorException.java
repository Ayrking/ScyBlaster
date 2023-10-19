package io.meltwin.scyblaster.common.exceptions;

public class NullDescriptorException extends ScyblasterException {

    private static final String EXCEPTION_MSG = "Couldn't proceed to the operation as the Version Descriptor is null !";

    public NullDescriptorException() {
        super(EXCEPTION_MSG);
    }

}
