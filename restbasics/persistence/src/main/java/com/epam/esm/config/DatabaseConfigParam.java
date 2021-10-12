package com.epam.esm.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@lombok.Value
@PropertySource(value = "classpath:property/database.properties", encoding = "UTF-8")
public class DatabaseConfigParam {
    @Value("${poolSize:10}")
    private final String poolSize;

    @Value("${driver:com.mysql.cj.jdbc.Driver}")
    private final String driver;
    @Value("${url:jdbc:mysql://localhost:3306/mydb}")
    private final String url;
    @Value("${user:root}")
    private final String username;
    @Value("${password:Qwe123okA123}")
    private final String password;
}
