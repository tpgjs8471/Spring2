package com.myweb.www.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myweb.www.repository.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthMemberService implements UserDetailsService {
	
	@Inject
	private MemberDAO mdao;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username이 DB에 설정되어있는 email이 맞는지 체크
		// 인증하여 해당 객체를 AuthMember로 리턴
		// username 이 email
		MemberVO mvo = mdao.selectEmail(username);
		if(mvo == null) { // 해당 유저가 등록되지 않은 유저
			throw new UsernameNotFoundException(username); // security에서 제공하는 throw문
		}
		mvo.setAuthList(mdao.selectAuth(username)); // AuthVO를 채움
		log.info(">>>>> username 확인 >>>>> {}",username);
		log.info(">>>> userDetail 확인 >>>> {}", mvo);
		return new AuthMember(mvo);
	}

}
