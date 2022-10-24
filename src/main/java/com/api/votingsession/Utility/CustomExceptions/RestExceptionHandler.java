package com.api.votingsession.Utility.CustomExceptions;

public class RestExceptionHandler extends RuntimeException {

    private static final long serialVersionUID = -8440752361452977631L;
    public RestExceptionHandler(String mensagem ){
        super(mensagem);
    }

}
