package com.example.demo.domain.repository;

import com.example.demo.domain.entity.User;
import com.example.demo.type.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void t1(){
        //User user = new User("example@example.com","1234", Role.ROLE_USER);
        //userRepository.save(user);

    }

}