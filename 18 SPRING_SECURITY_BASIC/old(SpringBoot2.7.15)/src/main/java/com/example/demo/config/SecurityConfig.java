package com.example.demo.config;


import com.example.demo.config.auth.PrincipalDetailsService;
import com.example.demo.config.auth.exceptionhandler.CustomAccessDeniedHandler;
import com.example.demo.config.auth.exceptionhandler.CustomAuthenticationEntryPoint;
import com.example.demo.config.auth.loginHandler.CustomAuthenticationFailureHandler;
import com.example.demo.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailsService principalDetailsService;

    @Autowired
    private DataSource dataSource;

    // 웹 요청 처리
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/","/login","/join").permitAll()
                .antMatchers("/user").hasRole("USER")		//ROLE_USER
                .antMatchers("/member").hasRole("MEMBER")	//ROLE_MEMBER
                .antMatchers("/admin").hasRole("ADMIN")		//ROLE_ADMIN
                .anyRequest().authenticated()				//나머지 URL은 모두 인증작업이 완료된 이후 접근가능
                .and()

                //로그인
                .formLogin()
                .loginPage("/login")
                .successHandler(new CustomLoginSuccessHandler())//ROLE_USER -> user페이지 / ROLE_MEMBER -> member페이지
                .failureHandler(new CustomAuthenticationFailureHandler())

                .and()
                //로그아웃
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .addLogoutHandler(new CustomLogoutHandler())							//세션초기화
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())				//기본위치로 페이지이동


                .and()
                //예외처리
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())		//인증이 필요한 자원에 접근 예외처리
                .accessDeniedHandler(new CustomAccessDeniedHandler())											//권한 실패 예외처리

                .and()
                //REMEMBER-ME ADDED
                .rememberMe()
                .key("rememberMeKey")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(3600)
                .alwaysRemember(false)		//체크박스 체크해야 Remember-me 동작
                .tokenRepository(tokenRepository())
                .userDetailsService(principalDetailsService);


    }

    //REMEMBER-ME ADDED
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.inMemoryAuthentication()
//				.withUser("user")
//				.password(passwordEncoder().encode("1234"))
//				.roles("USER");
//		auth
//			.inMemoryAuthentication()
//				.withUser("member")
//				.password(passwordEncoder().encode("1234"))
//				.roles("MEMBER");
//		auth
//			.inMemoryAuthentication()
//				.withUser("admin")
//				.password(passwordEncoder().encode("1234"))
//				.roles("ADMIN");

        auth.userDetailsService(principalDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    // BCryptPasswordEncoder Bean 등록
    // 패스워드 검증에 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
