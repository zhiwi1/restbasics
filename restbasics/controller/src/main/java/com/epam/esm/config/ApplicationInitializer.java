package com.epam.esm.config;

import org.springframework.web.WebApplicationInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.active", "prod");
    }
}