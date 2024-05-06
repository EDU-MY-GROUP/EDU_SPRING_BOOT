package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    // 사용자 이름으로 사용자 조회
    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findByUsername(String id);

    // 사용자 역할이 주어진 역할과 일치하는 사용자 조회
    @Query("SELECT u FROM User u WHERE u.role = ?1")
    List<User> findByRole(String role);

    // 사용자 ID와 패스워드로 사용자 조회
    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.pw = ?2")
    User findByIdAndPassword(String id, String password);


}
