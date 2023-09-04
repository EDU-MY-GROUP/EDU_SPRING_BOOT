package com.example.demo.domain.entity;


import com.example.demo.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User  {


    //---------------------
    // OAUTH
    //---------------------
    private String provider;
    private String providerId;
    @Builder
    public User(String username, String password,Role role,String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
    //---------------------
    //기존
    //---------------------
    @Id
    private String username; //이름
    private String password; //비밀번호
    private Role role; //권한번호

}
