package com.example.demo.domain.service;


import com.example.demo.domain.dto.MemoDto;
import com.example.demo.domain.entity.Memo;
import com.example.demo.domain.mapper.MemoMapper;

import com.example.demo.domain.repository.MemoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.Exception;
@Service
@Slf4j
public class TxTestService {

    @Autowired
    private MemoMapper memoMapper;

    @Autowired
    private MemoRepository memoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void txMapper(){
        log.info("[TxTestService] txMapper..");

        memoMapper.insert(new MemoDto(1,"aaaa"));
        memoMapper.insert(new MemoDto(1,"bbbb"));
    }
//    @Transactional(rollbackFor = Exception.class)
    public void txRepository(){
        log.info("[TxTestService] txRepository..");
        memoRepository.save(new Memo(1,"aaaa"));
        memoRepository.save(new Memo(1,"bbbb"));
    }
}
