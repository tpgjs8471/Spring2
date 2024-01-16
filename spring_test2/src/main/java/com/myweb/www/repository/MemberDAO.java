package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.security.AuthVO;
import com.myweb.www.security.MemberVO;

public interface MemberDAO {

	MemberVO getUser(String email);

	int insert(MemberVO mvo);

	int insertAuthInit(String email);

	MemberVO selectEmail(String username);

	List<AuthVO> selectAuth(String username);

	int updateLastLogin(String authEmail);

	List<MemberVO> selectAllList();

	int modifyPwdEmpty(MemberVO mvo);
	
	int updatePw(MemberVO mvo);

	void removeAuth(String email);

	int remove(String email);

}
