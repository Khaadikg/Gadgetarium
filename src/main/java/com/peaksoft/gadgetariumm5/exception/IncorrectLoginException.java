package com.peaksoft.gadgetariumm5.exception;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException (String massage){
        super(massage);
    }
}
