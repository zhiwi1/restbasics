package com.epam.esm.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.epam.esm"})
public class WebConfig implements WebMvcConfigurer {
    @Value("UTF-8")
    private String encoding;
    @Value("exception_message")
    private String fileName;
    @Value("en")
    private String languageOfLocale;
    @Value("locale")
    private String cookieName;
    @Value("lang")
    private String langParamName;
    private static final int COOKIE_MAX_AGE = 4800;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale(languageOfLocale));
        resolver.setCookieName(cookieName);
        resolver.setCookieMaxAge(COOKIE_MAX_AGE);
        resolver.setDefaultTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(langParamName);
        registry.addInterceptor(interceptor);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(fileName);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding(encoding);
        return messageSource;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}