package com.example.demo.domain.repository;


import com.example.demo.domain.entity.ImageBoardFileInfo;
import com.example.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {



}
