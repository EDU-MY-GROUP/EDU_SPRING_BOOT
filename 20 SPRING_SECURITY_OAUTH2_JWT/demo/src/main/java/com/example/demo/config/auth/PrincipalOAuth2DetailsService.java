package com.example.demo.config.auth;

import com.example.demo.config.auth.jwt.JwtProperties;
import com.example.demo.config.auth.jwt.TokenInfo;
import com.example.demo.config.auth.provider.GoogleUserInfo;
import com.example.demo.config.auth.provider.KakaoUserInfo;
import com.example.demo.config.auth.provider.NaverUserInfo;
import com.example.demo.config.auth.provider.OAuth2UserInfo;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.Optional;


@Service
public class PrincipalOAuth2DetailsService  extends DefaultOAuth2UserService implements UserDetailsService{


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PrincipalOAuth2DetailsService(){
        bCryptPasswordEncoder= new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //01 UserRequest 정보 확인 -----------------------------------------------------
        System.out.println("UserRequest :" + userRequest);
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());     // 어떤 Oauth 로 로그인했는지 확인
        System.out.println("getAccessToken : " + userRequest.getAccessToken());
        System.out.println("getTokenValue : " + userRequest.getAccessToken().getTokenValue());


        //구글로그인버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴(Oauth-client라이브러리) -> Access-token 요청
        //userRequest정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 받아준다.

//        System.out.println("UserRequest : " + super.loadUser(userRequest).getAttributes());

        //02 UserRequest->OAuth2User 객체변환 -----------------------------------------------------
        OAuth2User oauth2User = super.loadUser(userRequest);
        System.out.println("getAttributes : " + oauth2User.getAttributes());

        //강제 회원가입하기 위한 작업

        //03 NAVER/KAKAO/GOOGLE API 선별하여 OAuth2UserInfo로 변환 -----------------------------------------------------
        OAuth2UserInfo oAuth2Uerinfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청 확인!");
            oAuth2Uerinfo = new GoogleUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청 확인!");
            oAuth2Uerinfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("카카오 로그인 요청 확인!");
            //유저 Attribute , access-token을 KakaoUserInfo에 저장
            oAuth2Uerinfo = new KakaoUserInfo((Map)oauth2User.getAttributes().get("properties"));
        }

        //04 OAuth2UserInfo 정보 확인 (provider,providerId,username,password,email,role)-----------------------------------------------------
        //String provider = userRequest.getClientRegistration().getClientId();
        String provider = oAuth2Uerinfo.getProvider();
        System.out.println("Provider : " + provider);       //NAVER LOGIN 시 추가
        String providerId = oAuth2Uerinfo.getProviderId();
        System.out.println("ProviderId : " + providerId);       //NAVER LOGIN 시 추가
        String username = provider + "_" + providerId; //google_1020340102...
        System.out.println("Username : " + username);
        String password = bCryptPasswordEncoder.encode("1234");
        String email = oAuth2Uerinfo.getEmail();
        String role = "ROLE_USER";


        //04 DB에 저장-----------------------------------------------------
        Optional<User> userEntity = userRepository.findById(username);
        System.out.println("userEntity : " + userEntity);
        User user = null;
        if(userEntity.isEmpty()){
            user = User.builder()
                    .username(username)
                    .password(password)
                    .role(Role.valueOf(role))
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
            System.out.println("구글 최초 접속...");
        }else{
            user = userEntity.get();
            System.out.println("로그인성공!...");
        }


        //return super.loadUser(userRequest);

        //05 Authtication객체에 저장 (Security Context에 저장되어 관리)-----------------------------------------------------
        PrincipalDetails principalDetails = new PrincipalDetails(user,oauth2User.getAttributes());
        principalDetails.setAccessToken(userRequest.getAccessToken().getTokenValue());
        //----------------------------------------------------
        //JWT TOKEN
        //----------------------------------------------------





        return principalDetails ;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user =  userRepository.findById(username);


        if(user.isEmpty())
            return null;
        return new PrincipalDetails(user.get());
    }


}
