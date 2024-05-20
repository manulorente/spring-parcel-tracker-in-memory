package com.dam.parcelmanagement.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Constructor que recibe un mensaje y lo pasa a la superclase RuntimeException
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
