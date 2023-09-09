package com.example.demo.domain.entity;


import com.fasterxml.jackson.annotation.JsonTypeId;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

//@Entity
public class Board {
    //@Id
    //Auto_increment 설정
    private Long no;
    private String username;
    private String title;
    private String content;
    private LocalDate regdate;
    private String count;
    private String dirpath;
    private String filename;
    private String filesize;


}


