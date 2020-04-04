package com.puercha.algo.content.service;

import java.util.List;

import com.puercha.algo.challenge.vo.ChallengeCaseVO;
import com.puercha.algo.challenge.vo.ChallengeVO;

/**
 * 컨텐츠 관리 기능 정의
 * @author Hyeonuk
 *
 */
public interface ContentManagingService {
	/**
	 * 모든 도전과제 리스트를 불러옴
	 * @param userNum 도전과제 작성자의 사용자 번호
	 * @return 도전과제 리스트
	 */
	List<ChallengeVO> getChallengeList(long userNum);
	
	/**
	 * 빈 도전과제 생성
	 * @param userNum 작성자의 번호
	 * @param username 작성자 이름
	 * @return 생성한 도전과제 번호
	 */
	long createEmptyChallenge(long userNum, String username);
	
	/**
	 * 빈 케이스를 생성
	 * @param cNum 생성할 케이스의 도전과제 번호
	 * @return 생성된 도전과제케이스의 번호
	 */
	long createEmptyCase(long cNum, long userNum);
	
	
	/**
	 * 해당 도전과제를 삭제한다. 작성자가 일치해야 삭제됨
	 * @param cNum 도전과제 번호
	 * @param userNum 작성자 번호
	 * @return 성공 시 1
	 */
	int deleteChallenge(long cNum, long userNum);
	
	/**
	 * 도전과제 제목을 변경함
	 * @param cNum 변경할 도전과제 번호
	 * @param title 도전과제 제목
	 * @return 성공 시 1
	 */
	int updateChallengeTitle(long cNum, String title, long userNum);
	
	/**
	 * 도전과제 내용을 가져옴
	 * @param cNum 도전과제 번호
	 * @return 도전과제 VO
	 */
	ChallengeVO getChallengeContent(long cNum);
	
	/**
	 * 도전과제 내용을 변경함
	 * @param challenge 도전과제 VO
	 * @return 성공 시 1
	 */
	int updateChallenge(ChallengeVO challenge);
	
	/**
	 * 도전과제 케이스 목록을 가져옴
	 * @param cNum 도전과제 번호
	 * @return 케이스VO의 리스트 객체
	 */
	List<ChallengeCaseVO> getCaseList(long cNum);
	
	/**
	 * 한 케이스 내용을 가져옴
	 * @param caseNum 가져올 케이스의 케이스번호
	 * @return 케이스VO 
	 */
	ChallengeCaseVO getCase(long caseNum);
	
	/**
	 * 새 케이스를 생성한다.
	 * @param userNum 케이스 작성자 번호
	 * @param cNum 도전과제 번호
	 * @return 생성된 케이스의 번호
	 */
	long createCase(long userNum, long cNum);
	
	/**
	 * 케이스를 삭제한다.
	 * @param caseNum 케이스 번호
	 * @return 성공 시 1
	 */
	int deleteCase(long caseNum);
	
	/**
	 * 케이스를 업데이트 한다
	 * @param challengeCase 변경할 case의 VO객체 
	 * @return 성공 시 1
	 */
	int updateCase(ChallengeCaseVO challengeCase);
}
