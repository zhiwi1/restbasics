package com.epam.esm.exception;

public class ResourceNotFoundException extends ServiceException {
    public ResourceNotFoundException(String messageKey, int code) {
        super(messageKey);
    }

    @Override
    public String getMessageKey() {
        return ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY;
    }

    @Override
    public int getCode() {
        return ExceptionCode.RESOURCE_NOT_FOUND;
    }
}
