package com.epam.esm.exception;


import lombok.Value;

@Value
public class ExceptionResponse {
    int exceptionCode;
    String exceptionMessage;
}

