package com.example.demo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long cart_id;
    private Long product_id;
    private int amount;
    private String username;
    private LocalDateTime regdate;


}
