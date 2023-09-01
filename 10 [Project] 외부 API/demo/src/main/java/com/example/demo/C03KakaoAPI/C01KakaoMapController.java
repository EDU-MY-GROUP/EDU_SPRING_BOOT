package com.example.demo.C03KakaoAPI;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/th/kakao")
public class C01KakaoMapController {

    @GetMapping("/map")
    public void map() {
    }
}


// 참고
// 카카오 마커 종류 https://devtalk.kakao.com/t/topic/98043
