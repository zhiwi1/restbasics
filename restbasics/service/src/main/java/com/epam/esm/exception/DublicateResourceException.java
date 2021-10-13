package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class DublicateResourceException extends ServiceException {

    public DublicateResourceException(String entityName) {
        super(entityName);
    }

    @Override
    public String getErrorMessageKey() {
        return ExceptionMessageKey.INVALID_INPUT_WITH_PARAM;
    }

    @Override
    public int getCode() {
        return ExceptionCode.INCORRECT_PARAMETER_VALUE;
    }
}
