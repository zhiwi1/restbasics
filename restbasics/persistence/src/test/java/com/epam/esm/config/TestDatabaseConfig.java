package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@Import(DatabaseConfig.class)
//?
@ComponentScan("com.epam.esm")
public class TestDatabaseConfig {

    @Value("UTF-8")
    private String encoding;
    @Value("classpath:script/create_tags.sql")
    private String tagsCreatePath;
    @Value("classpath:script/create_giftcertificates.sql")
    private String giftCertificatesCreatePath;
    @Value("classpath:script/create_certificate_tags.sql")
    private String createCertificateTagsPath;
    @Value("classpath:script/tags_fill_up.sql")
    private String tagsFillUpPath;
    @Value("classpath:script/giftcertificates_fill_up.sql")
    private String certificatesFillUpPath;
    @Value("classpath:script/certificate_tags_fill_up.sql")
    private String certificateTagsCertificateTagsPath;

    @Profile("dev")
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding(encoding)
                .addScript(tagsCreatePath)
                .addScript(giftCertificatesCreatePath)
                .addScript(createCertificateTagsPath)
                .addScript(tagsFillUpPath)
                .addScript(certificatesFillUpPath)
                .addScript(certificateTagsCertificateTagsPath)
                .build();
    }


}
