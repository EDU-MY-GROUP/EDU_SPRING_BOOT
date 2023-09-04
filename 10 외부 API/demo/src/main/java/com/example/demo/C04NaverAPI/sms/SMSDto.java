package com.example.demo.C04NaverAPI.sms;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SMSDto {
    String to;
    String content;
}
