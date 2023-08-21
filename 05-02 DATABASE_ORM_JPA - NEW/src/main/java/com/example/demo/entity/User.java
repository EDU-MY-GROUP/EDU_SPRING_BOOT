package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @Column(length = 100)
    private String id;
    @Column(length = 255)
    private String pw;
    @Column(length = 255)
    private String username;
    @Column(length = 255)
    private String role;
}
