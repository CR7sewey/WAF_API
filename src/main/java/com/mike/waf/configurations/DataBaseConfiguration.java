package com.mike.waf.configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;



    @Bean
    @Primary
    public DataSource hikariDataSource() {
        HikariConfig dataSource = new HikariConfig();
        dataSource.setDriverClassName(driver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setMaximumPoolSize(10); // size of pool of connections, maximum
        dataSource.setMinimumIdle(1); // initial size of the pool
        dataSource.setPoolName("library-pool");
        dataSource.setMaxLifetime(600000); // 600 mil ms
        dataSource.setConnectionTimeout(100000); // time limit to establish a connection
        dataSource.setConnectionTestQuery("SELECT 1"); // test to check if it is ok

        return new HikariDataSource(dataSource);
    }

}
