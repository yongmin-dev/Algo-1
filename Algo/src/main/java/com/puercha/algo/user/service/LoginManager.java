package com.puercha.algo.user.service;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.puercha.algo.user.dao.UserDAO;
import com.puercha.algo.user.vo.UserVO;

@Service
public class LoginManager implements LoginService{
	private static final Logger logger
	= LoggerFactory.getLogger(LoginManager.class);
	
	@Inject
	UserDAO userDAO;
	// 로그인
	@Override
	public UserVO loginUser(String email, String pw, HttpSession session) {
		logger.info("loginService.loginUser(String email,String pw) 호출됨");
		UserVO user = null;
		// 로그인 여부 확인
		user = userDAO.selectUser(email, pw);
		logger.info("user logs in:"+user);
		if(user!=null) {
			//세션에 로그인된 사용자 정보 넣기 
			session.setAttribute(KEY_USER_INFO, user);
		}else {
			session.invalidate();
		}
		return user;
	}

	/**
	 *  로그인 된 세션 가져오기 
	 * @param session 현재 세션
	 * @return 실패시 null
	 */
	@Override
	public UserVO getLoggedInUser(HttpSession session) {		
		return (UserVO) session.getAttribute(KEY_USER_INFO);
	}

	// 로그아웃
	@Override
	public int logoutUser(HttpSession session) {
		logger.info("logoutUser.logoutUser() 호출됨");
//		session.getAttribute("userInfo");
		UserVO user = (UserVO) session.getAttribute("userInfo");
		if(user != null) {
			session.removeAttribute("userInfo");
			session.invalidate();
			return 1;
		}
		return 0;
	}

}
