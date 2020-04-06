package com.puercha.algo.user.service;

import javax.servlet.http.HttpSession;

import com.puercha.algo.user.vo.UserVO;

/**
 * 사용자 로그인 관련 기능 정의
 * @author Hyeonuk
 *
 */
public interface LoginService {
	public static final String KEY_USER_INFO = "userInfo"; 

	// 로그인
	UserVO loginUser(String email, String pw, HttpSession session);
	// 로그아웃
	int logoutUser(HttpSession session);
	/**
	 *  로그인 된 세션 가져오기 
	 * @param session 현재 세션
	 * @return 실패시 null
	 */
	UserVO getLoggedInUser(HttpSession session);
}
