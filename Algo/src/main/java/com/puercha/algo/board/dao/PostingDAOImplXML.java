package com.puercha.algo.board.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// 카테고리 읽어오기
	@Override
	public List<BoardCategoryVO> getCategory() {

		return sqlSession.selectList("mappers.PostingDAO-mapper.getCategory");
	}

	// 글쓰기
	@Override
	public int insert(BoardPostVO boardPostVO) {
		return sqlSession.insert("mappers.PostingDAO-mapper.write", boardPostVO);
	}

	// 파일 첨부
	@Override
	public int insertFile(AttachmentVO attachmentVO) {
		return sqlSession.insert("mappers.PostingDAO-mapper.fileInsert", attachmentVO);
	}

	// 글수정
	@Override
	public int update(BoardPostVO boardPostVO) {
		return sqlSession.update("mappers.PostingDAO-mapper.update", boardPostVO);
	}

	// 글삭제
	@Override
	public int delete(String postNum) {
		return sqlSession.delete("mappers.PostingDAO-mapper.delete", Long.valueOf(postNum));
	}

	// 첨부파일삭제
	@Override
	public int deleteFile(String fid) {
		return sqlSession.delete("mappers.PostingDAO-mapper.deleteFile", Long.valueOf(fid));
	}

	// 첨부전체파일삭제
	@Override
	public int deleteFiles(String postNum) {
		return sqlSession.delete("mappers.PostingDAO-mapper.deleteFiles", Long.valueOf(postNum));
	}

	// 게시글보기
	@Override
	public BoardPostVO select(String postNum) {
		return sqlSession.selectOne("mappers.PostingDAO-mapper.select", Long.valueOf(postNum));
	}

	// 파일 전체 보기
	@Override
	public List<AttachmentVO> selectFiles(String postNum) {
		logger.info("postingDAOImpl SelectFiles" + postNum);
		return sqlSession.selectList("mappers.PostingDAO-mapper.selectFile", Long.valueOf(postNum));
	}

	// 조회수 증가
	@Override
	public int updateHit(String postNum) {
		return sqlSession.update("mappers.PostingDAO-mapper.updateHit", Long.valueOf(postNum));
	}

	// 게시글목록
	// 전체보기
	@Override
	public List<BoardPostVO> selectList() {
		return sqlSession.selectList("mappers.PostingDAO-mapper.selectList");
	}

	// 검색어 없는 게시글 페이징??
	@Override
	public List<BoardPostVO> selectList(int startRec, int endRec) {
		Map<String, Object> map = new HashMap<>();
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		return sqlSession.selectList("mappers.PostingDAO-mapper.selectList2", map);
	}

	// 검색어 있는 게시글 검색
	@Override
	public List<BoardPostVO> selectList(int startRec, int endRec, String searchType, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		map.put("searchType", searchType);
		if (keyword != null) {
			map.put("keyword", Arrays.asList(keyword.split("\\s+")));
		}

		return sqlSession.selectList("mappers.PostingDAO-mapper.selectList3", map);
	}

	@Override
	public int countTotalRecord(String searchType, String keyword) {
		logger.info("keyword:" + keyword);
		Map<String, Object> map = new HashMap<>();
		map.put("searchType", searchType);

		if (keyword != null) {
			map.put("list", Arrays.asList(keyword.split("\\s+")));

		}

		logger.info("keyword2:" + map.get("list"));

		return sqlSession.selectOne("mappers.PostingDAO-mapper.countTotalRecord", map);
	}

	// 게시글 답글 작성
	@Override
	public int insertReply(BoardPostVO boardPostVO) {
		// 1) 이전 답글 step 업데이트
		updateStep(boardPostVO.getPostGroup(), boardPostVO.getPostStep());

		return sqlSession.insert("mappers.PostingDAO-mapper.insertReply", boardPostVO);
	}

	// 이전 답글 step 업데이트 메소드
	public int updateStep(long postGroup, long postStep) {
		Map<String, Object> map = new HashMap();
		map.put("postGroup", postGroup);
		map.put("postStep", postStep);
		return sqlSession.update("mappers.PostingDAO-mapper.updateStep", map);

	}

	// 첨부파일 조회
	@Override
	public AttachmentVO selectFile(String fid) {
		return sqlSession.selectOne("mappers.PostingDAO-mapper.selectFile", Long.valueOf(fid));
	}

}
