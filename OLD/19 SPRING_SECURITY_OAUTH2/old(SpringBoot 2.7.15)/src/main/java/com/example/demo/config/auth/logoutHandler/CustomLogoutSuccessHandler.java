package com.example.demo.config.auth.logoutHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.config.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.thymeleaf.util.StringUtils;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{
	//-------------
	//KAKAO
	//-------------

	private String kakaoCliendId = "206caf77477fe5ba91dafb10da8d95d2";


	private String LOGOUT_REDIRECT_URI = "http://localhost:8080/login";


	private String naverClientId = "myCONIrrsJHFIPcgl9OQ";
	private String naverClientSecret = "ngJQs03WkY";

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println("CustomLogoutSuccessHandler's onLogoutSuccess!");


		PrincipalDetails principalDetails =  (PrincipalDetails) authentication.getPrincipal();
		String provider = principalDetails.getUser().getProvider();

		if(StringUtils.contains(provider,"kakao")){
			String url2 = "https://kauth.kakao.com/oauth/logout?client_id="+kakaoCliendId+"&logout_redirect_uri="+LOGOUT_REDIRECT_URI;
			response.sendRedirect(url2);
			return ;
		}

		else if(StringUtils.contains(provider,"naver"))
		{
			//네이버 재인증 요청
			//https://developers.naver.com/docs/login/devguide/devguide.md#5-2-1-%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%9E%AC%EC%9D%B8%EC%A6%9D%EC%9D%B4-%ED%95%84%EC%9A%94%ED%95%9C-%EA%B2%BD%EC%9A%B0
			System.out.println("NAVER!!!!!!!!! ");
			String url2 = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id="+naverClientId+"&redirect_uri="+LOGOUT_REDIRECT_URI+"auth_type=reauthenticate&state=1";
			response.sendRedirect(url2);
			return ;
		}


		response.sendRedirect( "/login" );
	}

}
