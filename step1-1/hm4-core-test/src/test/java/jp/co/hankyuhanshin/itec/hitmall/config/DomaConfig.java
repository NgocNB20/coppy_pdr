package jp.co.hankyuhanshin.itec.hitmall.config;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class DomaConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5437/hm4");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }

}
