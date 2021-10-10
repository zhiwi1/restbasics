package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ExceptionMessageCreator {

    private final MessageSource messageSource;

    @Autowired
    public ExceptionMessageCreator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String createMessage(String exceptionMessageKey,  Locale locale) {
        return messageSource.getMessage(exceptionMessageKey, new Object[]{}, locale);
    }
}