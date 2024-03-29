package com.example.demo.config.auth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{
    private Map<String,Object> attributes;      //oauth2User.getAttributes();

    public KakaoUserInfo(Map<String,Object> attributes){
        this.attributes = attributes;

    }
    @Override
    public String getProviderId() {
        return (String)attributes.get("id");
    }
    @Override
    public String getProvider() {
        return "kakao";
    }
    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
    @Override
    public String getName() {
        return  (String)attributes.get("name");
    }


}
