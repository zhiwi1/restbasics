package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:property/database.properties", encoding = "UTF-8")
public class DatabaseConfig {

  DatabaseConfigParam databaseConfigParam;

    @Profile("prod")
    @Bean
    public DataSource dataSource() {
        var config = new HikariConfig();
        config.setDriverClassName(databaseConfigParam.getDriver());
        config.setJdbcUrl(databaseConfigParam.getUrl());
        config.setUsername(databaseConfigParam.getUsername());
        config.setPassword(databaseConfigParam.getPassword());
        config.setMaximumPoolSize(Integer.parseInt(databaseConfigParam.getPoolSize()));
        return new HikariDataSource(config);
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}

