package com.puercha.algo.board.dao;

import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.puercha.algo.board.vo.AttachmentVO;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;

@Repository
public class PostingDAOImplXML implements PostingDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(PostingDAOImplXML.class);
	
	@Inject
	SqlSessionTemplate sqlSession;
	
	//카테고리 읽어오기
	@Override
	public List<BoardCategoryVO> getCategory() {
		
		return sqlSession.selectList("mappers.PostingDAO-mapper.getCategory");
	}

	@Override
	public int insert(BoardPostVO boardPostVO) {
		return sqlSession.insert("mappers.PostingDAO-mapper.write",boardPostVO );
	}

	@Override
	public int fileInsert(AttachmentVO attachmentVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(BoardPostVO boardPostVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String postNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFile(String fid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFiles(String postNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoardPostVO select(String postNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AttachmentVO> selectFiles(String postNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateHit(String postNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BoardPostVO> selectList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardPostVO> selectList(int startRec, int endRec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardPostVO> selectList(int startRec, int endRec, String searchType, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countTotalRecord(String searchType, String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertReply(BoardPostVO boardPostVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AttachmentVO selectFile(String fid) {
		// TODO Auto-generated method stub
		return null;
	}

}
