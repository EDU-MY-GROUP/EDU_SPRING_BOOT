package com.example.demo.domain.service;


import com.example.demo.domain.dto.MemoDto;
import com.example.demo.domain.entity.Memo;
import com.example.demo.domain.mapper.MemoMapper;

import com.example.demo.domain.repository.MemoRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Slf4j
public class TxTestService {
    @Autowired
    private MemoMapper memoMapper;
    @Transactional(rollbackFor = Exception.class,transactionManager = "dataSourceTransactionManager")
    public void txMapper() {
        log.info("[TxTestService] txMapper..");

        memoMapper.insert(new MemoDto(1,"aaaa"));
        memoMapper.insert(new MemoDto(1,"bbbb"));
    }
    @Autowired
    private MemoRepository memoRepository;

    @Transactional(rollbackFor = Exception.class,transactionManager = "jpaTransactionManager")
    public void txRepository() throws Exception {
        log.info("[TxTestServic] txRepository..");

        memoRepository.save(new Memo(1,"aaaa"));
        throw new Exception();
        //memoRepository.save(new Memo(2,"bbbb"));
    }


}
