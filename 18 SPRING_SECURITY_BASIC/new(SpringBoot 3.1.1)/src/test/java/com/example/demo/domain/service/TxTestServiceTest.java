package com.example.demo.domain.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TxTestServiceTest {

    @Autowired
    private TxTestService txTestService;

    @Test
    public void t1()throws Exception   {
        txTestService.txMapper();
    }


    @Test
    public void t2() throws Exception {
        txTestService.txRepository();

    }



}