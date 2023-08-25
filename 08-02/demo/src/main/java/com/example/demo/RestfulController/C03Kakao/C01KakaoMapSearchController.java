package com.example.demo.RestfulController.C03Kakao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class C01KakaoMapSearchController {

    ////   [참고] https://kakao-tam.tistory.com/57
    // 01 카카오 Map 키워드로 장소검색하고 목록으로 표출하기 : apis.map.kakao.com/web/sample/keywordList/
    // 02 스타일링(마커 변경하기.... )
    // 0
    // -
    // 03 검색된 마커를 클릭했을 때 장소정보를 띄우기 : 어떤걸로 할까? naver Search?


    @GetMapping("/searchMap")
    public void serarchMap(){
        log.info("GET /searchMap");
    }
}
