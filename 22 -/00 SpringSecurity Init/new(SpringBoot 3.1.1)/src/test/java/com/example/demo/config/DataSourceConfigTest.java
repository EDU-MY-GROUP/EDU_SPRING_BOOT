package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataSourceConfigTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void v1(){
        System.out.println(dataSource);
    }

}