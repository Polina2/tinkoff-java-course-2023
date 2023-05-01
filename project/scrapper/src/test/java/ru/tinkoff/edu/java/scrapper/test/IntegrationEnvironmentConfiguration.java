package ru.tinkoff.edu.java.scrapper.test;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static ru.tinkoff.edu.java.scrapper.test.IntegrationEnvironment.DB_CONTAINER;

@TestConfiguration
public class IntegrationEnvironmentConfiguration {
    @Bean
    public DataSource testDataSource() {
        return DataSourceBuilder.create()
                .url(DB_CONTAINER.getJdbcUrl())
                .username(DB_CONTAINER.getUsername())
                .password(DB_CONTAINER.getPassword())
                .build();
    }

    @Bean
    public JdbcTemplate testJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
