package com.api.votingsession.Utility.CustomException;

public class RestExceptionHandler extends RuntimeException {
    //    private static final long serialVersionUID = -8440752361452977631L;
    public RestExceptionHandler(String message) {
        super(message);
    }
}
