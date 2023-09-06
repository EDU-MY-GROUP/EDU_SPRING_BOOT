package com.example.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/list")
    public void List(){
        log.info("GET /board/list");
    }
    @GetMapping("/post")
    public void post(){
        log.info("GET /board/post");
    }
    @GetMapping("/put")
    public void put(){
        log.info("GET /board/put");
    }
    @GetMapping("/delete")
    public void delete(){
        log.info("GET /board/delete");
    }

}
