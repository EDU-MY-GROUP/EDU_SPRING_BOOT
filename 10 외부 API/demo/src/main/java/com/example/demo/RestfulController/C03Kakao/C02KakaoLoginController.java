package com.example.demo.RestfulController.C03Kakao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class C02KakaoLoginController {

    private static final String CLIENT_ID = "4c1e6a5f1add5934699b2afe0edf30a2";
    private static final String REDIRECT_URI = "http://localhost:8080/auth/kakao/callback";
    private static final String AUTHORIZATION_CODE_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    public static String access_token;

    //00 사전작업
    //- 카카오 로그인
    //- 애플케이션 추가
    //- 플랫폼에 localhost:8080 등록 , Redirect Uri : localhost:8080/auth/kakao/callback 등록
    //- 카카오 로그인 활성화
    //- 동의항목 체크 하기

    //00 기술 문서
    //https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token

    @GetMapping("/auth/kakao/getCode")
    public void getAuthCode(HttpServletResponse response) throws IOException {
        //01 인가코드 요청 URL에서 해보기
        //https://kauth.kakao.com/oauth/authorize?client_id={CLIENT_ID}&redirect_uri={REDIRECT_URI}&response_type=code
        // 카카오 인증 URL을 생성
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI + "&response_type=code";
        response.sendRedirect(kakaoAuthUrl);
    }

    @GetMapping("/auth/kakao/callback")
    public Object kakao_callback_func(String code) {
        //02 코드 확인
//       System.out.println("Code : " + code);
//		return "카카오 인증완료 Code : " + code;

        //02 토큰 받기 https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
        //RestFul Request Object 
        RestTemplate rt = new RestTemplate();
        //Request Header
        HttpHeaders headers = new HttpHeaders();    //Http Header Object 생성
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");//Header Key_Value Setup
        //Request Parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "4c1e6a5f1add5934699b2afe0edf30a2");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);
        //Entity에 Header / parameter 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //Http 요청하기 Post방식 , response 변수의 응답도 받기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",        //요청 URL
                HttpMethod.POST,                            //요청 Method
                kakaoTokenRequest,                            //
                String.class                                //
        );

//        System.out.println("response :" + response);
//        return response;


        //response String 을 JSON 으로 변환

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAccessToken accessToken = null;
        try {
            accessToken = objectMapper.readValue(response.getBody(), KakaoAccessToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("KakaoAccessToken : " + accessToken);
        System.out.println("카카오 엑세스 토큰 : " + accessToken.getAccess_token());


        this.access_token = accessToken.getAccess_token();

        return accessToken.getAccess_token();
    }


    // 사용자 정보가저오기 doc : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
    @GetMapping("/auth/kakao/getProfile")
    public Object getKakaoProfile() {
        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + access_token);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=urf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class

        );
        System.out.println("Response2 Body : " + response2.getBody());
        //return response2.getBody();     //나오는 정보 JSON to JAVA 로 변환

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile profile = null;
        try {
            profile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("profile : " + profile);


        return profile;

    }


    // 나에게 메시지 전송용 코드 전달 받기
    @GetMapping("/auth/kakao/getMessageCode")
    public void getAuthMessageCode(HttpServletResponse response) throws IOException {
        //01 인가코드 요청 URL에서 해보기
        //https://kauth.kakao.com/oauth/authorize?client_id={Client_Id}&redirect_uri={Redirect URL}&response_type=code&scope=talk_message
        // 카카오 인증 URL을 생성
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI + "&response_type=code&scope=talk_message";
        response.sendRedirect(kakaoAuthUrl);
    }


    // 나에게 메세지 보내기  doc : https://developers.kakao.com/docs/latest/ko/message/rest-api
    // Postmain 으로 먼저해보기 - 성공
    // code로는 성공
    // http://localhost:8080/auth/kakao/getCode or http://localhost:8080/auth/kakao/getMessageCode 수행 후 access-token 발급받은 이후 진행
    @GetMapping("/auth/kakao/me/{message}")
    public Object req_message_me(@PathVariable String message) {



        String KAKAO_API_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);  // --data-urlencode

        headers.add("Content-type", "application/x-www-form-urlencoded;charset=urf-8");     //-H "Content-Type: application/x-www-form-urlencoded" \
        headers.add("Authorization", "Bearer " + access_token);                             //-H "Authorization: Bearer ${access_token}"


        // Request Parameter를 JSON 객체로 생성
        JSONObject templateObject = new JSONObject();
        templateObject.put("object_type", "text");
        templateObject.put("text", message);
        templateObject.put("link", new JSONObject()); // Empty JSON object
        templateObject.put("button_title", "바로확인");

        //Request Parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("template_object", templateObject.toString());
        HttpEntity<MultiValueMap<String, String>> kakaoMessageRequest = new HttpEntity<>(params, headers);            //Entity에 Header / parameter 담기

        //Doc https://developers.kakao.com/docs/latest/ko/message/rest-api 예시 참고
        // Kakao API 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_API_URL, HttpMethod.POST, kakaoMessageRequest, String.class);

        System.out.println("response : " + response);


        return null;
    }
    //나에게 메시지 보내기 Curl
    //curl -v -X POST "https://kapi.kakao.com/v2/api/talk/memo/default/send" \
    //    -H "Content-Type: application/x-www-form-urlencoded" \
    //    -H "Authorization: Bearer l3OeG3J2aiNxHoY1dFvT3CRe05sP9KxycQ0W2AovCj1z6wAAAYl3LTkM" \
    //    --data-urlencode 'template_object={
    //        "object_type": "text",
    //        "text": "텍스트 영역입니다. 최대 200자 표시 가능합니다.",
    //        "link": {
    //            "web_url": "https://developers.kakao.com",
    //            "mobile_web_url": "https://developers.kakao.com"
    //        },
    //        "button_title": "바로 확인"
    //    }'




    //추가항목 동의 받기 : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-additional-consent
    //다른 학생이 접속해서 최소 1번이상의 정보제공 동의를 해야지 친구목록 확인이 가능함



    //오류 https://devtalk.kakao.com/t/topic/126276
    //참고 https://cocoabba.tistory.com/16
    //기술문서 https://developers.kakao.com/docs/latest/ko/kakaotalk-social/rest-api#get-friends

    //추가항목 동의받기를 통해서 동의항목 추가
    //https://kauth.kakao.com/oauth/authorize
    //URL 요청하기
    //https://kauth.kakao.com/oauth/authorize?client_id=4c1e6a5f1add5934699b2afe0edf30a2&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code&scope=friends
    @GetMapping("/auth/kakao/MyFriends")
    public Object My_Friends_List() {

        RestTemplate restTemplate = new RestTemplate();

        //URL
        String FRIENDS_LIST_SERVICE_URL = "https://kapi.kakao.com/v1/api/talk/friends";

        //HEADER
        System.out.println("access-token : " + access_token);
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + access_token);
        //header.setBearerAuth(access_token);
        HttpEntity<String> entity = new HttpEntity<>(header);


        //GET
        ResponseEntity<String> response = restTemplate.exchange(FRIENDS_LIST_SERVICE_URL, HttpMethod.GET, entity, String.class);


        System.out.println(response);
        return response;

    }
    //친구한테 메시지 보내보기



}