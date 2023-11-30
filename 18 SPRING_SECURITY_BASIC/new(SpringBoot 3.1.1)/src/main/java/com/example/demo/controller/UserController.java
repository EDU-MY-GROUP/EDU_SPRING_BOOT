package com.example.demo.controller;


import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.type.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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



    @GetMapping("/join")
    public void join_get() {
        log.info("GET /join");
    }

    @PostMapping("/join")
    public String join_post(UserDto dto) {
        log.info("POST /join "+dto);

        //01    파라미터 받기

        //02    입력값 검증(Validation Check)

        //03    서비스 실행
        User user = User.builder()
                .username(dto.getUsername())
                .password( passwordEncoder.encode(dto.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        //04 VIEW 이동
        return "redirect:login?msg=Join_Success!";

    }



}
