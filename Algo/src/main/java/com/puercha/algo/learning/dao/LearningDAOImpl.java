package com.puercha.algo.learning.dao;

import java.util.Arrays;
import java.util.Comparator;
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

	/**
	 * 마무리문제 생성
	 * @param quiz 생성할 데이터 담긴 VO
	 * @return 성공 시 1
	 */
	@Override	
	public int insertQuiz(QuizVO quiz) {
		logger.info("insertQuiz(QuizVO quiz)");
		return sqlSession.insert("mappers.learningDAO-mapper.insertQuiz",quiz);
	}

	/**
	 * 마무리문제 답안 생성
	 * @param quizAnswer 생성할 데이터가 담긴 VO
	 * @return 성공 시 1
	 */
	@Override
	public int insertAnswer(QuizAnswerVO quizAnswer) {
		logger.info("insertAnswer(QuizAnswerVO quizAnswer)");
		return sqlSession.insert("mappers.learningDAO-mapper.insertAnswer",quizAnswer);
	}

	@Override
	public int insertUnitCompletion(UnitCompletionVO unitCompletion) {
		// TODO Auto-generated method stub
		return 0;
	}
	/* Read */
	@Override
	public UnitVO selectOneUnit(long unitNum) {
		logger.info("selectOneUnit(long unitNum)");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitNum", unitNum);
		return sqlSession.selectOne("mappers.learningDAO-mapper.selectOneUnit", map);
	}

	@Override
	public List<UnitVO> selectAllUnits(long subjectNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectNum", subjectNum);
		List<UnitVO> unitList = sqlSession.selectList("mappers.learningDAO-mapper.selectUnitList", map);
		unitList.sort( new Comparator<UnitVO>() {

			@Override
			public int compare(UnitVO o1, UnitVO o2) {
				logger.info("comapre(UnitVO o1, UnitVO o2)");
				String o1Depth = o1.getChapterDepth().substring(0, o1.getChapterDepth().length()-1);
				String o2Depth = o2.getChapterDepth().substring(0, o2.getChapterDepth().length()-1);
				String[] o1Tokens = o1Depth.split("\\.");
				String[] o2Tokens = o2Depth.split("\\.");
				logger.info("o1Tokens :"+Arrays.toString(o1Tokens) );
				logger.info("o2Tokens :"+Arrays.toString(o2Tokens) );
				int commonIdx = 0;
				int end = Math.min(o1Tokens.length,o2Tokens.length);
				
				for(commonIdx =0;commonIdx<end;commonIdx++ ) {
					int o1Num = Integer.parseInt(o1Tokens[commonIdx]);
					int o2Num = Integer.parseInt(o2Tokens[commonIdx]);
					logger.info("o1Num: "+o1Num+", o2Num:"+o2Num);
					if(o1Num<o2Num)
						return -1;
					else if(o1Num>o2Num) {
						return 1;
					}							
				}
				// 지금까지 똑같은 상황 길이가 짧은 쪽이 더 빠름
				if(o1Tokens.length<o2Tokens.length) {
					return -1;
				}else {
					return 1;					
				}
			}			
		});
		return unitList;
	}
	/**
	 * 내용을 제외한 unit의 데이터들의 리스트를 가져옴
	 * @param subjectNum 과목번호
	 * @return unit vo의 리스트 객체
	 */
	@Override
	public List<UnitVO> selectAllUnitMetadatas(long subjectNum){
		logger.info("selectAllUnitMetadatas(long subjectNum)");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectNum", subjectNum);
		List<UnitVO> unitList = sqlSession.selectList("mappers.learningDAO-mapper.selectAllUnitMetadatas", map);
		unitList.sort( new Comparator<UnitVO>() {

			@Override
			public int compare(UnitVO o1, UnitVO o2) {
				logger.info("comapre(UnitVO o1, UnitVO o2)");
				String o1Depth = o1.getChapterDepth().substring(0, o1.getChapterDepth().length()-1);
				String o2Depth = o2.getChapterDepth().substring(0, o2.getChapterDepth().length()-1);
				String[] o1Tokens = o1Depth.split("\\.");
				String[] o2Tokens = o2Depth.split("\\.");
				logger.info("o1Tokens :"+Arrays.toString(o1Tokens) );
				logger.info("o2Tokens :"+Arrays.toString(o2Tokens) );
				int commonIdx = 0;
				int end = Math.min(o1Tokens.length,o2Tokens.length);
				
				for(commonIdx =0;commonIdx<end;commonIdx++ ) {
					int o1Num = Integer.parseInt(o1Tokens[commonIdx]);
					int o2Num = Integer.parseInt(o2Tokens[commonIdx]);
					logger.info("o1Num: "+o1Num+", o2Num:"+o2Num);
					if(o1Num<o2Num)
						return -1;
					else if(o1Num>o2Num) {
						return 1;
					}							
				}
				// 지금까지 똑같은 상황 길이가 짧은 쪽이 더 빠름
				if(o1Tokens.length<o2Tokens.length) {
					return -1;
				}else {
					return 1;					
				}
			}			
		});
		logger.info("unitList:"+unitList);
		return unitList;
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
		return sqlSession.selectOne("mappers.learningDAO-mapper.selectOneQuiz",quizNum);
	}
	
	/**
	 * 답안 하나의 정보를 가져온다.
	 * @param answerNum 답안번호
	 * @return 답안의 vo
	 */
	@Override
	public QuizAnswerVO selectOneAnswer(long answerNum) {
		logger.info("selectOneAnswer(long answerNum)");
		return sqlSession.selectOne("mappers.learningDAO-mapper.selectOneAnswer", answerNum);
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

	/**
	 * 마무리문제 수정
	 * @param quiz 수정된 데이터를 가진 VO
	 * @return 성공 시 1
	 */
	@Override
	public int updateQuiz(QuizVO quiz) {
		logger.info("updateQuiz(QuizVO quiz)");
		return sqlSession.update("mappers.learningDAO-mapper.updateQuiz",quiz);
	}

	/**
	 * 마무리문제 답안 수정
	 * @param quizAnswer 수정된 데이터를 가진 VO
	 * @return 성공시 1
	 */
	@Override
	public int updateAnswer(QuizAnswerVO quizAnswer) {
		logger.info("updateAnswer(QuizAnswerVO quizAnswer)");
		return sqlSession.update("mappers.learningDAO-mapper.updateAnswer",quizAnswer);
	}

	@Override
	public int deleteUnit(long unitNum) {
		logger.info("deleteUnit(long unitNum)");
		return sqlSession.delete("mappers.learningDAO-mapper.deleteUnit",unitNum);
	}

	@Override
	public int deleteSubject(long subjectNum) {
		logger.info("deleteUnit(long unitNum)");
		return sqlSession.delete("mappers.learningDAO-mapper.deleteSubject",subjectNum);
	}

	@Override
	public int deleteQuiz(long quizNum) {
		logger.info("deleteQuiz(long quizNum)");
		return sqlSession.delete("mappers.learningDAO-mapper.deleteQuiz",quizNum);
	}

	@Override
	public int deleteAnswer(long answerNum) {
		logger.info("deleteAnswer(long answerNum)");
		return sqlSession.delete("mappers.learningDAO-mapper.deleteAnswer",answerNum);
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
