package com.example.demo.controller;

import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.type.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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
                .role(Role.ROLE_USER.toString())
                .build();


        userRepository.save(user);

        //04
        return "redirect:login?msg=Join_Success!";

    }

}
