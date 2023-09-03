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

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println("CustomLogoutSuccessHandler's onLogoutSuccess!");
		PrincipalDetails principalDetails =  (PrincipalDetails) authentication.getPrincipal();

		String provider = principalDetails.getUser().getProvider();


		if(StringUtils.contains(provider,"kakao")){
			String url2 = "https://kauth.kakao.com/oauth/logout?client_id="+kakaoCliendId+"&logout_redirect_uri="+LOGOUT_REDIRECT_URI;
			response.sendRedirect(url2);
		}


		else
			response.sendRedirect( request.getContextPath() );
		
	}

}
