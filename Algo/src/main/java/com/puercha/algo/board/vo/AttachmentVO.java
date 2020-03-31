package com.puercha.algo.board.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author Yongmin
 * 첨부파일VO
 * 
 */
@Data
public class AttachmentVO {

	//파일아이디
	private long fid;
	
	//게시글번호
	private long postNum;
	
	//파일명
	private String fname;
	
	//파일크기
	private String fsize;
	
	//파일유형
	private String ftype;
	
	//첨부파일
	private byte[] fdata;
	
	//작성일
	private Date createdAt;
	
	//수정일
	private Date updatedAt;
}
