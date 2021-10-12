package com.epam.esm.config;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class WebConfigParam {
    @Value("${cookie_max_age:4800}")
    private String cookieMaxAge;
    @Value("${encoding:UTF-8}")
    private String encoding;
    @Value("${filename_of_i18n_message:exception_message}")
    private String filename;
    @Value("${default_locale:en}")
    private String languageOfLocale;
    @Value("${cookie_name:locale}")
    private String cookieName;
    @Value("${interceptor_name:lang}")
    private String interceptorName;
}
