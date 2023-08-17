package com.example.demo.config.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleController {

    // JSP TEST
    @GetMapping("/index")
    public void join_form(){
        System.out.println("JOIN!");
    }


    // THYMELEAF TEST
    @GetMapping("/thymeleaf/index")
    public void a_form(){
        System.out.println("a!");
    }

}
