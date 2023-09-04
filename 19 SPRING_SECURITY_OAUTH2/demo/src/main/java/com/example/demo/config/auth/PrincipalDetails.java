package com.example.demo.config.auth;


import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor

public class PrincipalDetails implements UserDetails, OAuth2User {

	//OAuth2 추가---------------------------------------
	private Map<String,Object> attributes;
	private String accessToken;

	public PrincipalDetails(User user,Map<String,Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	public PrincipalDetails(User user) {
		this.user = user;
		this.attributes = null;
	}
	@Override
	public String getName() {
		return null;
	}
	@Override
	public Map<String,Object> getAttributes() {
		return attributes;
	}
	//---------------------------------------


	private User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList();
		
		collection.add(new GrantedAuthority(){
			@Override
			public String getAuthority() {
				return user.getRole().toString();
			}
	
		} );
		
		return collection;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
