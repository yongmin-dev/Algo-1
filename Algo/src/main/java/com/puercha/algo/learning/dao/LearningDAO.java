package com.puercha.algo.learning.dao;

import java.util.List;
import java.util.Map;

import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizResultVO;
import com.puercha.algo.learning.vo.QuizVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitCompletionVO;
import com.puercha.algo.learning.vo.UnitVO;

/**
 * 이론학습 데이터 DAO 정의
 * @author Hyeonuk
 *
 */
public interface LearningDAO {
	/* Create */
	
	/**
	 *  단원 생성
	 * @param unit 새로운 데이터의 VO
	 * @return 성공 시 1
	 */
	int insertUnit(UnitVO unit);
	
	//과목 생성
	int insertSubject(SubjectVO subject);
	
	/**
	 * 마무리문제 생성
	 * @param quiz 생성할 데이터 담긴 VO
	 * @return 성공 시 1
	 */
	int insertQuiz(QuizVO quiz);
	
	/**
	 * 마무리문제 답안 생성
	 * @param quizAnswer 생성할 데이터가 담긴 VO
	 * @return 성공 시 1
	 */
	int insertAnswer(QuizAnswerVO quizAnswer);
	
	//진척도
	int insertUnitCompletion(UnitCompletionVO unitCompletion);
	
	/*Select*/
	
	/**
	 * 과목 한개 조회
	 * @param subjectNum 과목번호
	 * @return 과목 VO
	 */
	SubjectVO selectOneSubject(long subjectNum);
	
	
	/**
	 * 단원리스트를 가져옴(내용제외:content)
	 * @param subjectNum 과목 번호
	 * @return 단원 VO의 리스트
	 */
	List<UnitVO> selectAllUnits(long subjectNum);
	//단원 열람, 단원내용관리화면
//	UnitVO selectOneUnit(long unitNum);
	UnitVO selectOneUnit(long unitNum, long userNum);

	//과목리스트, 과목 조회(검색)
	List<SubjectVO> selectAllSubjects(long pageNum,  String searchType,String keyword);
	List<SubjectVO> selectAllSubjects(long pageNum,  String searchType,String keyword,long userNum);
	List<SubjectVO> selectAllSubjects(long startRowNum, long endRowNum,  String searchType,String keyword,long userNum);
	
	//과목리스트,자기가 작성한 과목 조회(관리)	
	List<SubjectVO> selectAllUserSubjects(long userNum);	
	//과목리스트, 자기가 작성한 과목 조회(관리)
	List<SubjectVO> selectAllUserSubjects(long userNum, long pageNum);
	
	//마무리문제풀기화면, 마무리문제 리스트
	List<QuizVO> selectAllQuiz(long unitNum);

	//마무리문제 답안 리스트
	List<QuizAnswerVO> selectAllAnswer(long quizNum);
	
	//진척도 조회
	UnitCompletionVO selectUnitCompletion(long userNum);
	
	/**
	 * 내용을 제외한 unit의 데이터들의 리스트를 가져옴
	 * @param subjectNum 과목번호
	 * @param userNum 사용자 번호
	 * @return unit vo의 리스트 객체
	 */
	List<UnitVO> selectAllUnitMetadatas(long subjectNum, long userNum);
	
	
	/**
	 * 한 개의 마무리문제를 열람
	 * @param quizNum 열람할 마무리 문제 번호
	 * @return Quiz VO
	 */
	QuizVO selectOneQuiz(long quizNum);
	
	/**
	 * 답안 하나의 정보를 가져온다.
	 * @param answerNum 답안번호
	 * @return 답안의 vo
	 */
	QuizAnswerVO selectOneAnswer(long answerNum);
	
	/* update */
	
	/**
	 * 단원 수정, 단원내용 수정 완료
	 * @param unit 단원 VO
	 * @return 성공 시 1
	 */
	int updateUnit(UnitVO unit);
	
	/** 
	 * 과목수정
	 * @param subject 수정된 데이터를 가진 VO
	 * @return 성공시 1
	 */
	int updateSubject(SubjectVO subject);
	
	/**
	 * 마무리문제 수정
	 * @param quiz 수정된 데이터를 가진 VO
	 * @return 성공 시 1
	 */
	int updateQuiz(QuizVO quiz);
	
	/**
	 * 마무리문제 답안 수정
	 * @param quizAnswer 수정된 데이터를 가진 VO
	 * @return 성공시 1
	 */
	int updateAnswer(QuizAnswerVO quizAnswer);
	
	/* delete */
	
	//unit 삭제
	int deleteUnit(long unitNum);
	
	//과목 삭제
	int deleteSubject(long subjectNum);
	
	//마무리문제 삭제
	int deleteQuiz(long quizNum);
	
	//마무리문제 답안 삭제
	int deleteAnswer(long answerNum);
	
	//총 레코드 수
	int countTotalRecord(String searchType, String keyword);

	/**
	 * 퀴즈 결과를 생성함
	 * @param quizResult 퀴즈 결과 VO
	 * @return 성공시 1
	 */
	int insertQuisResult(QuizResultVO quizResult);

	/**
	 * 단원의 마무리문제 전체 통과 여부 조회
	 * @param unitNum 단원 번호
	 * @param userNum 사용자 번호 
	 * @return 통과여부,총문제 수, 통과문제 수 등
	 */
	Map<String, Object> selectUnitProgress(long unitNum, long userNum);

	/**
	 * 각 마무리문제의 통과여부 조회
	 * @param unitNum 단원 번호
	 * @param userNum 사용자 번호
	 * @return Map단위의 리스트객체(row는 통과여부, 퀴즈번호, 통과결과 개수)
	 */
	List<Map<String,Object>> selectAllPassFailOfQuiz(long unitNum, long userNum);

	
	/**
	 * 다음 unitNum을 가져온다.
	 * @param unitNum 현재 단원번호
	 * @return 다음 단원번호
	 */
	long getNextUnitNum(long unitNum);
	

	

	
}
