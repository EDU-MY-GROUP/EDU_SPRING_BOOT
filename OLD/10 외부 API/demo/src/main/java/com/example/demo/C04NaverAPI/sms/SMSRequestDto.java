package com.example.demo.C04NaverAPI.sms;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SMSRequestDto {
    String type;
    String contentType;
    String countryCode;
    String from;
    String content;
    List<SMSDto> messages;
}
