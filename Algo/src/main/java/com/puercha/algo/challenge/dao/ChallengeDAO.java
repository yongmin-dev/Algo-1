package com.puercha.algo.challenge.dao;
/**
 * 도전과제 DAO 기능 정의
 * @author Hyeonuk
 *
 */

import java.util.List;

import com.puercha.algo.challenge.vo.ChallengeCaseVO;
import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.challenge.vo.ChallengeVO;

public interface ChallengeDAO {
	
	/* Create */	
	/**
	 * 도전과제 생성
	 * @param challenge 생성할 도전과제 VO
	 * @return 성공 시 1 반환
	 */
	int insertChallenge(ChallengeVO challenge);
	
	/**
	 * 도전과제 결과 생성
	 * @param result 생성할 도전과제 결과 VO
	 * @return
	 */
	int insertChallengeResult(ChallengeResultVO result);
	
	/**
	 * 도전과제 테스트 케이스 생성
	 * @param testCase 새로운 테스트 케이스
	 * @return 성공시 1
	 */
	int insertTestCase(ChallengeCaseVO testCase);
	
	/* Read */	
	/**
	 * 도전과제 row 1개 열람
	 * @param cNum 도전과제 번호
	 * @return 도전과제 VO
	 */
	ChallengeVO selectOne(long cNum);
	
	/**
	 * 도전과제 목록 검색
	 * @param startRowNum 시작 row
	 * @param endRowNum 끝 row
	 * @param type 검색 타입
	 * @param keyword 검색 keyword
	 * @return 도전과제VO의 리스트 객체
	 */
	List<ChallengeVO> selectAllChallenge(long startRowNum, long endRowNum, String type, String keyword);
		
	 /**
	  * 도전과제 결과 보기 
	  * @param resultNum 결과 번호
	  * @return 도전과제 결과VO
	  */
	ChallengeResultVO selectOneResult(long resultNum);
	
	 /**
	  * 전체 도전과제 결과 보기
	  * @param startRowNum 시작 행
	  * @param endRowNum 끝 행
	  * @return 도전과제결과 List
	  */
	List<ChallengeResultVO> selectAllResult(long startRowNum, long endRowNum);
	 
	 /**
	  * 문제 내 전체 도전과제 결과 보기
	  * @param startRowNum 시작 행
	  * @param endRowNum 끝 행
	  * @param cNum 도전과제 번호
	  * @return 도전과제결과 List
	  */
	List<ChallengeResultVO> selectAllResult(long startRowNum, long endRowNum, long cNum);
	
	 
	/**
	 * 문제의 내가 제출한 도전과제 결과 보기
	 * @param startRowNum 시작 행
	 * @param endRowNum 끝 행
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @return 도전과제결과 List
	 */
	List<ChallengeResultVO> selectAllResult(long startRowNum, long endRowNum, long cNum, long userNum);

//	ChallengeResultVO getLastResult(long cNum);
	
	/**
	 * 도전과제의 결과 중 가장 최근 과제 결과를 가져옴
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @return 가장최근 결과
	 */
	ChallengeResultVO getLastResult(long cNum, long userNum);
	
	
	/**
	 * 모든 도전과제의 모든 결과 개수를 가져옴
	 * @return 결과개수
	 */
	long getCountTotalResult();
	/**
	 * 도전과제의 모든 결과 개수를 가져옴
	 * @param cNum
	 * @return 결과 개수
	 */
	long getCountTotalResult(long cNum);
	
	
	/**
	 * 케이스 번호로 케이스 데이터 하나 열람 
	 * @param caseNum 케이스 번호
	 * @return 테스트케이스VO
	 */
	ChallengeCaseVO selectOneCase(long caseNum);
	
	/**
	 * 도전과제내 모든 케이스를 가져옴 
	 * @param cNum 도전과제 번호
	 * @return 테스트케이스 VO의 리스트
	 */
	List<ChallengeCaseVO> selectAllCase(long cNum);
	
	/* Update */
	/**
	 * 도전과제 변경
	 * @param challenge 수정된 VO
	 * @return 성공 시 1
	 */
	int updateChallenge(ChallengeVO challenge);
	
	/**
	 * 도전과제 결과 변경
	 * @param result
	 * @return 성공 시 1
	 */
	int updateChallengeResult(ChallengeResultVO result);
	
	/**
	 * 도전과제 테스트케이스 변경
	 * @param testCase  수정된 VO
	 * @return 성공 시 1
	 */
	int updateChallengeCase(ChallengeCaseVO testCase);
	
	
	
	/* Delete */
	/**
	 * 도전과제 삭제
	 * @param cNum 도전과제 번호
	 * @return 성공 시 1
	 */
	int deleteChallenge(long cNum);
	
	/**
	 * 도전과제 결과 삭제
	 * @param resultNum
	 * @return 성공 시 1
	 */
	int deleteChallengeReseult(long resultNum);	
	
	/**
	 * 도전과제 테스트케이스 삭제
	 * @param caseNum 케이스 번호
	 * @return 성공 시 1
	 */
	int deleteChallengeCase(long caseNum);
	
	/**
	 * 도전과제에 포함된 모든 테스트케이스삭제
	 * @param cNum
	 * @return
	 */
	int deleteAllCase(long cNum);
}
