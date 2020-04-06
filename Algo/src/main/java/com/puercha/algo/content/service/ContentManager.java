package com.puercha.algo.content.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.puercha.algo.challenge.dao.ChallengeDAO;
import com.puercha.algo.challenge.vo.ChallengeCaseVO;
import com.puercha.algo.challenge.vo.ChallengeVO;
import com.puercha.algo.learning.dao.LearningDAO;
import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;

@Service
public class ContentManager implements ContentManagingService {
	private static final Logger logger = LoggerFactory.getLogger(ContentManager.class);
	
	
	@Inject
	ChallengeDAO challengeDAO;
	
	@Inject
	LearningDAO learingDAO;
	
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

	/**
	 * 과목 리스트를 가져온다.
	 * @param userNum 과목을 작성한 사용자의 userNum
	 * @return 과목의 리스트 객체
	 */
	@Override
	public List<SubjectVO> getSubjectList(long userNum) {
		
		return null;
	}

	
	/**
	 * 과목을 삭제한다.
	 * @param subjectNum 삭제할 과목의 번호
	 * @param userNum 과목을 작성한 사람
	 * @return
	 */
	@Override
	public int deleteSubject(long subjectNum, long userNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 빈 과목 객체를 생성한다.
	 * @param userNum 과목의 작성자의 사용자 번호
	 * @return 성공 시 새 과목의 과목번호
	 */
	@Override
	public long createEmptySubject(long userNum) {
		// TODO Auto-generated method stub
		return 0;
	}


	/**
	 * 과목의 제목을 수정한다.
	 * @param subjectNum 수정할 과목의 과목번호
	 * @param title 제목 
	 * @param userNum 작성자 일치여부 확인용 사용자번호
	 * @return 성공 시 1
	 */
	@Override
	public int updateSubjectTitle(long subjectNum, String title, long userNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 단원 내용을 가져온다.
	 * @param unitNum 가져올 단원 번호
	 * @return 단원의 VO
	 */
	@Override
	public UnitVO getUnitContent(long unitNum) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 단원 내용을 수정 한다.
	 * @param unit 수정될 내용의 단원 VO
	 * @return 성공시 1
	 */
	@Override
	public int updateUnit(UnitVO unit) {
		logger.info("updateUnit(UnitVO unit)");
		return learingDAO.updateUnit(unit);
	}

	/**
	 * 단원 리스트를 가져옴
	 * @param subjectNum 단원이 소속된 과목의 번호
	 * @return 단원VO의 리스트 객체
	 */
	@Override
	public List<UnitVO> getUnitList(long subjectNum) {
		logger.info("getUnitList(long subjectNum)");
		return learingDAO.selectAllUnits(subjectNum);
	}

	/**
	 * 빈 단원을 생성한다.
	 * @param subjectNum 과목 번호
	 * @return 성공 시 생성된 단원의 번호
	 */
	@Override
	public long createEmptyUnit(long subjectNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 단원을 삭제함
	 * @param unitNum 단원 번호
	 * @param userNum 작성자 여부를 확인할 사용자 번호
	 * @return 성공시 1
	 */
	@Override
	public int deleteUnit(long unitNum, long userNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 
	 * 단원의 제목을 변경함
	 * @param unitNum 제목을 변경할 단원의 번호
	 * @param title 변경할 제목
	 * @param userNum 작성자 여부를 확인할 사용자 번호
	 * @return 성공시 1
	 */
	@Override
	public int updateUnitTitle(long unitNum, String title, long userNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**
	 * 마무리 문제 VO의 리스트를 가져옴
	 * @param unitNum 마무리문제가 속한 단원 번호
	 * @return 마무리문제 VO의 리스트 객체
	 */
	@Override
	public List<QuizVO> getQuizList(long unitNum) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * 마무리 문제 VO를 하나 가져옴
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 VO
	 */
	@Override
	public QuizVO getQuiz(long quizNum) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 새 빈 마무리 문제를 생성함 
	 * @param unitNum 마무리문제가 속한 단원의 번호
	 * @return 새 마무리문제의 번호
	 */
	@Override
	public long createEmptyQuiz(long unitNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 마무리문제 를 삭제함
	 * @param quizNum 삭제할 마무리문제 번호 
	 * @param userNum 소유여부 확인할 사용자번호
	 * @return 성공시 1
	 */
	@Override
	public int deleteQuiz(long quizNum, long userNum) {
		// TODO Auto-generated method stub
		return 0;
	}		
	
	/**
	 * 마무리문제를 수정함
	 * @param quiz 마무리문제 VO
	 * @return 성공시 1
	 */
	@Override
	public int updateQuiz(QuizVO quiz) {
		return 0;
	}
	
	
	/**
	 * 마무리문제 답안리스트를 가져옴
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 답안리스트 객체
	 */
	@Override
	public List<QuizVO> getAnswerList(long quizNum){
		return null;
	}
	
	/**
	 * 새 마무리문제 답안을 생성함
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 답안 VO
	 */
	@Override
	public QuizAnswerVO createEmptyAnswer(long quizNum) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 마무리문제 답안을 삭제함
	 * @param answerNum 삭제할 답안 번호
	 * @return 성공시 1
	 */
	@Override
	public int deleteAnswer(long answerNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**
	 * 마무리문제 답안을 수정
	 * @param answer 수정된 VO 객체
	 * @return 성공시 1
	 */
	@Override
	public int updateAnswer(QuizAnswerVO answer) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
