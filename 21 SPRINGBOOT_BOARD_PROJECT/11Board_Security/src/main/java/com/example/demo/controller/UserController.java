package com.example.demo.controller;

import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/mypage")
    public void user(Authentication authentication) {
        log.info("GET /user/mypage...");
        System.out.println("authentication : " + authentication);
        System.out.println("name : " + authentication.getName());
        System.out.println("principal : " + authentication.getPrincipal());
        System.out.println("authorities : " + authentication.getAuthorities());
        System.out.println("detail : " + authentication.getDetails());
        System.out.println("credential : " + authentication.getCredentials());

    }





    @GetMapping("/error")
    public void error() {
        log.info("GET /error...");
    }


    @GetMapping("/login")
    public void login(){
        log.info("GET /user/login");
    }


    @GetMapping("/join")
    public void join_get() {
        log.info("GET /join");
    }

    @PostMapping("/join")
    public String join_post(UserDto dto) {
        log.info("POST /join "+dto);

        //01

        //02

        //03

        User user = User.builder()
                .username(dto.getUsername())
                .password( passwordEncoder.encode(dto.getPassword()))
                .role("ROLE_USER")
                .build();


        userRepository.save(user);

        //04
        return "redirect:login?msg=Join_Success!";

    }


}
