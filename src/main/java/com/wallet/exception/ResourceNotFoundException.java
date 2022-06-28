package com.wallet.exception;

    public class ResourceNotFoundException extends RuntimeException {

        private static final long serialVersionUID = -6189862692141368904L;
        public ResourceNotFoundException(){ }
        public ResourceNotFoundException(String s){
            super(s);
        }
    }

