package com.example.demo.config.auth;


import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user =  userRepository.findById(username).get();


		UserDto dto = new UserDto();
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setRole(user.getRole());
		if(dto==null)
			return null;
		
		return new PrincipalDetails(dto);
		
	 
	
	}

}
