package com.example.demo.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
@Builder
public class User {
    //---------------------
    // OAUTH
    //---------------------
    private String provider;
    private String providerId;
    @Builder
    public User(String username, String password,String role,String provider, String providerId,String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
    }
    //---------------------
    //기존
    //---------------------
    @Id
    private String username; //이름
    private String password; //비밀번호
    private String email;
    private String role; //권한번호



}
