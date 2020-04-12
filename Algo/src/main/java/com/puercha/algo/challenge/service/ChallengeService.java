package com.puercha.algo.challenge.service;

import java.util.List;
import java.util.Map;

import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.challenge.vo.ChallengeVO;

/**
 * 도전과제 기능 정의
 * @author Hyeonuk
 *
 */
public interface ChallengeService {
	public final static String KEY_CHALLENGE_LIST = "challengeList"; 
	public final static String KEY_PAGE_INFO = "pageInfo";
	/**
	 * 도전과제 검색하기
	 * @param page 페이지 번호
	 * @param keyword 키워드
	 * @param type 타입
	 * @return
	 */
	Map<String,Object> serachChallenges(long page, String keyword, String type);
	/**
	 * 도전과제 열람
	 * @param ChallengeNum 도젅과제 번호
	 * @return 도전과제 가져옴
	 */
	ChallengeVO viewChallenges(long challengeNum);	
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
	
	/**
	 * 전체 랭킹을 반환함
	 * @param page 랭킹 페이지
	 * @param cNum 도전문제 번호
	 * @param type 타입
	 * @return 랭킹의 리스트 및 페이지 정보
	 */
//	List<ChallengeResultVO> getRankingList(long page, long cNum, String type);
	Map<String,Object> getRankingList(long page, long cNum, String type);
	
	
	/**
	 * user의 랭킹을 검색함
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @param type 검색타입 (메모리기준,사용시간기준)
	 * @return 내 랭킹 결과 (등수, 도전과제결과객체)
	 */
//	Map<String,Object> getRank(long cNum, long userNum, String type);
	/**
	 * user의 랭킹을 검색함
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @param type 검색타입 (메모리기준,사용시간기준)
	 * @return 내 랭킹 결과 (등수, 도전과제결과객체)
	 */
//	Map<String,Object> getRank(long cNum, long userNum, String type);
	
	/**
	 * user의 랭킹을 검색함
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @param type 검색타입 (메모리기준,사용시간기준)
	 * @return 내 랭킹 결과 VO
	 */
	ChallengeResultVO getMyRank(long cNum, long userNum, String type);
	
	/**
	 * 핫한 문제들을 가져옴
	 * @param num 문제 개수
	 * @return 문제 리스트 
	 */
	List<Map<String, Object>> getHotChalllenges(long num);
	/**
	 * 탑 랭커들의 정보를 가져옴
	 * @param rankers 랭커의 수
	 * @return 랭커 정보의 리스트
	 */
	List<Map<String, Object>> getTopRankers(long rankersNum);
}
