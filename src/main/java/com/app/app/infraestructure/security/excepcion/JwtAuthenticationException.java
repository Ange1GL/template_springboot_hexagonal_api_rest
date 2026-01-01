package com.app.app.infraestructure.security.excepcion;

public class JwtAuthenticationException extends RuntimeException {

    private final JwtErrorCode errorCode;


    public JwtAuthenticationException(JwtErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    public JwtErrorCode getErrorCode() {
        return errorCode;
    }
}
