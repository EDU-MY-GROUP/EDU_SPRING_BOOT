package com.example.demo.C03KakaoAPI;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/th/kakao/karlo")

public class C05KakaoKarloAPIController {




    private final String RESTAPI_KEY = "REST_API_KEY";

    @GetMapping("/index")
    public void index(){
        System.out.println("GET /th/kakao/karlo/index ");
    }
    @GetMapping("/request/{prompt}")
    public @ResponseBody Object request(@PathVariable("prompt") String prompt)
    {
        System.out.println("GET /th/kakao/karlo/requqest " + prompt);
        //URL
        String url = "https://api.kakaobrain.com/v2/inference/karlo/t2i";
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK "+RESTAPI_KEY);

        headers.add("Content-Type", "application/json");


        //Parameter

        JSONObject param = new JSONObject();;
        param.put("prompt",prompt);


        //header + parameter
        HttpEntity<String> entity = new HttpEntity<>(param.toString(),headers);

        //Request_Case1
      RestTemplate rt = new RestTemplate();
      ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);

      System.out.println(response);
      System.out.println(response.getBody());

      return response.getBody();
        //Request_Case2
//        RestTemplate rt = new RestTemplate();
//        KakaoTokenResponse response =  rt.postForObject(url,entity,KakaoTokenResponse.class);
//        System.out.println(response);
//        this.kakaoTokenResponse = response;

    }



}
