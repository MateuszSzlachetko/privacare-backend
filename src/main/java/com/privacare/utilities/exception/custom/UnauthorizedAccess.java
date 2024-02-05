package com.privacare.utilities.exception.custom;

public class UnauthorizedAccess extends RuntimeException{
    public UnauthorizedAccess(String message){
        super(message);
    }
}
