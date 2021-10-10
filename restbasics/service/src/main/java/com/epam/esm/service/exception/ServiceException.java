package com.epam.esm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ServiceException extends RuntimeException {
    private final String messageKey;
    private final int code;
}
