package com.puercha.algo.user.service;

import java.util.Date;
import java.util.List;

import com.puercha.algo.user.vo.UserVO;

/**
 * 사용자 기능 정의
 * @author Hyeonuk
 *
 */
public interface UserService {
	//사용자 생성
	int joinUser(UserVO userVO);
	
	//사용자 수정
	int modifyUser(UserVO userVO);
	
	//사용자 전체조회
	List<UserVO> selectAllUser();
	
	//사용자 개별조회
	UserVO selectUser(String email);
	UserVO selectUser(long userNum);
	
	//사용자 삭제
	int outUser(String email, String pw);
	
//	//로그인
//	UserVO loginUser(String email, String pw);
	

	//비밀번호 변경
	int changePW(long userNum,String oldPw,String newPw);
}
