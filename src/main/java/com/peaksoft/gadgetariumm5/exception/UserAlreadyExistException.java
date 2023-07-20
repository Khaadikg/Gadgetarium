package com.peaksoft.gadgetariumm5.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException (String massage){
        super(massage);
    }
}
