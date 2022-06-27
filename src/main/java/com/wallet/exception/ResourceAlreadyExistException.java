package com.wallet.exception;

    public class ResourceAlreadyExistException extends RuntimeException {


        private static final long serialVersionUID = -203939059687582861L;

        public ResourceAlreadyExistException(){

        }
        public ResourceAlreadyExistException(String s){
            super(s);
        }
    }

