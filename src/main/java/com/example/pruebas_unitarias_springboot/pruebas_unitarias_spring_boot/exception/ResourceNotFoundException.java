package com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
