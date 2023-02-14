package com.kameleoon.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public <T> NotFoundException(Class<T> cls, Long id) {
        super(cls.getSimpleName() + " with id: " + id + " does not exist!");

    }
}
