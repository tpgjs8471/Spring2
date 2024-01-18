package com.myweb.www.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.myweb.www.security.MemberVO;

public interface MemberService {

	int register(MemberVO mvo);

	boolean updateLastLogin(String authEmail);

	MemberVO detail(String email);

	List<MemberVO> getlist();

	int updatePw(MemberVO mvo);

	int modifyPwdEmpty(MemberVO mvo);

	int remove(String email);
}
