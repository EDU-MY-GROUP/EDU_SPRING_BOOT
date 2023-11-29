package com.example.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
public class UserController {

    @GetMapping("/login")
    public void login() {
        log.info("GET /user/login...");
    }

    @GetMapping("/user")
    public void user(Authentication authentication, Model model) {
        log.info("GET /user...");
        System.out.println("authentication : " + authentication);
        System.out.println("name : " + authentication.getName());
        System.out.println("principal : " + authentication.getPrincipal());
        System.out.println("authorities : " + authentication.getAuthorities());
        System.out.println("detail : " + authentication.getDetails());
        System.out.println("credential : " + authentication.getCredentials());

        model.addAttribute("authentication", authentication);
    }

    @GetMapping("/member")
    public void memberpage(){
        Authentication authentication =  (Authentication)SecurityContextHolder.getContext().getAuthentication();

        System.out.println("authentication : " + authentication);
        System.out.println("name : " + authentication.getName());
        System.out.println("principal : " + authentication.getPrincipal());
        System.out.println("authorities : " + authentication.getAuthorities());
        System.out.println("detail : " + authentication.getDetails());
        System.out.println("credential : " + authentication.getCredentials());

    }
    @GetMapping("/admin")
    public void adminpage(){
    }

}
