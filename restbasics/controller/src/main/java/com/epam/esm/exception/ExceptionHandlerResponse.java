package com.epam.esm.exception;


import lombok.Value;

@Value
public class ExceptionHandlerResponse {
    int exceptionCode;
    String exceptionMessage;
}

