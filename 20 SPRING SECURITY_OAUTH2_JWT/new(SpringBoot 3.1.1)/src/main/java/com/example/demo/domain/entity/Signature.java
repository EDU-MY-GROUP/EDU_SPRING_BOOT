package com.example.demo.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.security.Key;

@Entity

public class Signature {
    @Id
    @Column(name="Signaturekey", nullable=false,length=3072)
    private Key key;
}
