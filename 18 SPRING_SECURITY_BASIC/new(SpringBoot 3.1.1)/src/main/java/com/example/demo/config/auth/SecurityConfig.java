package com.example.demo.config.auth;


import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain config(HttpSecurity http) throws Exception {
		http
				//csrf비활성화
				.csrf(csrfconfig-> csrfconfig.disable())
				//X-Frame-Option 비활성화(페이지내에서 다른페이지 로딩 금지)
				.headers((headerConfig) ->
						headerConfig.frameOptions(frameOptionsConfig ->
								frameOptionsConfig.disable())
				)
				//요청URL별 권한 설정
				.authorizeHttpRequests( (authorizeRequests)->
						authorizeRequests.requestMatchers("/","/login/**").permitAll()
										 .requestMatchers("/user").hasRole("USER")
										 .requestMatchers("/member").hasRole("MEMBER")
										 .requestMatchers("/admin").hasRole("ADMIN")
										 .anyRequest().authenticated()
				)
				//로그인폼
				.formLogin(
						login->{
							login.permitAll();
						}
				);
				//로그아웃

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

		userDetailsManager.createUser(User.withUsername("user")
				.password(passwordEncoder.encode("1234"))
				.roles("USER")
				.build());

		userDetailsManager.createUser(User.withUsername("member")
				.password(passwordEncoder.encode("1234"))
				.roles("MEMBER")
				.build());

		userDetailsManager.createUser(User.withUsername("admin")
				.password(passwordEncoder.encode("1234"))
				.roles("ADMIN")
				.build());

		return userDetailsManager;
	}


	// BCryptPasswordEncoder Bean 등록 - 패스워드 검증에 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}