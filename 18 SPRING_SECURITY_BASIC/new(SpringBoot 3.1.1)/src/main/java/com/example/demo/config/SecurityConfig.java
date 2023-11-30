package com.example.demo.config;


import com.example.demo.config.auth.exceptionhandler.CustomAccessDeniedHandler;
import com.example.demo.config.auth.exceptionhandler.CustomAuthenticationEntryPoint;
import com.example.demo.config.auth.loginHandler.CustomAuthenticationFailureHandler;
import com.example.demo.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
						authorizeRequests.requestMatchers("/","/login").permitAll()
										 .requestMatchers("/user").hasRole("USER")
										 .requestMatchers("/member").hasRole("MEMBER")
										 .requestMatchers("/admin").hasRole("ADMIN")
										 .anyRequest().authenticated()
				)
				//로그인폼
				.formLogin(
						login->{
							login.permitAll();
							login.loginPage("/login");
							login.successHandler(new CustomLoginSuccessHandler());
							login.failureHandler(new CustomAuthenticationFailureHandler());

						}
				)
				//로그아웃
				.logout(logout->{
					logout.logoutUrl("/logout");	//Post방식으로 요청해야함
					logout.permitAll();
					logout.addLogoutHandler(new CustomLogoutHandler());							//세션초기화
					logout.logoutSuccessHandler(new CustomLogoutSuccessHandler());				//기본위치로 페이지이동
				})
				//예외처리
				.exceptionHandling(ex->{
					ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
					ex.accessDeniedHandler(new CustomAccessDeniedHandler());
				});
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