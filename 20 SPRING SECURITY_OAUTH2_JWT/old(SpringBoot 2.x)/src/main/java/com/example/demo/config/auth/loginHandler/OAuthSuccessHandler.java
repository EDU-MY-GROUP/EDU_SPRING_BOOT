package com.example.demo.config.auth.loginHandler;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.config.auth.PrincipalDetailsOAuth2Service;
import com.example.demo.config.auth.jwt.JwtProperties;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.config.auth.jwt.TokenInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("[OAUTH2]로그인 성공!");

        //----------------------------------------------------------------
        //JWT
        //----------------------------------------------------------------
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//      String token = JwtUtils.createToken(principalDetails);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        // 쿠키 생성
        Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, tokenInfo.getAccessToken());
        cookie.setMaxAge(JwtProperties.EXPIRATION_TIME); // 쿠키의 만료시간 설정
        cookie.setPath("/");
        response.addCookie(cookie);

        System.out.println("[JWT LOGIN SUCCESS HANDLER]...TokenInfo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! : " + tokenInfo);


        response.sendRedirect("/");
    }


}
