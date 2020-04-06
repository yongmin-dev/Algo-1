package com.puercha.algo.content.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.puercha.algo.challenge.dao.ChallengeDAO;
import com.puercha.algo.challenge.vo.ChallengeCaseVO;
import com.puercha.algo.challenge.vo.ChallengeVO;

@Service
public class ContentManager implements ContentManagingService {
	private static final Logger logger = LoggerFactory.getLogger(ContentManager.class);
	
	
	@Inject
	ChallengeDAO challengeDAO;
	/**
	 * 모든 도전과제 리스트를 불러옴
	 * @param userNum 도전과제 작성자의 사용자 번호
	 * @return 도전과제 리스트
	 */
	@Override
	public List<ChallengeVO> getChallengeList(long userNum) {
		return challengeDAO.selectOwnChallengeList(userNum);
	}

	/**
	 * 빈 도전과제 생성
	 * @param userNum 작성자의 번호
	 * @return 생성한 도전과제 번호
	 */
	@Override
	public long createEmptyChallenge(long userNum, String username) {
		ChallengeVO challenge = new ChallengeVO();		
		challenge.setUserNum(userNum);
		challenge.setTitle("새 도전과제");
		challenge.setContent("새 문제를 입력해 주세요");
		challengeDAO.insertChallenge(challenge);
		return challenge.getCNum();
	}
	
	
	/**
	 * 빈 케이스를 생성
	 * @param cNum 생성할 케이스의 도전과제 번호
	 * @return 생성된 도전과제케이스의 번호
	 */
	@Override
	public long createEmptyCase(long cNum, long userNum) {
		ChallengeCaseVO challengeCase = new ChallengeCaseVO();
		challengeCase.setCNum(cNum);
		challengeCase.setInput("input data");
		challengeCase.setOutput("expected result");
		challengeCase.setUserNum(userNum);
		challengeDAO.insertTestCase(challengeCase);
		return challengeCase.getCaseNum();
	}
	
	/**
	 * 해당 도전과제를 삭제한다. 작성자가 일치해야 삭제됨
	 * @param cNum 도전과제 번호
	 * @param userNum 작성자 번호
	 * @return 성공 시 1
	 */
	@Override
	public int deleteChallenge(long cNum, long userNum) {
		ChallengeVO challenge = challengeDAO.selectOne(cNum);
		if(challenge==null || challenge.getUserNum() != userNum) // 작성자 확인
			return 0;
		return challengeDAO.deleteChallenge(cNum);
	}

	/**
	 * 도전과제 제목을 변경함
	 * @param cNum 변경할 도전과제 번호
	 * @param title 도전과제 제목
	 * @return 성공 시 1
	 */
	@Override
	public int updateChallengeTitle(long cNum, String title, long userNum) {
		ChallengeVO challenge = challengeDAO.selectOne(cNum);
		if(challenge==null || challenge.getUserNum() != userNum) // 작성자 확인
			return 0;
		challenge.setTitle(title);
		return challengeDAO.updateChallenge(challenge);
	}

	/**
	 * 도전과제 내용을 가져옴
	 * @param cNum 도전과제 번호
	 * @return 도전과제 VO
	 */
	@Override
	public ChallengeVO getChallengeContent(long cNum) {		
		return challengeDAO.selectOne(cNum);
	}

	/**
	 * 도전과제 내용을 변경함
	 * @param challenge 도전과제 VO
	 * @return 성공 시 1
	 */
	@Override
	public int updateChallenge(ChallengeVO challenge) {
		return challengeDAO.updateChallenge(challenge);
	}

	/**
	 * 도전과제 케이스 목록을 가져옴
	 * @param cNum 도전과제 번호
	 * @return 케이스VO의 리스트 객체
	 */
	@Override
	public List<ChallengeCaseVO> getCaseList(long cNum) {
		return challengeDAO.selectAllCase(cNum);
	}

	/**
	 * 한 케이스 내용을 가져옴
	 * @param caseNum 가져올 케이스의 케이스번호
	 * @return 케이스VO 
	 */
	@Override
	public ChallengeCaseVO getCase(long caseNum) {
		logger.info(String.format("getCase(%d)", caseNum));
		return challengeDAO.selectOneCase(caseNum);
	}


	/**
	 * 케이스를 삭제한다.
	 * @param caseNum 케이스 번호
	 * @return 성공 시 1
	 */
	@Override
	public int deleteCase(long caseNum) {
		logger.info(String.format("deleteCase(%d)", caseNum));
		return challengeDAO.deleteChallengeCase(caseNum);
	}

	/**
	 * 케이스를 업데이트 한다
	 * @param challengeCase 변경할 case의 VO객체 
	 * @return 성공 시 1
	 */
	@Override
	public int updateCase(ChallengeCaseVO challengeCase) {
		logger.info(String.format("updateCase(%d)", challengeCase.getCaseNum()));
		return challengeDAO.updateChallengeCase(challengeCase);
	}
	
	/**
	 * 케이스를 업데이트 한다
	 * @param caseNum 변경할 케이스의 번호
	 * @param input 
	 * @param expected
	 * @return 성공 시 1
	 */
	@Override
	public int updateCase(long caseNum, String input, String expected) {
		ChallengeCaseVO challengeCase = challengeDAO.selectOneCase(caseNum);
		logger.info(String.format("updateCase(%d)", challengeCase.getCaseNum()));
		challengeCase.setInput(input);
		challengeCase.setOutput(expected);
		return challengeDAO.updateChallengeCase(challengeCase);
	}
}
