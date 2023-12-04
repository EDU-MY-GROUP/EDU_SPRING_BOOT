package com.example.demo.domain.entity;

import com.example.demo.type.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="user")
public class User {
    @Id
    private String username;        //이름
    private String password;        //비밀번호
    //private String role;            //권한번호
    private Role role;

    // OAuth2 Added
    private String provider;
    private String providerId;

}
