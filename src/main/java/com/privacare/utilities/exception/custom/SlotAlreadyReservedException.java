package com.privacare.utilities.exception.custom;

public class SlotAlreadyReservedException extends RuntimeException {
    public SlotAlreadyReservedException(String message) {
        super(message);
    }
}
