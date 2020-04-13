package com.puercha.algo.user.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.puercha.algo.user.dao.UserDAO;
import com.puercha.algo.user.vo.UserVO;

@Service
public class UserManager implements UserService {

	private static final Logger logger
		= LoggerFactory.getLogger(UserManager.class);
	
	@Inject
	UserDAO UserDAO;
	//사용자 생성		
	@Override
	public int joinUser(UserVO userVO) {
		logger.info("UserManager.joinUser(UserVO userVO) 호출됨");
		return UserDAO.insertUser(userVO);
	}
	//사용자 수정
	@Override
	public int modifyUser(UserVO userVO) {
		logger.info("UserManager.modifyUser(UserVO userVO) 호출됨");
		return UserDAO.updateUser(userVO);
	}
	//사용자 전체조회
	@Override
	public List<UserVO> selectAllUser() {
		logger.info("UserManager.selectAllUser() 호출됨");
		return UserDAO.selectAllUser();
	}
	//사용자 개별조회
	@Override
	public UserVO selectUser(String email) {
		logger.info("UserManager.selectUser(String email) 호출됨");
		return UserDAO.selectUser(email);
	}
	@Override
	public UserVO selectUser(long userNum) {
		logger.info("UserManager.selectUser(long userNum) 호출됨");
		return UserDAO.selectUser(userNum);
	}
	//사용자 삭제
	@Override
	public int outUser(String email, String pw) {
		logger.info("UserManager.outUser(String email, String pw) 호출됨");
		return UserDAO.deleteUser(email, pw);
	}
	//로그인
//	@Override
//	public UserVO loginUser(String email, String pw) {
//		logger.info("UserManager.loginUser(String email, String pw) 호출됨");
//		return UserDAO;
//	}
	
	//비밀번호 변경
	@Override
	public int changePW(long userNum,String oldPw,String newPw) {
		logger.info("UserManager.changePW(long userNum,String oldPw,String newPw) 호출됨");
		return UserDAO.changePW(userNum,oldPw, newPw);
	}

}
