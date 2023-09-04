package com.example.demo.config.auth.logoutHandler;


import com.example.demo.config.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class OAuthLogoutHandler implements LogoutHandler {

    private final RestTemplate restTemplate;

    public OAuthLogoutHandler() {
        this.restTemplate = new RestTemplate();
    }



//-------------------------------------------------------------------------------------------------------------
    //-------------
    //NAVER
    //-------------
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    //-------------
    //KAKAO
    //-------------
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoCliendId;


    private String LOGOUT_REDIRECT_URI = "http://localhost:8080/login";

//-------------------------------------------------------------------------------------------------------------


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  {
        System.out.println("OAuthLogoutHandler's logout");
        PrincipalDetails principalDetails =  (PrincipalDetails) authentication.getPrincipal();

        System.out.println("OAuthLogoutHandler logout authentication  : "  + authentication);
        System.out.println("OAuthLogoutHandler logout principalDetails : " +principalDetails );
        System.out.println("OAuthLogoutHandler logout principalDetails getAttributes() : " +  principalDetails.getAttributes());
        System.out.println("OAuthLogoutHandler logout principalDetails getProvider() : " +  principalDetails.getUser().getProvider());
        String provider = principalDetails.getUser().getProvider();

        if(provider!=null &&  StringUtils.contains(provider,"kakao")){

            //--------------------------------
            //01 KAKAO LOGOUT
            //--------------------------------
            System.out.println("카카오 로그아웃 진행!...................");

            String accessToken = ((PrincipalDetails) authentication.getPrincipal()).getAccessToken();

            //URL
            String url = "https://kapi.kakao.com/v1/user/logout";
            //Header
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            headers.add("Authorization", "Bearer "+accessToken);

            //Parameter
            //header + parameter
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

            //Request_Case1
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> resp =  rt.exchange(url, HttpMethod.POST,entity,String.class);

            System.out.println(resp);
            System.out.println(resp.getBody());




        }
        else if(provider!=null && StringUtils.contains(provider,"google")){

            System.out.println("authentication getPrincipal()  : "  + (PrincipalDetails)authentication.getPrincipal());
            System.out.println("getAccessToken()  : "  + ((PrincipalDetails) authentication.getPrincipal()).getAccessToken());
            String accessToken = ((PrincipalDetails) authentication.getPrincipal()).getAccessToken();

            //구글 LOGIN APP API 와 연결끊기
            String revokeUrl = "https://accounts.google.com/o/oauth2/revoke?token=" + accessToken;
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            restTemplate.exchange(revokeUrl, HttpMethod.GET, entity, String.class);

            System.out.println("GOOGLE LOGOUT Success!");

            //기존 세션 제거
            HttpSession session = request.getSession(false);
            if(session!=null)
                session.invalidate();

            // 로그아웃 후 리다이렉트할 URL을 지정합니다.
            try {
                response.sendRedirect("/loginForm");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else if(provider!=null && StringUtils.contains(provider,"naver"))
        {

            System.out.println("naverClientId : " + naverClientId);
            System.out.println("naverClientSecret : " + naverClientSecret);

            System.out.println("authentication  : "  + authentication);
            System.out.println("authentication getPrincipal()  : "  + (PrincipalDetails)authentication.getPrincipal());
            System.out.println("getAccessToken()  : "  + ((PrincipalDetails) authentication.getPrincipal()).getAccessToken());
            String accessToken = ((PrincipalDetails) authentication.getPrincipal()).getAccessToken();

//            //
            // 네이버 API 로그아웃을 위한 URL 생성
            String logoutUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="
                    + naverClientId + "&client_secret=" + naverClientSecret + "&access_token=" + accessToken + "&service_provider=NAVER";
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            restTemplate.exchange(logoutUrl, HttpMethod.GET, entity, String.class);

            System.out.println("NAVER LOGOUT Success!");
            //기존 세션 제거
            HttpSession session = request.getSession(false);
            if(session!=null)
                session.invalidate();

            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }
}
