package com.ms.hotel.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("User Not Found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
