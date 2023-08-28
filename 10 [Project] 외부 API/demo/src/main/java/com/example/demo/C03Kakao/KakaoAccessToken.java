package com.example.demo.C03Kakao;


import lombok.Data;


//JSON To JAVA Site  https://jsonformatter.org/json-to-java
@Data
public class KakaoAccessToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
    private long refresh_token_expires_in;
}
