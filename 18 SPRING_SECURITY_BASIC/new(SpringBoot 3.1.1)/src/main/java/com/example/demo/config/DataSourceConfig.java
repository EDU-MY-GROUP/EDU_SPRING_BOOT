package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataSourceConfig {

	//	HikariCP DataSource
    @Bean(name="datasource")
    public HikariDataSource dataSource()
    {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testDb");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");

        return dataSource;
    }

    @Bean(name="datasource2")
    public HikariDataSource dataSource2()
    {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testDb");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");

        return dataSource;
    }

}
