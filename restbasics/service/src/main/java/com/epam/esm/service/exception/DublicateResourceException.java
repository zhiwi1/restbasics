package com.epam.esm.service.exception;

public class DublicateResourceException extends ServiceException {
    public DublicateResourceException(String message, int code) {
        super(message, code);
    }
}
