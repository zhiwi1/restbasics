package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class DublicateResourceException extends ServiceException {
    private Long id;
    //message
    public DublicateResourceException( Long id) {
        super(ExceptionMessageKey.INVALID_INPUT);
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
