package com.example.demo.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    //---------------------
    // OAUTH
    //---------------------
    private String provider;
    private String providerId;

    //---------------------
    //기존
    //---------------------
    @Id
    private String username; //이름
    private String password; //비밀번호
    private String role; //권한번호
    private String phone;
    private String zipcode;
    private String addr1;
    private String addr2;


}
