package com.example.demo.repository;

import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class C03UserRepositoryQueryAnnotationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testInsertUser() {
        User user = new User();
        user.setId("user1");
        user.setPw("password1");
        user.setUsername("John");
        user.setRole("ROLE_USER");

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(user.getId(), savedUser.getId());
    }

    @Test
    @Transactional
    public void testInsertMultipleUsers() {
        for (int i = 1; i <= 50; i++) {
            User user = new User();
            user.setId("user" + i);
            user.setPw("password" + i);
            user.setUsername("User " + i);
            user.setRole("ROLE_USER");

            userRepository.save(user);
        }

        List<User> users = userRepository.findAll();
        Assertions.assertEquals(50, users.size());
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        // Assuming a user with id "user1" already exists in the database
        User user = userRepository.findById("user1").orElse(null);
        Assertions.assertNotNull(user);

        user.setUsername("Updated User");
        userRepository.save(user);

        User updatedUser = userRepository.findById("user1").orElse(null);
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("Updated User", updatedUser.getUsername());
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        // Assuming a user with id "user1" already exists in the database
        User user = userRepository.findById("user1").orElse(null);
        Assertions.assertNotNull(user);

        userRepository.delete(user);

        User deletedUser = userRepository.findById("user1").orElse(null);
        Assertions.assertNull(deletedUser);
    }


}