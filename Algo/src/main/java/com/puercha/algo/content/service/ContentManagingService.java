package com.puercha.algo.content.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.puercha.algo.challenge.vo.ChallengeCaseVO;
import com.puercha.algo.challenge.vo.ChallengeVO;
import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;

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
	/**
	 * 케이스를 업데이트 한다
	 * @param caseNum 변경할 케이스의 번호
	 * @param input 
	 * @param expected
	 * @return 성공 시 1
	 */
	int updateCase(long caseNum, String input, String expected);
	
	/**
	 * 과목 리스트를 가져온다.
	 * @param userNum 과목을 작성한 사용자의 userNum
	 * @return 과목의 리스트 객체
	 */
	List<SubjectVO> getUserSubjectList(long userNum);

	/**
	 * 과목을 삭제한다.
	 * @param subjectNum 삭제할 과목의 번호
	 * @param userNum 과목을 작성한 사람
	 * @return 성공 시 1
	 */
	int deleteSubject(long subjectNum, long userNum);

	/**
	 * 빈 과목 객체를 생성한다.
	 * @param userNum 과목의 작성자의 사용자 번호
	 * @return 성공 시 새 과목의 과목번호
	 */
	long createEmptySubject(long userNum);

	
	/**
	 * 과목의 제목을 수정한다.
	 * @param subjectNum 수정할 과목의 과목번호
	 * @param title 제목 
	 * @param userNum 작성자 일치여부 확인용 사용자번호
	 * @return 성공 시 1
	 */
	int updateSubjectTitle(long subjectNum, String title, long userNum);

	/**
	 * 단원 내용을 가져온다.
	 * @param unitNum 가져올 단원 번호
	 * @return 단원의 VO
	 */
	UnitVO getUnitContent(long unitNum);

	/**
	 * 단원 내용을 수정 한다.
	 * @param unit 수정될 내용의 단원 VO
	 * @return 성공시 1
	 */
	int updateUnit(UnitVO unit);

	
	/**
	 * 단원 리스트를 가져옴
	 * @param subjectNum 단원이 소속된 과목의 번호
	 * @return 단원VO의 리스트 객체
	 */
	List<UnitVO> getUnitList(long subjectNum);

	/**
	 * 빈 단원을 생성한다.
	 * @param subjectNum 과목 번호
	 * @return 성공 시 생성된 단원의 번호
	 */
	long createEmptyUnit(long subjectNum, String chapterDepth);

	/**
	 * 단원을 삭제함
	 * @param unitNum 단원 번호
	 * @param userNum 작성자 여부를 확인할 사용자 번호
	 * @return 성공시 1
	 */
	int deleteUnit(long unitNum, long userNum);

	
	/** 
	 * 단원의 제목을 변경함
	 * @param unitNum 제목을 변경할 단원의 번호
	 * @param title 변경할 제목
	 * @param userNum 작성자 여부를 확인할 사용자 번호
	 * @return 성공시 1
	 */
	int updateUnitTitle(long unitNum, String title, long userNum);

	/**
	 * 마무리 문제 VO의 리스트를 가져옴
	 * @param unitNum 마무리문제가 속한 단원 번호
	 * @return 마무리문제 VO의 리스트 객체
	 */
	List<QuizVO> getQuizList(long unitNum);

	
	/**
	 * 마무리 문제 VO를 하나 가져옴
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 VO
	 */
	QuizVO getQuiz(long quizNum);

	/**
	 * 새 빈 마무리 문제를 생성함 
	 * @param unitNum 마무리문제가 속한 단원의 번호
	 * @param userNum 사용자 번호
	 * @return 새 마무리문제의 번호
	 */
	long createEmptyQuiz(long unitNum, long userNum);

	/**
	 * 마무리문제 를 삭제함
	 * @param quizNum 삭제할 마무리문제 번호 
	 * @param userNum 소유여부 확인할 사용자번호
	 * @return 성공시 1
	 */
	int deleteQuiz(long quizNum, long userNum);

	/**
	 * 마무리문제를 수정함
	 * @param quiz 마무리문제 VO
	 * @return 성공시 1
	 */
	int updateQuiz(QuizVO quiz);
	/**
	 * 수정할 수 있는 데이터만 수정함
	 * @param quizNum 퀴즈 번호
	 * @param datas 수정할 데이터가 든  map 
	 * @return 성공 시 1
	 */
	int updateQuiz(long quizNum, Map<String,Object> datas);
	
	/**
	 * 마무리문제 답안리스트를 가져옴
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 답안리스트 객체
	 */
	List<QuizAnswerVO> getAnswerList(long quizNum);

	/**
	 * 새 마무리문제 답안을 생성함
	 * @param quizNum 마무리문제 번호
	 * @return 마무리문제 답안 VO
	 */
	long createEmptyAnswer(long quizNum);

	/**
	 * 마무리문제 답안을 삭제함
	 * @param answerNum 삭제할 답안 번호
	 * @return 성공시 1
	 */
	int deleteAnswer(long answerNum);

	/**
	 * 마무리문제 답안을 수정
	 * @param answer 수정된 VO 객체
	 * @return 성공시 1
	 */
	int updateAnswer(QuizAnswerVO answer);

	/**
	 * 마무리문제 답안을 수정
	 * @param answerNum 답안 번호
	 * @param datas 수정할 데이터가 든 map
	 * @return 성공 시 1
	 */
	int updateAnswer(long answerNum, Map<String,Object> datas);
	/**
	 * 단원의 depth를 설정한다.
	 * @param unitNum 단원 번호
	 * @param depth 
	 * @param userNum 작성자 확인용 번호
	 * @return 성공 시 1 
	 */
	int updateUnitDepth(long unitNum, String depth, long userNum);

	/**
	 * 답안 내용을 가져온다.
	 * @param answerNum 답안 번호
	 * @return 답안의 VO
	 */
	QuizAnswerVO getAnswer(long answerNum);

	/**
	 * 과목번호로 과목의 데이터를 조회한다.
	 * @param subjectNum 조회할 과목 번호
	 * @return 과목 VO
	 */
	SubjectVO getSubject(long subjectNum);

	/**
	 * 과목의 이미지를 변경함
	 * @param subjectNum 변경할 과목의 번호
	 * @param file 이미지 파일
	 * @return 성공시 1
	 */
	int changeImage(long subjectNum, MultipartFile file);

}
