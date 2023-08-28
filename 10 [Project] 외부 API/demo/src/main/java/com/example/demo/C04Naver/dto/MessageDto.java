package com.example.demo.C04Naver.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MessageDto {
    String to;
    String content;
}
