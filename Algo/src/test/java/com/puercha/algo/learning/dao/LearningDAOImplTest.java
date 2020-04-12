package com.puercha.algo.learning.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.puercha.algo.board.dao.PostingDAOImplTest;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;
import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class LearningDAOImplTest {
	private final static Logger logger = LoggerFactory.getLogger(PostingDAOImplTest.class);

	@Inject
	LearningDAO learningDAO;
	
	@Test
	@Named("과목목록")
	@Disabled
	public void list() {
		
		List<SubjectVO> list = learningDAO.selectAllSubjects(1, null, "test2");
				
		for(SubjectVO subjectList : list){
			logger.info(subjectList.toString());
		}

	}

	@Test
	@Disabled
	public void totalRecordCount() {
		logger.info("totalRecordCount()");
		int cnt = learningDAO.countTotalRecord("T", "test");
		logger.info("totalRecordCount"+cnt);

	}
	
	@Test
	@Disabled
	public void unitList() {
				
		List<UnitVO> unitList = learningDAO.selectAllUnits(1);
		for(UnitVO unit : unitList) {
			logger.info(unit.toString());
		}
	}


	@Test
	@Disabled
	public void unitContent() {
		UnitVO unitVO = new UnitVO();
//		unitVO = learningDAO.selectOneUnit(1);
		logger.info(unitVO.toString());
	}
	
	@Test
//	@Disabled
	public void quizList() {
		List<QuizVO> quizList = learningDAO.selectAllQuiz(1);
		for(QuizVO quiz : quizList) {
			logger.info(quiz.toString());
//			for(QuizVO answers :  )
		}
	}
	@Test
	@Disabled
	public void answerList() {
		List<QuizAnswerVO> answerList = learningDAO.selectAllAnswer(1);
		for(QuizAnswerVO answer : answerList) {
			logger.info(answer.toString());
		}
	}
	
	@Test
	public void testGetNextUnitNum() {
		
		logger.info("unitNum:"+learningDAO.getNextUnitNum(6));
	}
}
