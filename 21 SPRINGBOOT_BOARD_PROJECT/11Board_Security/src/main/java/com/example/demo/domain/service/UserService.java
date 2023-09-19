package com.example.demo.domain.service;


import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.io.File;

@Service
@Slf4j
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public boolean addUser(UserDto dto, Model model){

        //프로필 파일 기본경로 설정
        File profileFile = new File("/images/profile/account.png");
        System.out.println("PROFILE FILEPATH : " + profileFile.getAbsolutePath());
        System.out.println("PROFILE FILENAME : " + profileFile.getName());

        User user = User.builder()
                .username(dto.getUsername())
                .password( passwordEncoder.encode(dto.getPassword()))
                .role("ROLE_USER")
                .build();

        user =  userRepository.save(user);


        return user!=null;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean joinMember(UserDto dto, Model model)
    {
        if(!dto.getPassword().equals(dto.getRepassword()))
        {
            model.addAttribute("repassword","패스워드가 일치하지 않습니다");
            return false;
        }
        dto.setRole("ROLE_USER");
        dto.setPassword(passwordEncoder.encode(dto.getPassword()) );

        return true;
    }

}
