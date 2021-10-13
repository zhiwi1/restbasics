package com.epam.esm.config;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class WebConfigParam {
    @Value("${encoding:UTF-8}")
    private String encoding;
    @Value("${filename_of_i18n_message:exception_message}")
    private String filename;
    @Value("${default_locale:ru}")
    private String defaultLocale;
    @Value("${second_locale:en}")
    private String secondLocale;
    @Value("${interceptor_name:lang}")
    private String interceptorName;
}
