package com.example.demo.domain.dto;

import lombok.Data;

@Data
public class UserDto {
	private String username;
	private String password;
	private String email;
	private String role;
	private String profileImage;    //프로필 이미지 경로
	private String phone;
	private String zipcode;
	private String addr1;
	private String addr2;


	//OAUTH2
	private String provider;
	private String providerId;
}
