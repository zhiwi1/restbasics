package com.epam.esm.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
@ComponentScan("com.epam.esm")
//@PropertySource("classpath:database.properties")
public class DataSourceConfig {
//
//    private static final String RELATIVE_PATH_TO_PROPERTIES = "database";
//
//    private static final String URL_PROPERTY = "url";
//
//    private static final String USERNAME_PROPERTY = "username";
//
//    private static final String PASSWORD_PROPERTY = "password";
//
//    private static final String DRIVER_PROPERTY = "driver";
//
//    private static final String INITIAL_SIZE_PROPERTY = "initial_size";
//
//    private static final String MAX_TOTAL_PROPERTY = "max_total";
//
//    private static final int INITIAL_SIZE;
//
//    private static final int MAX_TOTAL;
//
//    private static final String DATABASE_URL;
//
//    private static final String DATABASE_USERNAME;
//
//    private static final String DATABASE_PASSWORD;
//
//    private static final String DATABASE_DRIVER;
//
//    private static final ResourceBundle properties;
//
//    static {
//        properties = ResourceBundle.getBundle(RELATIVE_PATH_TO_PROPERTIES);
//        DATABASE_URL = properties.getString(URL_PROPERTY);
//        DATABASE_DRIVER = properties.getString(DRIVER_PROPERTY);
//        DATABASE_USERNAME = properties.getString(USERNAME_PROPERTY);
//        DATABASE_PASSWORD = properties.getString(PASSWORD_PROPERTY);
//        INITIAL_SIZE = (int) properties.getObject(INITIAL_SIZE_PROPERTY);
//        MAX_TOTAL = (int) properties.getObject(MAX_TOTAL_PROPERTY);
//    }
//
    @Bean
    public DataSource dataSource() {
        var dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("root");
        dataSource.setPassword("Qwe123okA123");
        dataSource.setInitialSize(4);
        dataSource.setMaxTotal(10);
        return dataSource;
    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }
//@Bean
//public DataSource dataSource() {
//    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//    dataSource.setDriverClassName();
//    dataSource.setUrl();
//    dataSource.setUsername();
//    dataSource.setPassword();
//
//    return dataSource;
//}

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
