package com.example.demo.C04NaverAPI;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("/th/naver")
public class C01NaverSerachAPIController {

    private final String NAVER_CLIENT_ID = "NAVER_CLIENT_ID";

    private final String NAVER_CLIENT_SECRET="NAVER_CLIENT_SECRET";

    @GetMapping(value = "/lo" +
            "cal/{keyword}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody LocalResponse local(@PathVariable String keyword){
        log.info("GET /th/naver/local " + keyword);

        //URL
        String url = "https://openapi.naver.com/v1/search/local.json?query="+keyword+"&display=1";
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        //header + parameter
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        //Request_Case1
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response =  rt.exchange(url, HttpMethod.GET,entity,String.class);
//        System.out.println(response);
//        System.out.println(response.getBody());
//        return response.getBody();

        //Request_Case2
        RestTemplate rt = new RestTemplate();

        LocalResponse response =  rt.exchange(url, HttpMethod.GET,entity, LocalResponse.class).getBody();
        System.out.println(response);

        return response;

    }




    @GetMapping("/image/{page}/{keyword}")
    public @ResponseBody String image(@PathVariable String page, @PathVariable String keyword){
        log.info("GET /th/naver/image");

        //URL
        String url = "https://openapi.naver.com/v1/search/image.json?query="+keyword+"&display=10&start="+page;
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        //header + parameter
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        //Request_Case1
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =  rt.exchange(url, HttpMethod.GET,entity,String.class);

        System.out.println(response);
        System.out.println(response.getBody());
        return response.getBody();
        //Request_Case2
//        RestTemplate rt = new RestTemplate();
//        KakaoProfile response =  rt.postForObject(url,entity,KakaoProfile.class);
//        System.out.println(response);
//        this.kakaoProfile = response;

    }

}


@Data
class LocalResponse {
    private String lastBuildDate;
    private Long total;
    private Long start;
    private Long display;
    ArrayList< Object > items = new ArrayList< Object >();
}