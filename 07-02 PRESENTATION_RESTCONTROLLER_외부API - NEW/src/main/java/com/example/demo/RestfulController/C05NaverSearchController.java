package com.example.demo.RestfulController;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;


@Controller
@Slf4j
public class C05NaverSearchController {
    


//    [참고] https://developers.naver.com/docs/serviceapi/search/local/local.md
//    1

    private final String NAVER_API_URL = "https://openapi.naver.com/v1/search/local.json";  //local - news - ... 로 바꿔서 검색해보기
    private final String NAVER_CLIENT_ID = "5RsCotNjdotWr3GrU6yP"; // 네이버 개발자 센터에서 발급받은 클라이언트 ID
    private final String NAVER_CLIENT_SECRET = "4Rspu4j5VX"; // 네이버 개발자 센터에서 발급받은 클라이언트 시크릿




    @GetMapping("/search/{addr}")
    public @ResponseBody ResponseEntity<String> searchLocal(@PathVariable  String addr) {
        System.out.println("ADDR : " + addr);
        RestTemplate restTemplate = new RestTemplate();

        //URL
        String url = NAVER_API_URL + "?query="+addr+"&type=json&display=100"; // 검색할 지역과 검색어 설정
        String headerValue = "Bearer " + NAVER_CLIENT_SECRET;
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.set("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        //Header + Param 객체
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //요청
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response;
    }







}
