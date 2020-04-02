package com.puercha.algo.challenge.service;

import java.util.List;

import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.challenge.vo.ChallengeVO;

/**
 * 도전과제 기능 정의
 * @author Hyeonuk
 *
 */
public interface ChallengeService {
	
	/**
	 * 도전과제 검색하기
	 * @param page 페이지 번호
	 * @param keyword 키워드
	 * @param type 타입
	 * @return
	 */
	List<ChallengeVO> serachChallenges(long page, String keyword, String type);
	/**
	 * 도전과제 열람
	 * @param ChallengeNum 도젅과제 번호
	 * @return 도전과제 가져옴
	 */
	ChallengeVO viewChallenges(long ChallengeNum);	
	// 도전과제 생성
	/**
	 * 도전과제 생성
	 * @param challenge 새로운 도전과제
	 * @return 성공 시 1
	 */
	int createdNewChallenge(ChallengeVO challenge);
	
	/**
	 * 도전과제 삭제
	 * @param ChallengeNum
	 * @return 도전과제 번호
	 */
	int deleteChallenge(long ChallengeNum);
	
	
	/**
	 * 코드의 테스트 실행
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @param code 코드 
	 * @return 성공시 생성된 도전과제 결과 VO 반환
	 */
	ChallengeResultVO  doTest(long cNum,long userNum, String code);
	/**
	 * 코드 실행 결과
	 * @param resultNum 결과 번호
	 * @return 도전과제 결과 VO
	 */	
	ChallengeResultVO getResult(long resultNum);
}
