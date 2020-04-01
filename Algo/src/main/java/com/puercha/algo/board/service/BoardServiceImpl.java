package com.puercha.algo.board.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.puercha.algo.board.dao.PostingDAO;
import com.puercha.algo.board.vo.AttachmentVO;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;
import com.puercha.algo.common.FindCriteria;
import com.puercha.algo.common.PageManager;
import com.puercha.algo.common.RowCriteria;

@Service
public class BoardServiceImpl implements BoardService {

	public static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	@Inject
	PostingDAO postingDAO;

	// 게시글 읽어오기
	@Override
	public List<BoardCategoryVO> getCategory() {
		return postingDAO.getCategory();
	}

	@Transactional
	@Override
	public int write(BoardPostVO boardPostVO) {

		// 1) 게시글 작성
		int cnt = postingDAO.insert(boardPostVO);

		// 2) 첨부파일 있을 경우
		logger.info("첨부파일: " + boardPostVO.getFiles().size());

		if (boardPostVO.getFiles() != null && boardPostVO.getFiles().size() > 0) {
			writeFile(boardPostVO.getFiles(), boardPostVO.getPostNum());
		}
		return cnt;
	}

	// 첨부파일 저장하기
	private void writeFile(List<MultipartFile> files, long postNum) {
		for (MultipartFile file : files) {
			try {
				logger.info("파일 첨부 : " + file.getOriginalFilename());

				AttachmentVO attachmentVO = new AttachmentVO();
				// 게시글 번호
				attachmentVO.setPostNum(postNum);
				// 첨부파일 이름
				attachmentVO.setFname(file.getOriginalFilename());
				attachmentVO.setFsize(""+file.getSize());
				attachmentVO.setFtype(file.getContentType());
				attachmentVO.setFdata(file.getBytes());

				if (file.getSize() > 0) {
					postingDAO.insertFile(attachmentVO);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 게시글 수정
	@Transactional
	@Override
	public int modify(BoardPostVO boardPostVO) {
		// 1) 게시글 수정
		int cnt = postingDAO.update(boardPostVO);
		// 2) 첨부파일 추가
		if (boardPostVO.getFiles() != null && boardPostVO.getFiles().size() > 0) {
			writeFile(boardPostVO.getFiles(), boardPostVO.getPostNum());
		}
		return cnt;
	}

	// 게시글 삭제(파일 삭제 병행)
	@Transactional
	@Override
	public int delete(String postNum) {
		int cnt = 0;
		postingDAO.deleteFiles(postNum);
		cnt = postingDAO.delete(postNum);
		return cnt;
	}

	// 파일 한개 삭제
	@Transactional
	@Override
	public int deleteFile(String fid) {

		int cnt = 0;
		cnt = postingDAO.deleteFile(fid);
		return cnt;
	}

	// 파일 전체 삭제
	@Transactional
	@Override
	public int deleteFiles(String postNum) {

		int cnt = 0;
		cnt = postingDAO.deleteFiles(postNum);

		return cnt;
	}

	
	//게시글 읽기
	@Transactional
	@Override
	public Map<String, Object> view(String postNum) {
		// 1) 게시글 가져오기
		BoardPostVO boardPostVO = postingDAO.select(postNum);

		List<AttachmentVO> attachmentVO = postingDAO.selectFiles(postNum);

		postingDAO.updateHit(postNum);

		Map<String, Object> map = new HashMap<>();
		map.put("boardPostVO", boardPostVO);
		if (attachmentVO != null && attachmentVO.size() > 0) {
			map.put("attachmentVO", attachmentVO);
		}

		return map;
	}

	// 게시글 목록
	// 1) 전체
	@Override
	public List<BoardPostVO> list() {
		return postingDAO.selectList();
	}

	@Override
	public List<BoardPostVO> list(int startRec, int endRec) {
		return null;
	}

	@Override
	public List<BoardPostVO> list(String reqPage, String searchType, String keyword) {

		logger.info("list param :" + reqPage + " "+ searchType);
		int l_reqPage = 0;
		
		//요청 페이지 정보가 없으면 1로 초기화
		if(reqPage == null || reqPage.trim().isEmpty()) {
			l_reqPage = 1;
		}else {
			l_reqPage = Integer.parseInt(reqPage);
		}
		RowCriteria rowCriteria = new RowCriteria(l_reqPage);
		logger.info("RowCriteria"+ rowCriteria.getStartRec()+" "+ rowCriteria.getEndRec() +" " + searchType+ " " + keyword);
		return postingDAO.selectList(rowCriteria.getStartRec(), rowCriteria.getEndRec(), searchType, keyword);
	}
	
	//페이지 제어
	@Override
	public PageManager getPageManager(String reqPage, String searchType, String keyword) {
		
		PageManager pm = null;
		FindCriteria fc = null;
		
		int totalRec = 0;
		int l_reqPage = 0;
		
		// 요청페이지 정보가 없으면 1로 초기화
		if(reqPage == null || reqPage.trim().isEmpty()) {
			l_reqPage = 1;
		}else {
			l_reqPage = Integer.parseInt(reqPage);
		}
		
		totalRec = postingDAO.countTotalRecord(searchType, keyword);
		
		pm = new PageManager(fc, totalRec);
		fc = new FindCriteria(l_reqPage, searchType, keyword);
		
		return pm;
	}
	//답글 작성
	@Transactional
	@Override
	public int reply(BoardPostVO boardPostVO) {
		//1) 게시글 답글 작성
		int cnt = postingDAO.insertReply(boardPostVO);
		
		if(boardPostVO.getFiles()!= null && boardPostVO.getFiles().size() > 0) {
			writeFile(boardPostVO.getFiles(), boardPostVO.getPostNum());
		}
		return cnt;
	}

	//첨부파일 보기
	@Override
	public AttachmentVO viewFile(String fid) {
		return postingDAO.selectFile(fid);
	}

}
