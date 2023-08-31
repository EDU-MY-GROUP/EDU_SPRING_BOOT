package com.example.demo.C03KakaoAPI;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/th/kakao")
public class C03KakaoChannelAPI {
    @GetMapping("/channel")
    public void channel() {
        System.out.println("GET /th/kakao/channel");
    }
    // 채널 공개
    // 채널 -> 프로필 -> 프로필 설정 -> 옵션 설정 -> 채널공개 ON , 검색허용 ON
    //[참고] 채널 채팅 설정
    //채널 -> 1:1채팅 -> 채팅 설정 -> 1:1채팅 사용 ON





}
