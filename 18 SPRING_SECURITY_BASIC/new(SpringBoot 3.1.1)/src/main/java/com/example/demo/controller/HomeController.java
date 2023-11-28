package com.example.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "index";
    }
    @GetMapping("/user")
    public void userpage(){

    }
    @GetMapping("/member")
    public void memberpage(){

    }
    @GetMapping("/admin")
    public void adminpage(){
    }

}
