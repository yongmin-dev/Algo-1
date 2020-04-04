package com.puercha.algo.learning.dao;

import java.util.List;

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
	
	//단원 생성
	int insertUnit(UnitVO unit);
	
	//과목 생성
	int insertSubject(SubjectVO subject);
	
	//마무리문제 생성
	int insertQuiz(QuizVO quiz);
	
	//마무리문제 답안 생성
	int insertAnswer(QuizAnswerVO quizAnswer);
	
	//진척도
	int insertUnitCompletion(UnitCompletionVO unitCompletion);
	
	/*Select*/
	
	
	/**
	 * 단원리스트를 가져옴(내용제외:content)
	 * @param subjectNum 과목 번호
	 * @return 단원 VO의 리스트
	 */
	List<UnitVO> selectAllUnits(long subjectNum);
	//단원 열람, 단원내용관리화면
	UnitVO selectOneUnit(long unitNum);

	
	
		//과목리스트, 과목 조회(검색)
	List<SubjectVO> selectAllSubjects(long pageNum,  String searchType,String keyword);
	
	//과목리스트,자기가 작성한 과목 조회(관리)	
	List<SubjectVO> selectAllSubjects(long userNum);	
	//과목리스트, 자기가 작성한 과목 조회(관리)
	List<SubjectVO> selectAllSubjects(long userNum, long pageNum);
	
	//마무리문제풀기화면, 마무리문제 리스트
	List<QuizVO> selectAllQuiz(long unitNum);

	//마무리문제 답안 리스트
	List<QuizAnswerVO> selectAllAnswer(long quizNum);
	
	//진척도 조회
	UnitCompletionVO selectUnitCompletion(long userNum);
	
	/* update */
	
	//단원 수정, 단원내용 수정 완료
	int updateUnit(UnitVO unit);
	
	//과목수정
	int updateSubject(SubjectVO subject);
	
	//마무리문제 수정
	int updateQuiz(QuizVO quiz);
	
	//마무리문제 답안 수정
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
	
	
}
