package com.example.demo.services;

public class AnimalNotFoundException extends Throwable{
    public AnimalNotFoundException(String message) {
        super(message);
    }
}
