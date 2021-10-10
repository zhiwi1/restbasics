package com.epam.esm.service.exception;

public class ResourceNotFoundException extends ServiceException {
    public ResourceNotFoundException(String messageKey, int code) {
        super(messageKey, code);
    }
}
