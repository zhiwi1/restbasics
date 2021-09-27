package com.epam.esm.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;



import javax.sql.DataSource;


@Configuration
@ComponentScan("com.epam.esm")
//@PropertySource("classpath:database.properties")
public class DataSourceConfig {
//cp trans
    @Bean
    public DataSource dataSource() {
        var dataSource = new BasicDataSource();
        //todo
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("root");
        dataSource.setPassword("Qwe123okA123");
        dataSource.setInitialSize(4);
        dataSource.setMaxTotal(10);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
