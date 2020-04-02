package com.puercha.algo.learning.dao;

import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizResultVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;

/**
 * 이론학습 데이터 DAO 정의
 * @author Hyeonuk
 *
 */
public interface LearningDAO {
	/* Create */
	
	
	
	
	//과목 조회
	int selectAllSubjects(SubjectVO subjectVO);
	
	//단원 열람
	int selectOneUnit(UnitVO unitVO);
	
	//마무리문제풀기화면
	int selectAllQuiz(QuizResultVO quizResultVO);
	
	//문제정답확인
	int selectAllQuizAnswers(QuizAnswerVO quizAnswerVO);
	
	
	
}
