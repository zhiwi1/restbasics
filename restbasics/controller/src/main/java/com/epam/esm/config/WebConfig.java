package com.epam.esm.config;


import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WebConfigParam webConfigParam;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale(webConfigParam.getLanguageOfLocale()));
        resolver.setCookieName(webConfigParam.getCookieName());
        resolver.setCookieMaxAge(webConfigParam.getCookieMaxAge());
        resolver.setDefaultTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(webConfigParam.getInterceptorName());
        registry.addInterceptor(interceptor);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(webConfigParam.getFilename());
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding(webConfigParam.getEncoding());
        return messageSource;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}