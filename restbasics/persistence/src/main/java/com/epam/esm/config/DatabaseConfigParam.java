package com.epam.esm.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class DatabaseConfigParam {
    @Value("${poolSize:10}")
    private String poolSize;
    @Value("${driver:com.mysql.cj.jdbc.Driver}")
    private String driver;
    @Value("${url:jdbc:mysql://localhost:3306/mydb}")
    private String url;
    @Value("${user}")
    private String username;
    @Value("${password}")
    private String password;
}
