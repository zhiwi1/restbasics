package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ServiceException {
    private final Long[] id;

    public ResourceNotFoundException(Long... id) {
        super();
        this.id = id;
    }

    @Override
    public String getErrorMessageKey() {
        return ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY;
    }

    @Override
    public int getCode() {
        return ExceptionCode.RESOURCE_NOT_FOUND;
    }
}
