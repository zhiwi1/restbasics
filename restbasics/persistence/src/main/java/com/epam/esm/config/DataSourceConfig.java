package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
public class DataSourceConfig {

    private static final String RELATIVE_PATH_TO_PROPERTIES = "database";

    private static final String URL_PROPERTY = "url";

    private static final String USERNAME_PROPERTY = "username";

    private static final String PASSWORD_PROPERTY = "password";

    private static final String DRIVER_PROPERTY = "driver";

    private static final String DATABASE_URL;

    private static final String DATABASE_USERNAME;

    private static final String DATABASE_PASSWORD;

    private static final String DATABASE_DRIVER;

    private static final ResourceBundle properties;

    static {
        properties = ResourceBundle.getBundle(RELATIVE_PATH_TO_PROPERTIES);
        DATABASE_URL = properties.getString(URL_PROPERTY);
        DATABASE_DRIVER = properties.getString(DRIVER_PROPERTY);
        DATABASE_USERNAME = properties.getString(USERNAME_PROPERTY);
        DATABASE_PASSWORD = properties.getString(PASSWORD_PROPERTY);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DATABASE_DRIVER);
        dataSource.setUrl(DATABASE_URL);
        dataSource.setUsername(DATABASE_USERNAME);
        dataSource.setPassword(DATABASE_PASSWORD);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
