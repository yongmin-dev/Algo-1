package com.puercha.algo.board.service;
/**
 * 게시판 기능 정의
 * @author Hyeonuk
 *
 */

import java.util.List;
import java.util.Map;

import com.puercha.algo.board.vo.AttachmentVO;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;
import com.puercha.algo.common.PageManager;

public interface BoardService {
	
	List<BoardCategoryVO> getCategory();
	
	//게시글 작성
	int insert(BoardPostVO boardVO);
	
	//게시글 수정
	int update(BoardPostVO boardPostVO);
	
	//게시글삭제
	int delete(String postNum);
	
	//첨부파일1건 삭제
	int deleteFile(String fid);
	
	//첨부파일전체삭제
	int deleteFiles(String postNum);
	
	//게시글보기
	Map<String, Object> select(String postNum);
	
	//게시글 목록보기
	//1) 전체
	List<BoardPostVO> selectList();
	
	//2) 검색어 없는 게시글 페이징
	List<BoardPostVO> selectList(int startRec, int endRec);
	
	//3) 검색어 있는 게시글 페이징
	List<BoardPostVO> selectList(String reqPage, String searchType, String keyword);
	
	//페이지 제어
	PageManager getPageManager(String reqPage, String searchType, String keyword);
	
	//게시글 답글 작성
	
	int insertReply(BoardPostVO boardPostVO);
	
	//첨부파일 조회
	AttachmentVO selectFile(String fid);
	
	

}
