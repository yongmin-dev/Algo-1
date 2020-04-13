package com.puercha.algo.user.dao;

import java.util.List;

import com.puercha.algo.user.vo.TutorApplicationVO;
import com.puercha.algo.user.vo.UserVO;

/**
 * 사용자 데이터 DAO 정의
 * @author Hyeonuk, Youdong
 *
 */
public interface UserDAO {
	
	/* Create */
	//사용자 생성
	int insertUser (UserVO userVO);
	// 신청서 생성
	int insertApplication(TutorApplicationVO tutorApplication);
	
	/* Read */
	//사용자 전체조회
	List<UserVO> selectAllUser();
	//사용자 개별조회
	UserVO selectUser(long userNum);	
	UserVO selectUser(String email);	
	UserVO selectUser(String email, String pw);
	
	/**
	 * 튜터신청서 목록을 가져옴
	 * @param startRowNum 시작 번호
	 * @param endRowNum 끝 번호
	 * @return 리스트
	 */
	List<TutorApplicationVO> selectAllTutorApplication(long startRowNum, long endRowNum);
	/**
	 *  튜터 신청서의 총 개수를 가져온다.
	 * @return 신청서 총 row개수
	 */
	long getTotalApplicationNum();
	// 신청서 하나 조회
	TutorApplicationVO selectOneTutorApplication(long applicationNum);
	/* Update */
	// 사용자 수정
	int updateUser (UserVO userVO);
	int changePW(long userNum,String oldPw,String newPw);
	int changePW(String email,String oldPw,String newPw);
	int updateApplication(TutorApplicationVO appli);
	
	/* Delete */
	// 사용자 삭제
	int deleteUser (String email,String pw);
	int deleteUser (long userNum);

	
	
	
}
