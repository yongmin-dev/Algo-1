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

	@Override
	public int insertUnit(UnitVO unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSubject(SubjectVO subject) {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public UnitVO selectOneUnit(long unitNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("UnitNum", unitNum);

		return sqlSession.selectOne("mappers.learningDAO-mapper.selectOneUnit", Long.valueOf(unitNum));
	}

	@Override
	public List<UnitVO> selectAllUnits(long subjectNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectNum", subjectNum);
		return sqlSession.selectList("mappers.learningDAO-mapper.selectUnitList", map);
	}
	@Override
	public List<QuizVO> selectAllQuiz(long unitNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitNum", unitNum);
		return sqlSession.selectList("mappers.learningDAO-mapper.selectQuizList", map);
	}
	
	@Override
	public List<SubjectVO> selectAllSubjects(long userNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubjectVO> selectAllSubjects(long userNum, long pageNum) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<QuizAnswerVO> selectAllAnswer(long quizNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitCompletionVO selectUnitCompletion(long userNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateUnit(UnitVO unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSubject(SubjectVO subject) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return 0;
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
