package com.example.demo.C03KakaoAPI;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/th/kakao")
public class C02KakaoLoginController {

//    private final String ADMIN_KEY = "APP_ADMIN_KEY";
    private final String CLIENT_ID = "746b917a629e6c3271acfaa6542552d5";

    private final String REDIRECT_URI = "http://localhost:8080/th/kakao/callback";

    private final String LOGOUT_REDIRECT_URI = "http://localhost:8080/th/kakao/login";

    //access-token/refresh-token/expires-in...
    private KakaoTokenResponse kakaoTokenResponse;

    private KakaoProfile kakaoProfile;

    // 인가코드 요청 URL
    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}

    @GetMapping("/getCode")
    public void authorize(HttpServletResponse response) throws Exception {
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI;
        if(kakaoTokenResponse == null)
            url += "&prompt=login";

        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public String redirectFunc(String code){
        log.info("GET /th/kakao/callback CODE : "+ code);

        //URL
        String url = "https://kauth.kakao.com/oauth/token";
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        //Parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",CLIENT_ID);
        params.add("redirect_uri",REDIRECT_URI);
        params.add("code",code);

        //header + parameter
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);

        //Request_Case1
//      RestTemplate rt = new RestTemplate();
//      ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);
//
//      System.out.println(response);
//      System.out.println(response.getBody());

        //Request_Case2
        RestTemplate rt = new RestTemplate();
        KakaoTokenResponse response =  rt.postForObject(url,entity,KakaoTokenResponse.class);
        System.out.println(response);
        this.kakaoTokenResponse = response;

        return "redirect:/th/kakao/main";
    }


    @GetMapping("/main")
    public void main() {
        log.info("GET /th/kakao/main");
    }
    @GetMapping("/login")
    public void login() {
        log.info("GET /th/kakao/login");
    }

    @GetMapping(value="/profile",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody KakaoProfile profile() {
        log.info("GET /th/kakao/profile");

        //URL
        String url = "https://kapi.kakao.com/v2/user/me";
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+kakaoTokenResponse.getAccess_token());
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        //header + parameter
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        //Request_Case1
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);
//
//        System.out.println(response);
//        System.out.println(response.getBody());

        //Request_Case2
        RestTemplate rt = new RestTemplate();
        KakaoProfile response =  rt.postForObject(url,entity,KakaoProfile.class);
        System.out.println(response);

        this.kakaoProfile = response;
        return response;
    }
    
    
    
    // 나에게 메시지 보내기 기능 구현
    //01 scope=talk_message 권한 받기
    @GetMapping("/getCodeMsg")
    public void authorize_Msg(HttpServletResponse response) throws Exception {
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&scope=talk_message";
        response.sendRedirect(url);
    }
    
    @GetMapping("/message/me/{message}")
    public Object sendMessageMe(@PathVariable("message") String message) {

        //URL
        String url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer "+kakaoTokenResponse.getAccess_token());

        //Parameter
        JSONObject template_object = new JSONObject();
        template_object.put("object_type","text");
        template_object.put("text",message);
        template_object.put("link",new JSONObject());
        template_object.put("button_title","바로확인");


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("template_object",template_object.toString());


        //header + parameter
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);

        //Request_Case1
      RestTemplate rt = new RestTemplate();
      ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);

      System.out.println(response);
      System.out.println(response.getBody());



        return null;
    }

    // 카카오 계정과 함께 로그아웃(카카오 서버에 저장된 사용자 액세스 토큰과 리프레시 토큰을 모두 만료)
    // 개발 문서 : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#logout

    @GetMapping("/logout")
    public String logoutKakao(HttpSession session){

        System.out.println("GET /th/kakao/logout");
        //URL
        String url = "https://kapi.kakao.com/v1/user/logout";
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer "+kakaoTokenResponse.getAccess_token());

        //Parameter

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type","user_id");
        System.out.println("targetId : " + kakaoProfile.getId());
        params.add("target_id",kakaoProfile.getId()+"");


        //header + parameter
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);

        //Request_Case1
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);

        System.out.println(response);
        System.out.println(response.getBody());

        //세션 확인
        String access_Token = (String)session.getAttribute("access_Token");
        System.out.println("Session's accessToken : " + access_Token);


        return "redirect:/th/kakao/logoutWithKakao";
    }

    // 카카오 계정과 함께 로그아웃(웹브라우저의 카카오정보 제거)
    // 기술문서 : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#logout-of-service-and-kakaoaccount

    @GetMapping("/logoutWithKakao")
    public String logoutKakaoWithKakao() throws Exception {

        System.out.println("GET /th/kakao/logoutWithKakao");

        //URL
        String url = "https://kauth.kakao.com/oauth/logout?client_id="+ CLIENT_ID +"&logout_redirect_uri="+LOGOUT_REDIRECT_URI;


        //Request_Case1
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =  rt.exchange(url, HttpMethod.GET,null,String.class);

        System.out.println(response);
        System.out.println(response.getBody());


        //초기화
        this.kakaoProfile = null;
        this.kakaoTokenResponse = null;
        return "redirect:/th/kakao/login";

    }


    //APP 연결 끊기
    @GetMapping("/disConnect")
    public String disconnectKakao(){

        System.out.println("GET /th/kakao/disConnect");

        //URL
        String url = "https://kapi.kakao.com/v1/user/unlink";
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer "+kakaoTokenResponse.getAccess_token());

        //Parameter

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type","user_id");
        params.add("target_id",kakaoProfile.getId()+"");


        //header + parameter
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);

        //Request_Case1
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);

        System.out.println(response);
        System.out.println(response.getBody());


        return "redirect:/th/kakao/logoutWithKakao";
    }



}


//----------------------------------------------------
@Data
class KakaoTokenResponse {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
    private long refresh_token_expires_in;
}
//----------------------------------------------------
@Data
class KakaoProfile {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("connected_at")
    private String connected_at;
    @JsonProperty("properties")
    private Properties properties;
    @JsonProperty("kakao_account")
    private Kakao_account kakao_account;

    @Data
    @NoArgsConstructor
    public static  class Kakao_account {
        private boolean profile_nickname_needs_agreement;
        private boolean profile_image_needs_agreement;
        private Profile profile;
        private boolean has_email;
        private boolean email_needs_agreement;
        private String is_email_valid;
        private String is_email_verified;
        private String email;
        private boolean has_age_range;
        private boolean age_range_needs_agreement;
        private String age_range;
    }
    @Data
    @NoArgsConstructor
    public static  class Profile {
        private String nickname;
        private String thumbnail_image_url;
        private String profile_image_url;
        private String is_default_image;

    }
    @Data
    @NoArgsConstructor
    public static  class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

}
