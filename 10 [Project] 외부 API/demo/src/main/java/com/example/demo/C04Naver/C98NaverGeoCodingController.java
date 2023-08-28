package com.example.demo.C04Naver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("/geoCoding")
public class C98NaverGeoCodingController {

    private final String NAVER_CLIENT_ID = "s0femem0gn"; // 네이버 개발자 센터에서 발급받은 클라이언트 ID
    private final String NAVER_CLIENT_SECRET = "SUwuByccz8qwPaALU1SaPKTp9q73Qkg4Xq0yFF7U"; // 네이버 개발자 센터에서 발급받은 클라이언트 시크릿


    @GetMapping("/get")
    public JSONObject f1(String keyword) throws JsonProcessingException {

        // URL
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+keyword;

        //요청 헤더 설정(생략)

        //요청 객체 생성
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", NAVER_CLIENT_ID);
        headers.set("X-NCP-APIGW-API-KEY", NAVER_CLIENT_SECRET);

        //Header + Param 객체
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //요청
        ResponseEntity<String> response= restTemplate.exchange(url, HttpMethod.GET, entity, String.class);




        //String을 -> Object로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 문자열을 원하는 객체로 변환
        JSONObject result = objectMapper.readValue(response.getBody(), JSONObject.class);
        return result;
    }


}
