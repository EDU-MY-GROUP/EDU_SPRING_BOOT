package com.example.demo.RestfulController;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@Slf4j
public class C01OpenDataRestController {

    //공공데이터 매핑
    //맛집 : https://www.daegufood.go.kr/kor/api/tasty.html?mode=json&addr=%EC%A4%91%EA%B5%AC
    @GetMapping(value="/Restful/openData1/{addr}",produces=MediaType.APPLICATION_JSON_VALUE)
    public Object  openData(@PathVariable String addr){

        // URL
        String url = "https://www.daegufood.go.kr/kor/api/tasty.html?mode=json&addr="+addr;

        //요청 헤더 설정(생략)

        //요청 객체 생성
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,null, String.class);
        String responseBody = response.getBody();


        //String을 -> Object로 변환
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON 문자열을 원하는 객체로 변환
            JSONObject result = objectMapper.readValue(responseBody, JSONObject.class);
            Object result_get_data =  result.get("data");
//            return result;
          return result_get_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
