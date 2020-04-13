package com.puercha.algo.content.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	LearningDAO learningDAO;

	/**
	 * 모든 도전과제 리스트를 불러옴
	 * 
	 * @param userNum 도전과제 작성자의 사용자 번호
	 * @return 도전과제 리스트
	 */
	@Override
	public List<ChallengeVO> getChallengeList(long userNum) {
		return challengeDAO.selectOwnChallengeList(userNum);
	}

	/**
	 * 빈 도전과제 생성
	 * 
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
	 * 
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
	 * 
	 * @param cNum    도전과제 번호
	 * @param userNum 작성자 번호
	 * @return 성공 시 1
	 */
	@Override
	public int deleteChallenge(long cNum, long userNum) {
		ChallengeVO challenge = challengeDAO.selectOne(cNum);
		if (challenge == null || challenge.getUserNum() != userNum) // 작성자 확인
			return 0;
		return challengeDAO.deleteChallenge(cNum);
	}

	/**
	 * 도전과제 제목을 변경함
	 * 
	 * @param cNum  변경할 도전과제 번호
	 * @param title 도전과제 제목
	 * @return 성공 시 1
	 */
	@Override
	public int updateChallengeTitle(long cNum, String title, long userNum) {
		ChallengeVO challenge = challengeDAO.selectOne(cNum);
		if (challenge == null || challenge.getUserNum() != userNum) // 작성자 확인
			return 0;
		challenge.setTitle(title);
		return challengeDAO.updateChallenge(challenge);
	}

	/**
	 * 도전과제 내용을 가져옴
	 * 
	 * @param cNum 도전과제 번호
	 * @return 도전과제 VO
	 */
	@Override
	public ChallengeVO getChallengeContent(long cNum) {
		return challengeDAO.selectOne(cNum);
	}

	/**
	 * 도전과제 내용을 변경함
	 * 
	 * @param challenge 도전과제 VO
	 * @return 성공 시 1
	 */
	@Override
	public int updateChallenge(ChallengeVO challenge) {
		return challengeDAO.updateChallenge(challenge);
	}

	/**
	 * 도전과제 케이스 목록을 가져옴
	 * 
	 * @param cNum 도전과제 번호
	 * @return 케이스VO의 리스트 객체
	 */
	@Override
	public List<ChallengeCaseVO> getCaseList(long cNum) {
		return challengeDAO.selectAllCase(cNum);
	}

	/**
	 * 한 케이스 내용을 가져옴
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @param caseNum  변경할 케이스의 번호
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
	 * 
	 * @param userNum 과목을 작성한 사용자의 userNum
	 * @return 과목의 리스트 객체
	 */
	@Override
	public List<SubjectVO> getUserSubjectList(long userNum) {
		logger.info("getSubjectList(long userNum)");
		return learningDAO.selectAllUserSubjects(userNum);
	}

	/**
	 * 과목을 삭제한다.
	 * 
	 * @param subjectNum 삭제할 과목의 번호
	 * @param userNum    과목을 작성한 사람
	 * @return
	 */
	@Override
	public int deleteSubject(long subjectNum, long userNum) {
		logger.info("deleteSubject(long subjectNum, long userNum)");
		SubjectVO subject = learningDAO.selectOneSubject(subjectNum);
		if (subject != null && subject.getUserNum() == userNum) {
			return learningDAO.deleteSubject(subjectNum);
		}
		return 0;
	}

	/**
	 * 빈 과목 객체를 생성한다.
	 * 
	 * @param userNum 과목의 작성자의 사용자 번호
	 * @return 성공 시 새 과목의 과목번호
	 */
	@Override
	public long createEmptySubject(long userNum) {
		SubjectVO subject = new SubjectVO();
		subject.setTitle("새 과목");
		subject.setUserNum(userNum);
		learningDAO.insertSubject(subject);
		return subject.getSubjectNum();
	}

	/**
	 * 과목의 제목을 수정한다.
	 * 
	 * @param subjectNum 수정할 과목의 과목번호
	 * @param title      제목
	 * @param userNum    작성자 일치여부 확인용 사용자번호
	 * @return 성공 시 1
	 */
	@Override
	public int updateSubjectTitle(long subjectNum, String title, long userNum) {
		logger.info("updateSubjectTitle(long subjectNum, String title, long userNum)");
		SubjectVO subject = learningDAO.selectOneSubject(subjectNum);
		if (userNum != subject.getUserNum())
			return -1;
		subject.setTitle(title);
		return learningDAO.updateSubject(subject);
	}

	/**
	 * 단원 내용을 가져온다.
	 * 
	 * @param unitNum 가져올 단원 번호
	 * @return 단원의 VO
	 */
	@Override
	public UnitVO getUnitContent(long unitNum) {
		logger.info("getUnitContent(long unitNum)");
		return learningDAO.selectOneUnit(unitNum,0);
	}

	/**
	 * 단원 내용을 수정 한다.
	 * 
	 * @param unit 수정될 내용의 단원 VO
	 * @return 성공시 1
	 */
	@Override
	public int updateUnit(UnitVO unit) {
		logger.info("updateUnit(UnitVO unit)");
		return learningDAO.updateUnit(unit);
	}

	/**
	 * 단원 리스트를 가져옴
	 * 
	 * @param subjectNum 단원이 소속된 과목의 번호
	 * @return 단원VO의 리스트 객체
	 */
	@Override
	public List<UnitVO> getUnitList(long subjectNum) {
		logger.info("getUnitList(long subjectNum)");
		return learningDAO.selectAllUnitMetadatas(subjectNum,0);
	}

	/**
	 * 빈 단원을 생성한다.
	 * 
	 * @param subjectNum 과목 번호
	 * @return 성공 시 생성된 단원의 번호
	 */
	@Override
	public long createEmptyUnit(long subjectNum, String chapterDepth) {
		logger.info("getUnitList(long subjectNum)");
		UnitVO unit = new UnitVO();
		unit.setChapterDepth(chapterDepth);
		unit.setSubjectNum(subjectNum);
		unit.setContent("내용을 입력해주세요~");
		unit.setTitle("새 단원");
		int result = learningDAO.insertUnit(unit);
		if (result == 1)
			return unit.getUnitNum();
		return -1;
	}

	/**
	 * 단원을 삭제함
	 * 
	 * @param unitNum 단원 번호
	 * @param userNum 작성자 여부를 확인할 사용자 번호
	 * @return 성공시 1
	 */
	@Override
	public int deleteUnit(long unitNum, long userNum) {
		logger.info("deleteUnit(long unitNum, long userNum)");
		logger.info(String.format("unitNum: %d, userNum %d", unitNum, userNum));
		UnitVO deletionTarget = learningDAO.selectOneUnit(unitNum,0);
		if(deletionTarget ==null)
			return -1;
		SubjectVO subject = learningDAO.selectOneSubject(deletionTarget.getSubjectNum());
		if (deletionTarget != null) {
			if (subject.getUserNum() == userNum) { // 단원의 주인인지 확인
				return learningDAO.deleteUnit(unitNum);
			}
		}
		return -1;
	}

	/**
	 * 단원의 제목을 변경함
	 * 
	 * @param unitNum 제목을 변경할 단원의 번호
	 * @param title   변경할 제목
	 * @param userNum 작성자 여부를 확인할 사용자 번호
	 * @return 성공시 1
	 */
	@Override
	public int updateUnitTitle(long unitNum, String title, long userNum) {
		UnitVO modifyingTarget = learningDAO.selectOneUnit(unitNum,userNum);
		modifyingTarget.setTitle(title);
		SubjectVO subject = learningDAO.selectOneSubject(modifyingTarget.getSubjectNum());
		if (modifyingTarget != null) {
			if (subject.getUserNum() == userNum) { // 단원의 주인인지 확인
				return learningDAO.updateUnit(modifyingTarget);
			}
		}
		return 0;
	}

	/**
	 * 단원의 depth를 설정한다.
	 * 
	 * @param unitNum 단원 번호
	 * @param depth
	 * @param userNum 작성자 확인용 번호
	 * @return 성공 시 1
	 */
	@Override
	public int updateUnitDepth(long unitNum, String depth, long userNum) {
		logger.info(String.format("unitNum: %d, depth: %s, userNum %d", unitNum, depth, userNum));
		UnitVO modifyingTarget = learningDAO.selectOneUnit(unitNum,userNum);
		modifyingTarget.setChapterDepth(depth);
		SubjectVO subject = learningDAO.selectOneSubject(modifyingTarget.getSubjectNum());
		if (modifyingTarget != null) {
			logger.info("modifyingTarget != null");
			if (subject.getUserNum() == userNum) { // 단원의 주인인지 확인
				return learningDAO.updateUnit(modifyingTarget);
			}
			logger.info("subject.getUserNum() == userNum"+(subject.getUserNum() == userNum)+" "+ subject.getUserNum() +", "+userNum);
		}
		return 0;
	}

	/**
	 * 마무리 문제 VO의 리스트를 가져옴
	 * 
	 * @param unitNum 마무리문제가 속한 단원 번호
	 * @return 마무리문제 VO의 리스트 객체
	 */
	@Override
	public List<QuizVO> getQuizList(long unitNum) {
		logger.info("getQuizList(long unitNum)");
		return learningDAO.selectAllQuiz(unitNum);
	}

	/**
	 * 마무리 문제 VO를 하나 가져옴
	 * 
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 VO
	 */
	@Override
	public QuizVO getQuiz(long quizNum) {
		logger.info("getQuiz(long quizNum)");
		return learningDAO.selectOneQuiz(quizNum);
	}

	/**
	 * 새 빈 마무리 문제를 생성함
	 * 
	 * @param unitNum 마무리문제가 속한 단원의 번호
	 * @param userNum 사용자 번호
	 * @return 새 마무리문제의 번호
	 */
	@Override
	public long createEmptyQuiz(long unitNum, long userNum) {
		QuizVO quiz = new QuizVO();
		quiz.setTitle("새 퀴즈");
		quiz.setDifficulty(1);
		quiz.setSolution("풀이");
		quiz.setUnitNum(unitNum);
		quiz.setContent("내용을 추가해주세요");
		quiz.setUserNum(userNum);// 작성자
		int result = learningDAO.insertQuiz(quiz);
		if (result == 1) {
			return quiz.getQuizNum();
		}
		return 0;
	}

	/**
	 * 마무리문제 를 삭제함
	 * 
	 * @param quizNum 삭제할 마무리문제 번호
	 * @param userNum 소유여부 확인할 사용자번호
	 * @return 성공시 1
	 */
	@Override
	public int deleteQuiz(long quizNum, long userNum) {
		logger.info("deleteQuiz(long quizNum, long userNum)");
		QuizVO quiz = learningDAO.selectOneQuiz(quizNum);
		if (quiz != null && quiz.getUserNum() == userNum) {
			return learningDAO.deleteQuiz(quizNum);
		}
		return 0;
	}

	/**
	 * 마무리문제를 수정함
	 * 
	 * @param quiz 마무리문제 VO
	 * @return 성공시 1
	 */
	@Override
	public int updateQuiz(QuizVO quiz) {
		logger.info("updateQuiz(QuizVO quiz)");
		return learningDAO.updateQuiz(quiz);
	}

	/**
	 * 수정할 수 있는 데이터만 수정함
	 * 
	 * @param quizNum 퀴즈 번호
	 * @param datas   수정할 데이터가 든 map
	 * @return 성공 시 1
	 */
	@Override
	public int updateQuiz(long quizNum, Map<String, Object> datas) {
		logger.info("updateQuiz(long quizNum, Map<String, Object> datas)");
		QuizVO quiz = learningDAO.selectOneQuiz(quizNum);
		if (quiz != null) {
			Set<String> keySet = datas.keySet();
			for (String key : keySet) {
				Object object = datas.get(key);
				String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
				Method[] methods = quiz.getClass().getDeclaredMethods();
				for (Method method : methods) {
					if (method.getName().equals(methodName)) {
						try {
							method.invoke(quiz, object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}

			}

			return learningDAO.updateQuiz(quiz);
		}
		return 0;
	}

	/**
	 * 마무리문제 답안리스트를 가져옴
	 * 
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 답안리스트 객체
	 */
	@Override
	public List<QuizAnswerVO> getAnswerList(long quizNum) {
		logger.info("getAnswerList(long quizNum)");
		return learningDAO.selectAllAnswer(quizNum);
	}

	/**
	 * 새 마무리문제 답안을 생성함
	 * 
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 답안 VO
	 */
	@Override
	public long createEmptyAnswer(long quizNum) {
		logger.info("createEmptyAnswer(long quizNum)");
		QuizAnswerVO answer = new QuizAnswerVO();
		answer.setQuizNum(quizNum);
		answer.setContent("새 답안");
		answer.setCorrect(true);
		int result = learningDAO.insertAnswer(answer);
		if (answer != null && result == 1) {
			return answer.getAnswerNum();
		}
		return 0;
	}

	/**
	 * 답안 내용을 가져온다.
	 * 
	 * @param answerNum 답안 번호
	 * @return 답안의 VO
	 */
	@Override
	public QuizAnswerVO getAnswer(long answerNum) {
		logger.info("getAnswer(long answerNum)");
		return learningDAO.selectOneAnswer(answerNum);
	}

	/**
	 * 마무리문제 답안을 삭제함
	 * 
	 * @param answerNum 삭제할 답안 번호
	 * @return 성공시 1
	 */
	@Override
	public int deleteAnswer(long answerNum) {
		logger.info("deleteAnswer(long answerNum)");
		return learningDAO.deleteAnswer(answerNum);
	}

	/**
	 * 마무리문제 답안을 수정
	 * 
	 * @param answer 수정된 VO 객체
	 * @return 성공시 1
	 */
	@Override
	public int updateAnswer(QuizAnswerVO answer) {
		logger.info("updateAnswer(QuizAnswerVO answer)");
		return learningDAO.updateAnswer(answer);
	}

	/**
	 * 마무리문제 답안을 수정
	 * 
	 * @param answerNum 답안 번호
	 * @param datas     수정할 데이터가 든 map
	 * @return 성공 시 1
	 */
	@Override
	public int updateAnswer(long answerNum, Map<String, Object> datas) {
		logger.info("updateAnswer(long answerNum, Map<String,Object> datas)");
		QuizAnswerVO answer = learningDAO.selectOneAnswer(answerNum);
		if (answer != null) {
			Set<String> keySet = datas.keySet();
			for (String key : keySet) {
				Object object = datas.get(key);
				String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
				Method[] methods = answer.getClass().getDeclaredMethods();
				for (Method method : methods) {
					if (method.getName().equals(methodName)) {
						try {
							method.invoke(answer, object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}

			}

			return learningDAO.updateAnswer(answer);
		}
		return 0;
	}
	
	/**
	 * 과목번호로 과목의 데이터를 조회한다.
	 * @param subjectNum 조회할 과목 번호
	 * @return 과목 VO
	 */
	@Override
	public SubjectVO getSubject(long subjectNum) {
		logger.info("getSubject(long subjectNum)");
		return learningDAO.selectOneSubject(subjectNum);
	}

	
	/**
	 * 과목의 이미지를 변경함
	 * @param subjectNum 변경할 과목의 번호
	 * @param file 이미지 파일
	 * @return 성공시 1
	 */
	@Override
	public int changeImage(long subjectNum, MultipartFile file) {
		SubjectVO subject = learningDAO.selectOneSubject(subjectNum);
		if(subject!=null) {
			subject.setImageName(file.getName());
			subject.setImageSize(file.getSize());
			subject.setImageType(file.getContentType());
			try {
				subject.setImageData(file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return learningDAO.updateSubject(subject);
		}
		return 0;
	}
}
