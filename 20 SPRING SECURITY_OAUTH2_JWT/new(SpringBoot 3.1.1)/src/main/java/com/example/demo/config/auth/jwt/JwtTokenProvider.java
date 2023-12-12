package com.example.demo.config.auth.jwt;


import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.Signature;
import com.example.demo.domain.repository.JwtRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {



    //Signature Key 저장
    private  Key key;                      // 클래스 내에서 사용될 JWT 토큰 생성에 사용되는 키를 나타내는 필드(Secret Key로 사용)

        //생성자
        public JwtTokenProvider() {


                 byte[] keyBytes =  KeyGenerator.getKeygen();     //난수키값 가져오기
                 this.key = Keys.hmacShaKeyFor(keyBytes);        // 생성된 키를 사용하여 HMAC SHA(암호화알고리즘)알고리즘에 기반한 Key 객체 생성
                 System.out.println("JwtTokenProvider Constructor  Key init: " + key);
        }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드(Security Authentication 이후에 처리될 내용)
    public TokenInfo generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        System.out.println("authorities : " + authorities.toString());

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        UserDto userDto = (UserDto)principalDetails.getUser();
        System.out.println("[JWTTOKENPROVIDER] generateToken() userDto : "+userDto);
        System.out.println("[JWTTOKENPROVIDER] generateToken()  accessToken : "+principalDetails.getAccessToken());

        long now = (new Date()).getTime();
        System.out.println("[JWTTOKENPROVIDER] generateToken()  authentication : " + authentication);
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + 60*1000); // 60초후 만료
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("username",authentication.getName())             //정보저장
                .claim("auth", authorities)                             //정보저장
                .claim("principal",authentication.getPrincipal())       //정보저장
                .claim("credentials",authentication.getCredentials())   //정보저장
                .claim("details",authentication.getDetails())           //정보저장 민감정보는 넣지 않는게 좋다...
                .claim("provider",userDto.getProvider())                //정보저장
                .claim("password",userDto.getPassword())                //정보저장 민감정보는 넣지 않는게 좋다...

                .claim("accessToken",principalDetails.getAccessToken())           //정보저장(OAUTH2??)

                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))    //1일: 24 * 60 * 60 * 1000 = 86400000
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("[JWTTOKENPROVIDER] generateToken() jwt accessToken : "          + accessToken);
        System.out.println("[JWTTOKENPROVIDER] generateToken() refresh accessToken : "      + refreshToken);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }



    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) throws IOException {
        System.out.println("[JWTTOKENPROVIDER] accessToken : " + accessToken);
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(auth -> new SimpleGrantedAuthority(auth))
                        .collect(Collectors.toList());

        String username = claims.getSubject(); //username
        System.out.println("[JWTTOKENPROVIDER] username  : " + username);


        //--------------------------------------------------
        //getPrincipal
        //--------------------------------------------------
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("[JWTTOKENPROVIDER] principalDetails  : " + claims.get("principal"));

        String provider =  (String)claims.get("provider");
        String password = (String)claims.get("password");
        String auth = (String)claims.get("auth");
        String oauthAccessToken = (String)claims.get("accessToken");
        UserDto userDto = new UserDto();
        userDto.setProvider(provider);
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setRole(auth);

        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setUser(userDto);
        principalDetails.setAccessToken(oauthAccessToken);   //Oauth AccessToken
        System.out.println("[JWTTOKENPROVIDER] getAuthentication() principalDetails  : " + principalDetails);


        //JWT + NO REMEMBERME
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(principalDetails, claims.get("credentials"), authorities);

        //JWT + REMEMBER ME
        RememberMeAuthenticationToken jwtAndRememberUserToken = new RememberMeAuthenticationToken("rememberMeKey", principalDetails, authorities);


        return jwtAndRememberUserToken;
    }



    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {

            //여기서 바로 서명을 통한 일치여부를 확인하지 않고..
            //먼저 기존의 받은 Token을 통해서 확인하는 작업부터 시작해야한다..
            //고려 대상
            // 1 JWT 토큰의 만료 기간 2 서명 키의 변경여부 3
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            //여기서 걸리는거.. 서명Key가 일치 하지 않았을때 Token값을  받는 다면 RefreshToken으로 Access-token을 새로 발급?
            //그러면.. 서명을 저장해야되는거아닌가?
//        }
//        catch (ExpiredJwtException e) {
//            log.info("Expired JWT Token", e);

        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}