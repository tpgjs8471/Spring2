package com.myweb.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

	private String authEmail;
	
	private String errorMessage;
	
	// 로그인 실패 시
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		setAuthEmail(request.getParameter("email")); // 로그인 할려 했던 ID
		
		// exceptin 발생 시 메세지 저장
		if(exception instanceof BadCredentialsException ||
			exception instanceof InternalAuthenticationServiceException) {
			setErrorMessage(exception.getMessage().toString());
		}
		
		log.info(">>>>> errMSG >>>> "+errorMessage);
		
		request.setAttribute("email", getAuthEmail());
		request.setAttribute("errMsg", getErrorMessage());
		request.getRequestDispatcher("/member/login?error").forward(request, response);
	}


}
