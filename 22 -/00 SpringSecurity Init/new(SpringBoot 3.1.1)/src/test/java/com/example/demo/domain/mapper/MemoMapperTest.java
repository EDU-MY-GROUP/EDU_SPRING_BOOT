package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.MemoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoMapperTest {

    @Autowired
    private MemoMapper memoMapper;

    @Test
    public void test() {
        System.out.println(memoMapper);
        memoMapper.insert(new MemoDto(2,"aaaaa"));
    }


    @Test
    public void t1(){
        MemoDto dto = memoMapper.FindByIdXML(2);
        System.out.println(dto);
    }


}








