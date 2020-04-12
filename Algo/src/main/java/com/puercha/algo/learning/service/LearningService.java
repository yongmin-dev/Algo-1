package com.puercha.algo.learning.service;

import java.util.List;
import java.util.Map;

import com.puercha.algo.common.PageManager;
import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;

/**
 * 이론학습 기능 정의 
 * @author Hyeonuk
 *
 */
public interface LearningService {
	
	//과목 리스트 보기
	//리스트 + 검색타입, 검색어
	List<SubjectVO> subjectList(String reqPage, String searchType, String keyword);
	
	//페이지제어
	PageManager getPageManager(String reqPage, String searchType, String keyword);
	
	//단원 불러오기
	List<UnitVO> unitList(String subjectNum);
	
	//단원 내용 불러오기
	Map<String, Object> viewUnit(String unitNum);
	
	//마무리문제 불러오기
	List<QuizVO> viewQuiz(String unitNum);
	
	//마무리문제 답지 불러오기
	List<QuizAnswerVO> viewQuizAnswer(String unitNum);
	
	/**
	 * 마무리문제 답안 체크하기
	 * @param quizNum 문제 번호
	 * @param answers 답
	 * @param userNum 사용자 번호
	 * @return 결과 데이터
	 */
	Map<String,Object> checkAnswers(long quizNum, List<Long> answers, long userNum);

	/** 
	 * 과목리스트를 가져온다
	 * @param reqPage 요청 페이지 
	 * @param searchType 검색 타입
	 * @param keyword 검색어
	 * @param userNum 사용자번호
	 * @return 검색결과
	 */
	Map<String, Object> getSubjectList(String reqPage, String searchType, String keyword, long userNum);

	/**
	 * 단원의 마무리문제 전체 통과 여부 조회
	 * @param unitNum 단원 번호
	 * @param userNum 사용자 번호 
	 * @return 통과여부,총문제 수, 통과문제 수 등
	 */
	Map<String, Object> getUnitProgress(long unitNum, long userNum);

	/**
	 * 단원 내용 불러오기
	 * @param unitNum 단원 번호
	 * @return 단원 VO
	 */
	UnitVO getUnitContent(long unitNum);
	/**
	 * 단원 내용 불러오기(사용자의 진척도도 불러온다)
	 * @param unitNum 단원 번호
	 * @param userNum 사용자 번호
	 * @return 단원 VO
	 */
	UnitVO getUnitContent(long unitNum, long userNum);

	/**
	 * 단원 리스트 불러오기
	 * @param subjectNum 과목 번호
	 * @return 단원 VO의 리스트
	 */
	List<UnitVO> getUnitList(long subjectNum, long userNUm);

	/**
	 * 단원 완료여부를 체크한다.
	 * @param unitNum 단원 번호 
	 * @param userNum 사용자 번호
	 * @return 단완 완료시 완료번호를 리턴함(0이 아닌 수)
	 */
	long checkCompletion(long unitNum, long userNum);

	/**
	 * 마무리 문제 리스트를 가져온다.
	 * @param unitNum 단원 번호
	 * @return 마무리문제 VO 리스트 
	 */
	List<QuizVO> getQuizList(long unitNum);

	/**
	 * 마무리문제의 메타정보들을 가져온다.
	 * @param unitNum 검색할 단원
	 * @param userNum 사용자 번호
	 * @return 메타정보 리스트
	 */
	List<Map<String, Object>> getQuizMatadatas(long unitNum, long userNum);

	/**
	 * 다음 단원번호를 가져온다.
	 * @param unitNum 현재 단원번호
	 * @return 다음 단원번호
	 */
	long getNextUnitNum(long unitNum);
	 
	
}
