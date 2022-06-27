package com.wallet.exception;

public class InsufficientBalanceException extends  RuntimeException{

    private static final long serialVersionUID = 6160707086806435675L;

    public InsufficientBalanceException(){ }

    public InsufficientBalanceException(String s){
        super(s);
    }
}
