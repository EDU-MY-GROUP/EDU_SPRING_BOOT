package com.example.demo.config.auth.logoutHandler;


import com.example.demo.config.auth.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	private String kakaoCliendId = "ef4f3d1c4e61aa5c2695db6b57320f9a";		//ADMIN KEY

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
			return ;
		}

		response.sendRedirect( request.getContextPath() );	// '/' 경로로 이동

	}

}
