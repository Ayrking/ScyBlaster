package io.meltwin.scyblaster.common.exceptions;

public class NullIndexException extends ScyblasterException {

    private static final String EXCEPTION_MSG = "Couldn't proceed to the operation as the Assets Index is null !";

    public NullIndexException() {
        super(EXCEPTION_MSG);
    }

}
