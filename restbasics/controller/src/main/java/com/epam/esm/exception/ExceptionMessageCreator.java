package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExceptionMessageCreator {

    private final MessageSource messageSource;

    @Autowired
    public ExceptionMessageCreator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String createMessage(String exceptionMessageKey, Locale locale, Object arg) {
        return messageSource.getMessage(exceptionMessageKey, new Object[]{arg}, locale);
    }
    public Set<String> createMessage(String exceptionMessageKey, Locale locale, Object... args) {
        return Arrays.stream(args)
                .map(arg -> messageSource.getMessage(exceptionMessageKey, new Object[]{arg}, locale))
                .collect(Collectors.toSet());

    }
    public String createMessage(String exceptionMessageKey, Locale locale) {
        return messageSource.getMessage(exceptionMessageKey,new Object[]{}, locale);
    }

}