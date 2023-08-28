package com.example.demo.C04Naver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
@Slf4j
public class C01NaverSearchController {
    


//    [참고] https://developers.naver.com/docs/serviceapi/search/local/local.md
//    1


    private final String NAVER_CLIENT_ID = "5RsCotNjdotWr3GrU6yP"; // 네이버 개발자 센터에서 발급받은 클라이언트 ID
    private final String NAVER_CLIENT_SECRET = "4Rspu4j5VX"; // 네이버 개발자 센터에서 발급받은 클라이언트 시크릿




    //지역검색
    @GetMapping("/search/addr/{addr}")
    public @ResponseBody ResponseEntity<String> searchLocal(@PathVariable  String addr) {

        String NAVER_API_URL = "https://openapi.naver.com/v1/search/local.json";

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
    //이미지 검색

    @GetMapping("/search/img/{keyword}")
    public @ResponseBody ResponseEntity<String> searchImg(@PathVariable  String keyword) {

        String NAVER_API_URL = "https://openapi.naver.com/v1/search/image.json";

        System.out.println("keyword : " + keyword);
        RestTemplate restTemplate = new RestTemplate();

        //URL
        String url = NAVER_API_URL + "?query="+keyword+"&type=json&display=1"; // 검색할 지역과 검색어 설정
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
