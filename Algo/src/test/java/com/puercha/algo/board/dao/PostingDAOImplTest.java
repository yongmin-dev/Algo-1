package com.puercha.algo.board.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.puercha.algo.board.vo.BoardPostVO;
import com.puercha.algo.common.RowCriteria;
import com.puercha.algo.board.dao.PostingDAO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations ="file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class PostingDAOImplTest {
	private final static Logger logger = LoggerFactory.getLogger(PostingDAOImplTest.class);
	
	@Inject
	PostingDAO postingDAO;
	
	//게시글목록
	@Test
	@Named("게시글목록")
	@Disabled
	public void list() {
		List<BoardPostVO> list = postingDAO.selectList();
		assertNotNull(list);
		logger.info(list.toString());
		
	}
	
	//게시글 목록(검색어, 페이지)
	@Test
	@Named("게시글 목록(페이징)")
	@Disabled
	public void list3() {
		
		int reqPage = 1;
		RowCriteria rowCriteria = new RowCriteria(reqPage);
		logger.info(""+rowCriteria.getStartRec());
		logger.info(""+rowCriteria.getEndRec());
		List<BoardPostVO> list = postingDAO.selectList(rowCriteria.getStartRec(), rowCriteria.getEndRec(), "T", "답글");
		logger.info("검색 결과" + list.size());
		for(BoardPostVO boardPostVO : list) {
			logger.info(boardPostVO.toString());
		}
	}
	
	//총 레코드수 카운트
	@Test
	@Named("총 레코드 카운트")
	@Disabled
	public void totalRecordCount() {
		int cnt = postingDAO.countTotalRecord("T", "test");
		
		logger.info("총레코드수" + cnt);
	}
	
	@Test
	@Named("게시글 보기")
	public void view() {
		
		BoardPostVO boardPostVO = new BoardPostVO();
		boardPostVO = postingDAO.select("1");
		logger.info(boardPostVO.toString());
		
	}
	
	
	
	

}
