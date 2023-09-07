package com.example.demo.domain.entity;


import com.fasterxml.jackson.annotation.JsonTypeId;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.annotation.processing.Generated;

//@Entity
public class Board {
    //@Id
    //Auto_increment 설정
    private String no;
    private String username;
    private String title;
    private String content;
    private String regdate;
    private String count;
    private String dirpath;
    private String filename;
    private String filesize;


}


