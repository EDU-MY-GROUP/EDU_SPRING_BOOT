package com.example.demo.C03Kakao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/kakaoChannel")
public class C03KakaoChannel {
    
    //[참고] 채널 만들기
    // 사업자등록 없이 만들기 : https://devtalk.kakao.com/t/how-can-i-switch-to-a-biz-app-if-i-do-not-have-any-business-registration-number/71983

    // 채널 공개
    // 채널 -> 프로필 -> 프로필 설정 -> 옵션 설정 -> 채널공개 ON , 검색허용 ON
    //[참고] 채널 채팅 설정
    //채널 -> 1:1채팅 -> 채팅 설정 -> 1:1채팅 사용 ON

    //채널 가입
    @GetMapping("/join")
    public String channel() {
        return "kakaoChannel/join";
    }
    //채널 채팅
    @GetMapping("/chat")
    public String chat() {
        return "kakaoChannel/chat";
    }

    // [불가] 카카오톡 채널 관계 확인하기  - 사업자 등록 이후 가능
    // [기술문서] https://developers.kakao.com/docs/latest/ko/kakaotalk-channel/rest-api#check-relationship
    // [동의항목 선행 조건]
    //카카오톡 채널 추가 상태 및 내역 / 카카오계정(전화번호)
    //비즈앱 전환하여 카카오톡 비즈채널 연결하면 필수/선택 동의 사용 가능



}
