package com.example.demo.domain.dto;


import com.example.demo.type.Role;
import lombok.Data;

@Data
public class UserDto {
	private String username;
	private String password;
	private String role;

	// OAuth2 Added
	private String provider;
	private String providerId;
}
