
package com.puercha.algo.board.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
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

import com.puercha.algo.board.vo.AttachmentVO;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;
import com.puercha.algo.common.RowCriteria;
import com.puercha.algo.board.dao.PostingDAO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class PostingDAOImplTest {
	private final static Logger logger = LoggerFactory.getLogger(PostingDAOImplTest.class);

	@Inject
	PostingDAO postingDAO;

	@Test
	@Named("카테고리 읽어오기")
	@Disabled
	public void getCategory() {
		List<BoardCategoryVO> list = postingDAO.getCategory();
		assertNotNull(list);
		logger.info(list.toString());
	}

	@Test
	@Named("게시글 작성 // 첨부 없음")
	@Disabled
	public void write() {
		BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
		BoardPostVO boardPostVO = new BoardPostVO();

		boardCategoryVO.setCategoryNum(1);
		boardCategoryVO.setName("test");

		boardPostVO.setCategory(boardCategoryVO);
		boardPostVO.setTitle("Junit테스트");
		boardPostVO.setUserNum(1);
		boardPostVO.setUserName("테스트");
		boardPostVO.setContent("테스트 본문");

		int cnt = postingDAO.insert(boardPostVO);

		assertEquals(1, cnt);
	}

	@Test
	@Named("파일첨부")
	@Disabled
	public void writeFile() {
		AttachmentVO attachmentVO = new AttachmentVO();
		BoardPostVO boardPostVO = new BoardPostVO();

		boardPostVO.setPostNum(1);
		byte[] file = new byte[1024];
		attachmentVO.setPostNum(boardPostVO.getPostNum());
		attachmentVO.setFname("file첨부");
		attachmentVO.setFsize("100");
		attachmentVO.setFtype("file 첨부 test");
		attachmentVO.setFdata(file);

		int cnt = postingDAO.insertFile(attachmentVO);
		assertEquals(1, cnt);
	}

	@Test
	@Named("게시글 수정")
//	@Disabled
	public void modify() {

		BoardPostVO boardPostVO = new BoardPostVO();
		BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
		boardPostVO = postingDAO.select("83");
		logger.info(boardPostVO.toString());

		boardCategoryVO.setCategoryNum(1);
		boardCategoryVO.setName("분류");
		boardPostVO.setCategory(boardCategoryVO);	
		boardPostVO.setTitle("수정테스트");
		boardPostVO.setContent("수정테스트");

		int cnt = postingDAO.update(boardPostVO);
		assertEquals(1, cnt);
		logger.info(boardPostVO.toString());

	}

	@Test
	@Named("게시글 삭제")
	@Disabled
	public void delete() {

		int cnt = postingDAO.delete("22");
		assertEquals(1, cnt);

	}

	@Test
	@Named("파일 삭제")
	@Disabled
	public void deleteFile() {
		int cnt = postingDAO.deleteFile("21");

		assertEquals(1, cnt);

	}

	@Test
	@Named("파일 삭제 전체")
	@Disabled
	public void deleteFiles() {
		int cnt = postingDAO.deleteFiles("1");

		assertEquals(2, cnt);
	}

	@Test
	@Named("게시글 보기")
	@Disabled
	public void view() {

		BoardPostVO boardPostVO = new BoardPostVO();
		boardPostVO = postingDAO.select("1");
		
		BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
		boardCategoryVO.setCategoryNum(1);
		boardCategoryVO.setName("카테고리");
		
		boardPostVO.setCategory(boardCategoryVO);
		
		
		
		
		logger.info("" + boardCategoryVO.getCategoryNum());
		assertEquals(1, boardPostVO.getPostNum());
		logger.info(boardPostVO.toString());

	}

	@Test
	@Named("조회수 증가")
	@Disabled
	public void hit() {

		BoardPostVO boardPostVO = new BoardPostVO();
		int cnt = postingDAO.updateHit("23");
		boardPostVO = postingDAO.select("23");

		logger.info("hit :" + boardPostVO.getHit());
		assertEquals(1, cnt);
	}

	@Test
	@Named("게시글목록")
	@Disabled
	public void list() {
		List<BoardPostVO> list = postingDAO.selectList();
		assertNotNull(list);
		logger.info(list.toString());

	}

	@Test
	@Named("게시글 목록(페이징 + 제목, 내용)")
	@Disabled
	public void list3() {

		int reqPage = 1;
		RowCriteria rowCriteria = new RowCriteria(reqPage);
		logger.info("" + rowCriteria.getStartRec());
		logger.info("" + rowCriteria.getEndRec());
		List<BoardPostVO> list = postingDAO.selectList(rowCriteria.getStartRec(), rowCriteria.getEndRec(), "T", "답글");
		logger.info("검색 결과" + list.size());
		for (BoardPostVO boardPostVO : list) {
			logger.info(boardPostVO.toString());
		}
	}

	@Test
	@Named("총 레코드 카운트")
	@Disabled
	public void totalRecordCount() {
		int cnt = postingDAO.countTotalRecord("T", "test");

		logger.info("총레코드수" + cnt);
	}

	@Test
	@Named("답글달기")
	@Disabled
	public void insertReply() {

		BoardPostVO boardPostVO = new BoardPostVO();
		BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
		boardPostVO = postingDAO.select("23");

		logger.info(boardPostVO.toString());

		boardCategoryVO.setCategoryNum(1);
		boardCategoryVO.setName("질문게시글");

		boardPostVO.setCategory(boardCategoryVO);
		boardPostVO.setTitle("답글테스트");
		boardPostVO.setUserNum(1);
		boardPostVO.setUserName("답글테스트");
		boardPostVO.setContent("답글테스트");

		int cnt = postingDAO.insertReply(boardPostVO);

	}

	@Test
	@Named("첨부파일 1개 조회")
	@Disabled
	public void selectFile() {
		AttachmentVO attachmentVO = new AttachmentVO();

		attachmentVO = postingDAO.selectFile("26");
		assertNotNull(attachmentVO);
		logger.info("selectFile :" + attachmentVO.toString());

	}

	@Test
	@Named("첨부파일 전체 조회")
	@Disabled
	public void selectFiles() {

		List<AttachmentVO> list = postingDAO.selectFiles("1");

		assertNotNull(list);
		logger.info("selectFiles :" + list.toString());

	}

	@Test
	@Named("답글 스텝 올려주기")
	@Disabled
	public void updateStep() {
		BoardPostVO boardPostVO = new BoardPostVO();

		boardPostVO = postingDAO.select("1");
		int cnt = postingDAO.updateStep(boardPostVO.getPostGroup(), boardPostVO.getPostStep());

		assertEquals(1, cnt);
	}

	@Test
	public void testLongList() {
		List<Long> longList = new ArrayList<Long>();
		longList.add((long) 1);
		longList.add((long) 7);
		longList.add((long) 5);
		logger.info("longList:"+longList);
	}
}
