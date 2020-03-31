package com.puercha.algo.board.dao;

import java.util.List;

import com.puercha.algo.board.vo.AttachmentVO;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;

/**
 * 게시글 DAO 정의
 * 
 * @author Yongmin
 *
 */
public interface PostingDAO {

	// 카테고리 읽어오기
	List<BoardCategoryVO> getCategory();

	// 게시글 작성
	int insert(BoardPostVO boardPostVO);

	int insertFile(AttachmentVO attachmentVO);

	// 게시글 수정
	int update(BoardPostVO boardPostVO);

	// 게시글삭제
	int delete(String postNum);

	// 첨부파일 1건 삭제
	int deleteFile(String fid);

	// 게시글 첨부파일 전체삭제
	int deleteFiles(String postNum);

	// 게시글 읽기
	BoardPostVO select(String postNum);

	// 첨부파일 전체 조회
	List<AttachmentVO> selectFiles(String postNum);

	// 첨부파일 하나 조회
	AttachmentVO selectFile(String fid);

	// 조회수 1 증가
	int updateHit(String postNum);

	// 게시글 목록
	// 1 전체
	List<BoardPostVO> selectList();

	// 2) 검색어 없는 게시글 페이징(구현안됨)**참조용
	List<BoardPostVO> selectList(int startRec, int endRec);

	// 3) 검색어 있는 게시글검색(제목, 내용, 작성자)
	List<BoardPostVO> selectList(int startRec, int endRec, String searchType, String keyword);

	// 총 레코드 수
	int countTotalRecord(String searchType, String keyword);

	// 게시글답글작성
	int insertReply(BoardPostVO boardPostVO);

	// 답글 스텝 올려주기
	int updateStep(long postGroup, long postStep);

}
