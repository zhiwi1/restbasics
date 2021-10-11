package com.epam.esm.config;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@lombok.Value
@PropertySource(value = "classpath:config.properties", encoding = "UTF-8")
public class WebConfigParam {
    @Value("${cookie_max_age:4800}")
    private final int cookieMaxAge;
    @Value("${encoding:UTF-8}")
    private final String encoding;
    @Value("${filename_of_i18n_message:exception_message}")
    private final String filename;
    @Value("${default_locale:en}")
    private final String languageOfLocale;
    @Value("${cookie_name:locale}")
    private final String cookieName;
    @Value("${interceptor_name:lang}")
    private final String interceptorName;
}
