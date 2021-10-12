package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class DublicateResourceException extends ServiceException {
    private final String entityName;

    public DublicateResourceException(String entityName) {
        super();
        this.entityName = entityName;
    }

    @Override
    public String getErrorMessageKey() {
        return ExceptionMessageKey.INVALID_INPUT;
    }

    @Override
    public int getCode() {
        return ExceptionCode.INCORRECT_PARAMETER_VALUE;
    }
}
