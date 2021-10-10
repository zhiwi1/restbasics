package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:property/database.properties", encoding = "UTF-8")
public class DatabaseConfig {

    private final Environment environment;

    @Autowired
    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }
    @Profile("prod")
    @Bean
    public DataSource dataSource() {
        var config = new HikariConfig();
        config.setDriverClassName(environment.getProperty("driver"));
        config.setJdbcUrl(environment.getProperty("url"));
        config.setUsername(environment.getProperty("user"));
        config.setPassword(environment.getProperty("password"));
        String poolSize=environment.getProperty("poolSize");
        if ( poolSize != null) {
            config.setMaximumPoolSize(Integer.parseInt(poolSize));
        }
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}

