package com.puercha.algo.learning.dao;


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
import com.puercha.algo.board.vo.BoardPostVO;
import com.puercha.algo.learning.vo.SubjectVO;

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
	
}
