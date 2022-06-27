package com.wallet.exception;

public class StaticFieldUpdateException  extends RuntimeException {

    private static final long serialVersionUID = -9157616256079224146L;

    public StaticFieldUpdateException(){

    }
    public StaticFieldUpdateException(String s){
        super(s);
    }
}
