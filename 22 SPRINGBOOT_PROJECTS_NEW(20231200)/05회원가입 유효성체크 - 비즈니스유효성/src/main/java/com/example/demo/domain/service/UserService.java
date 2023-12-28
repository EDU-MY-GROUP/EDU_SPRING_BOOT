package com.example.demo.domain.service;


import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean memberJoin(UserDto dto, Model model){




        //비지니스 Validation Check
        //password vs repasswod 비교
        if(!dto.getPassword().equals(dto.getRepassword())){
            model.addAttribute("password","패스워드가 일치하지 않습니다.");
            model.addAttribute("repassword","패스워드가 일치하지 않습니다.");
            return false;
        }

        //이메일 인증여부 확인 (

        //이외에 체크할것들 비교..

        //Dto->Entity
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setRole("ROLE_USER");

        //Db Saved...
        userRepository.save(user);

        return userRepository.existsById(user.getUsername());
    }


}
