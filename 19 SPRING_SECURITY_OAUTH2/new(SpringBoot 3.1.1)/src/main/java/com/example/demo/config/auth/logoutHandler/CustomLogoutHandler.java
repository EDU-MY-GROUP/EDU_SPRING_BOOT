package com.example.demo.config.auth.logoutHandler;

import com.example.demo.config.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;


public class CustomLogoutHandler implements LogoutHandler{

	private RestTemplate restTemplate;
	public CustomLogoutHandler() {
		this.restTemplate = new RestTemplate();
	}
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoCliendId;

	private String LOGOUT_REDIRECT_URI = "http://localhost:8080/login";

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
			System.out.println("CustomLogoutHandler's logout");
			HttpSession session = request.getSession(false);
			if(session!=null)
				session.invalidate();
			System.out.println("OAuthLogoutHandler's logout");
			PrincipalDetails principalDetails =  (PrincipalDetails) authentication.getPrincipal();

			System.out.println("OAuthLogoutHandler logout authentication  : "  + authentication);
			System.out.println("OAuthLogoutHandler logout principalDetails : " +principalDetails );
			System.out.println("OAuthLogoutHandler logout principalDetails getAttributes() : " +  principalDetails.getAttributes());
			System.out.println("OAuthLogoutHandler logout principalDetails getProvider() : " +  principalDetails.getUser().getProvider());
			String provider = principalDetails.getUser().getProvider();
			if(StringUtils.contains(provider,"kakao")){

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
			else if(StringUtils.contains(provider,"google")){
				;
			}
			else if(StringUtils.contains(provider,"naver"))
			{
				;
			}




	}
		

}
