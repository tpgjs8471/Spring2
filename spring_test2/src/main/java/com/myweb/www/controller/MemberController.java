package com.myweb.www.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.security.MemberVO;
import com.myweb.www.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService msv;
	private final BCryptPasswordEncoder bcEncoder;

	@GetMapping("/register")
	public void register() {} // register 로 페이지 이동
	@GetMapping("/login")
	public void login() {} // login 페이지 이동
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		
		mvo.setPwd(bcEncoder.encode(mvo.getPwd()));
		log.info("register >>>>>>>>>> mvo >>>>>> {}", mvo);		
		int isOk = msv.register(mvo);
		
		return "index";
	}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, RedirectAttributes re) {
		// 로그인 실패시 다시 로그인 페이지로 돌아와 오류 메시지 전송
		// 다시 로그인 유도
		re.addAttribute("email",request.getAttribute("email"));
		re.addAttribute("errMsg",request.getAttribute("errMsg"));
		return "redirect:/member/login";
	}
	
}