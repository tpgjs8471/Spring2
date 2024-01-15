package com.myweb.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import com.myweb.www.security.CustomAuthMemberService;
import com.myweb.www.security.LoginFailureHandler;
import com.myweb.www.security.LonginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity // 웹보안 활성화를 위한 annotation
@Configuration
@Slf4j
//WebSecurityConfigurerAdapter 상속받아 환경 설정.
// WebConfig 에 SecurityConfig.class 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	// 비밀번호 암호화 객체 PasswordEncoder
	// 빈 생성
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// SuccessHandler => 사용자 커스텀 생성
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LonginSuccessHandler(); // 아직 생성안함
	}
	
	// FailureHandler => 사용자 커스텀 생성
	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new LoginFailureHandler();
	}
	// UserDetail => 사용자 커스텀
	@Bean
	public UserDetailsService customUserService() {
		return new CustomAuthMemberService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 인증되는 객체 설정
		auth.userDetailsService(customUserService())
		.passwordEncoder(bcPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 화면에서 설정되는 권한에 따른 주소 맵핑 설정
		// csrf() 공격에 대한 설정 막기
		http.csrf().disable();
		
		// 승인요청
		// antMatchers : 접근을 허용하는 값
		// antMatchers("/member/list","/결제요청","/상품요청",...)
		//.antMatchers("/member/list").hasRole("ADMIN"); => ADMIN을 가진 사람많이 member/list를 볼 수 있다.
		// permitAll() : 누구나 접근 가능
		// anyRequest : 그 이외의 경로는
		// authenticated() : 인증된 사용자만 가능
		http.authorizeRequests()
		.antMatchers("/member/list").hasRole("ADMIN")
		.antMatchers("/","/board/list","/board/detail","/comment/**","/upload/**","/resources/**","/member/register","/member/login").permitAll()
		.anyRequest().authenticated();
		
		// 커스텀 로그인 페이지를 구성
		//.usernameParameter("email") => memberVO의 변수명
		// .passwordParameter("pwd") => memberVO의 변수명
		// controller에 주소요청 맵핑이 같이 있어야 함 ( ☆☆☆☆☆ 필수 ☆☆☆☆☆)
		// .loginPage("/member/login") => 로그인경로
		http.formLogin()
		.usernameParameter("email")
		.passwordParameter("pwd")
		.loginPage("/member/login")
		.successHandler(authSuccessHandler())	// 로그인 성공 시~
		.failureHandler(authFailureHandler());	// 로그인 실패 시~
		
		// 로그아웃 페이지? 버튼? 아무튼 로그아웃
		// 로그아웃 methd는 반드시 = "post"
		// JSESSIONID 아이디의 쿠키? 같은 개념?
		http.logout()
		.logoutUrl("/member/logout")
		.invalidateHttpSession(true) // 세션 끊기
		.deleteCookies("JSESSIONID")
		.logoutSuccessUrl("/");
	}

	



	
	
}
