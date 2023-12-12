package com.example.demo.domain.repository;


import com.example.demo.domain.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Key;

@Repository
public interface JwtRepository extends JpaRepository<Signature, Key> {
}
