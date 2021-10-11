package com.epam.esm.exception;

public class DublicateResourceException extends ServiceException {
    private Long id;
    //message
    public DublicateResourceException(String messageKey, Long id) {
        super(messageKey);
        this.id = id;
    }

    @Override
    public String getMessageKey() {
        return ExceptionMessageKey.INVALID_INPUT;
    }

    @Override
    public int getCode() {
        return ExceptionCode.INCORRECT_PARAMETER_VALUE;
    }
}
