package com.puercha.algo.user.service;

import javax.servlet.http.HttpSession;

import com.puercha.algo.user.vo.UserVO;

/**
 * 사용자 로그인 관련 기능 정의
 * @author Hyeonuk
 *
 */
public interface LoginService {
	// 로그인
	UserVO loginUser(String email, String pw, HttpSession session);
	// 로그아웃
	int logoutUser(HttpSession session);
}
