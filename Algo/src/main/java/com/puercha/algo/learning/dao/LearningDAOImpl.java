package com.puercha.algo.learning.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitCompletionVO;
import com.puercha.algo.learning.vo.UnitVO;

@Repository
public class LearningDAOImpl implements LearningDAO {

	private static final Logger logger = LoggerFactory.getLogger(LearningDAOImpl.class);
	
	@Inject
	SqlSessionTemplate sqlSession;

	// 과목리스트, 과목 조회(검색)
	@Override
	public List<SubjectVO> selectAllSubjects(long pageNum, String searchType, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNum", pageNum);
		map.put("searchType", searchType);
		map.put("keywords", keyword);

		if (keyword != null) {
			map.put("keywords", Arrays.asList(keyword.split("\\s+")));
		}
		return sqlSession.selectList("mappers.learningDAO-mapper.selectSubjectList", map);

	}

	
	/**
	 *  단원 생성
	 * @param unit 새로운 데이터의 VO
	 * @return 성공 시 1
	 */
	@Override
	public int insertUnit(UnitVO unit) {
		logger.info("insertSubject(SubjectVO subject)");
		return sqlSession.insert("mappers.learningDAO-mapper.insertUnit",unit);
	}

	//과목 생성
	@Override
	public int insertSubject(SubjectVO subject) {
		logger.info("insertSubject(SubjectVO subject)");
		return sqlSession.insert("mappers.learningDAO-mapper.insertSubject",subject);
	}

	@Override
	public int insertQuiz(QuizVO quiz) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertAnswer(QuizAnswerVO quizAnswer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertUnitCompletion(UnitCompletionVO unitCompletion) {
		// TODO Auto-generated method stub
		return 0;
	}
	/* Read */
	@Override
	public UnitVO selectOneUnit(long unitNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitNum", unitNum);
		return sqlSession.selectOne("mappers.learningDAO-mapper.selectOneUnit", map);
	}

	@Override
	public List<UnitVO> selectAllUnits(long subjectNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectNum", subjectNum);
		return sqlSession.selectList("mappers.learningDAO-mapper.selectUnitList", map);
	}
	/**
	 * 내용을 제외한 unit의 데이터들의 리스트를 가져옴
	 * @param subjectNum 과목번호
	 * @return unit vo의 리스트 객체
	 */
	@Override
	public List<UnitVO> selectAllUnitMetadatas(long subjectNum){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectNum", subjectNum);
		return sqlSession.selectList("mappers.learningDAO-mapper.selectAllUnitMetadatas", map);
	}
	
	@Override
	public List<QuizVO> selectAllQuiz(long unitNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitNum", unitNum);
		return sqlSession.selectList("mappers.learningDAO-mapper.selectQuizList", map);
	}
	
	@Override
	public List<QuizAnswerVO> selectAllAnswer(long quizNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("quizNum",  quizNum);
		return sqlSession.selectList("mappers.learningDAO-mapper.selectAnswerList", map);
	}
	
	@Override
	public List<SubjectVO> selectAllUserSubjects(long userNum) {
		logger.info("selectAllSubjects(long userNum)");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userNum", userNum);
		return sqlSession.selectList("mappers.learningDAO-mapper.selectAllUserSubjects",params);
	}

	@Override
	public List<SubjectVO> selectAllUserSubjects(long userNum, long pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 과목 한개 조회
	 * @param subjectNum 과목번호
	 * @return 과목 VO
	 */
	@Override
	public SubjectVO selectOneSubject(long subjectNum) {
		logger.info("selectOneSubject(long subjectNum)");
		return sqlSession.selectOne("mappers.learningDAO-mapper.selectOneSubject",subjectNum);
	}



	@Override
	public UnitCompletionVO selectUnitCompletion(long userNum) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 한 개의 마무리문제를 열람
	 * @param quizNum 열람할 마무리 문제 번호
	 * @return Quiz VO
	 */
	@Override
	public QuizVO selectOneQuiz(long quizNum) {
		logger.info("selectOneQuiz(long quizNum)");
		return sqlSession.selectOne("mappers.learningDAO-mapper.selectOneQuiz");
	}


	/* Update */
	/**
	 * 단원 수정, 단원내용 수정 완료
	 * @param unit 단원 VO
	 * @return 성공 시 1
	 */
	@Override
	public int updateUnit(UnitVO unit) {
		logger.debug("updateUnit(UnitVO unit)");
		return sqlSession.update("mappers.learningDAO-mapper.updateUnit",unit);
	}

	/** 
	 * 과목수정
	 * @param subject 수정된 데이터를 가진 VO
	 * @return 성공시 1
	 */
	@Override
	public int updateSubject(SubjectVO subject) {
		logger.debug("updateSubject(SubjectVO subject)");
		return sqlSession.update("mappers.learningDAO-mapper.updateSubject",subject);
	}

	@Override
	public int updateQuiz(QuizVO quiz) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateAnswer(QuizAnswerVO quizAnswer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUnit(long unitNum) {
		logger.info("deleteUnit(long unitNum)");
		return sqlSession.delete("mappers.learningDAO-mapper.deleteUnit",unitNum);
	}

	@Override
	public int deleteSubject(long subjectNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteQuiz(long quizNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAnswer(long answerNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 총 리코드 수
	@Override
	public int countTotalRecord(String searchType, String keyword) {
		logger.info("countTotalRecord(String searchType, String keyword)");
		String logMsg = String.format("%s : %s, %s: %s", "searchType",searchType, "keyword", keyword);
		logger.info(logMsg);
		Map<String, Object> map = new HashMap<>();
		map.put("searchType", searchType);
		

		if (keyword != null) {
			map.put("keywords", Arrays.asList(keyword.split("\\s+")));

		}
		logger.info("keywords value" + map.get("keywords"));
		return sqlSession.selectOne("mappers.learningDAO-mapper.countTotalRecord", map);

	}

}
