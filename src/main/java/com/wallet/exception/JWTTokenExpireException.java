package com.wallet.exception;

public class JWTTokenExpireException extends  RuntimeException{

    private static final long serialVersionUID = -6909310193107855413L;
    public JWTTokenExpireException(){ }
    public JWTTokenExpireException(String s){
        super(s);
    }
}
