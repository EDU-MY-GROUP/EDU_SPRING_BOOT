package com.example.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    //-------------------
    //-------------------
    @GetMapping("/list")
    public void list(){log.info("GET /board/list");}
    //-------------------
    //-------------------
    @GetMapping("/read")
    public void read(){log.info("GET /board/read");}
    //-------------------
    //-------------------
    @GetMapping("/post")
    public void post(){log.info("GET /board/post");}

}
