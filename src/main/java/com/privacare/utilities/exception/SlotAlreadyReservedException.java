package com.privacare.utilities.exception;

public class SlotAlreadyReservedException extends RuntimeException{
    public SlotAlreadyReservedException(String message){
        super(message);
    }
}
