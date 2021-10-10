package com.epam.esm.exception;

import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.ExceptionMessageKey;
import com.epam.esm.service.exception.ResourceNotFoundException;

import com.epam.esm.service.exception.DublicateResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
class GlobalExceptionHandler {
    private final ExceptionMessageCreator exceptionMessageCreator;

    @Autowired
    public GlobalExceptionHandler(ExceptionMessageCreator exceptionMessageCreator) {
        this.exceptionMessageCreator = exceptionMessageCreator;
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Set<ExceptionHandlerResponse> onConstraintValidationException(
            ConstraintViolationException e, Locale locale) {
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        return set.stream().map(violation -> exceptionMessageCreator.
                        createMessage(violation.getMessage(), locale))
                .map(message -> new ExceptionHandlerResponse(
                        ExceptionCode.INCORRECT_PARAMETER_VALUE, message))
                .collect(Collectors.toSet());

    }
    @ExceptionHandler(DublicateResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionHandlerResponse handleDublicateResourceException(
            DublicateResourceException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(e.getMessageKey(),
                locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(e.getCode(), exceptionMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Set<ExceptionHandlerResponse> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e, Locale locale) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(error->exceptionMessageCreator.createMessage(error.getDefaultMessage(), locale))
                .map(message -> new ExceptionHandlerResponse(
                        ExceptionCode.INCORRECT_PARAMETER_VALUE, message))
                .collect(Collectors.toSet());
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionHandlerResponse handleResourceNotFoundException(
            ResourceNotFoundException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(e.getMessageKey(),
                locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(e.getCode(), exceptionMessage);
    }


    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ExceptionHandlerResponse handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.UNSUPPORTED_MEDIA_TYPE, locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(ExceptionCode.UNSUPPORTED_MEDIA_TYPE, exceptionMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionHandlerResponse handleRuntimeException(RuntimeException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.INTERNAL_ERROR_KEY, locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(ExceptionCode.INTERNAL_ERROR, exceptionMessage);
    }

}

