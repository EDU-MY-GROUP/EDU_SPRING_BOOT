package com.example.demo.config;


import com.example.demo.config.auth.PrincipalDetailsOAuth2Service;
import com.example.demo.config.auth.exceptionhandler.CustomAccessDeniedHandler;
import com.example.demo.config.auth.exceptionhandler.CustomAuthenticationEntryPoint;
import com.example.demo.config.auth.jwt.JwtAuthenticationFilter;
import com.example.demo.config.auth.jwt.JwtAuthorizationFilter;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.config.auth.loginHandler.CustomAuthenticationFailureHandler;
import com.example.demo.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutSuccessHandler;
import com.example.demo.domain.repository.UserRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Autowired
	private HikariDataSource dataSource;
	//---------------
	//JWT TOKEN
	//---------------
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PrincipalDetailsOAuth2Service principalDetailsOAuth2Service;

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
						authorizeRequests.requestMatchers("/","/login","/join").permitAll()
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
					//JWT
					logout.deleteCookies("JSESSIONID","JWT-AUTHENTICATION");
					logout.invalidateHttpSession(true);

				})
				//예외처리
				.exceptionHandling(
						ex->{
							ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
							ex.accessDeniedHandler(new CustomAccessDeniedHandler());
						}
				)
				//RememberMe
				.rememberMe(
						rm->{
							rm.key("rememberMeKey");		//Handler에서 제어할때 사용
							rm.rememberMeParameter("remember-me"); //View에서 전달하는 파라미터명
							rm.alwaysRemember(false);
							rm.tokenValiditySeconds(3600);
							rm.tokenRepository(tokenRepository());
						}
				)
				//OAuth2
				.oauth2Login(
						oauth2->{
							oauth2.loginPage("/login");


						}
				)

				//----------------------------------------------------------------
				// Session 비활성화
				//----------------------------------------------------------------
				.sessionManagement(httpSecuritySessionManagementConfigurer ->  httpSecuritySessionManagementConfigurer
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				)
				//----------------------------------------------------------------
				// JWT
				//----------------------------------------------------------------

				.addFilterBefore(
                                new JwtAuthenticationFilter(authenticationManager(),jwtTokenProvider),  //JWT 인증 토큰 필터
                                UsernamePasswordAuthenticationFilter.class      //ID/PW 인증 시도 필터
				)
				.addFilterBefore(
						new JwtAuthorizationFilter(userRepository,jwtTokenProvider),
						BasicAuthenticationFilter.class
				)
				//----------------------------------------------------------------
				;


		return http.build();
	}
	//----------------------------------------------------------------
	// JWT
	//----------------------------------------------------------------
	@Bean
	public AuthenticationManager authenticationManager(){
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(principalDetailsOAuth2Service);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}
	//----------------------------------------------------------------



	//REMEMBER-ME ADDED
	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}


//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//
//		userDetailsManager.createUser(User.withUsername("user")
//				.password(passwordEncoder.encode("1234"))
//				.roles("USER")
//				.build());
//
//		userDetailsManager.createUser(User.withUsername("member")
//				.password(passwordEncoder.encode("1234"))
//				.roles("MEMBER")
//				.build());
//
//		userDetailsManager.createUser(User.withUsername("admin")
//				.password(passwordEncoder.encode("1234"))
//
//				.roles("ADMIN")
//				.build());
//
//		return userDetailsManager;
//	}


	// BCryptPasswordEncoder Bean 등록 - 패스워드 검증에 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}