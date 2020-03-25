package com.puercha.algo.board.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author Yongmin
 * 댓글 VO
 * 
 */
@Data
public class CommentVO {

	// =====================================
	// NUMBER => long
	// VARCHAR2 => String
	// CHAR => String
	// DATE => import java.util.Date
	// Date => import java.util.Date;
	// =====================================
	
	//댓글번호
	private long commentNum;
	
	//게시글 번호
	private long postNum;
	
	//작성자ID
	private	String userNum;
	
	//작성자이름(별칭)
	private	String username;
	
	//작성일
	private Date createdAt;
	
	//수정일
	private Date updatedAt;
	
	//본문내용
	private String content;
	
	//답글그룹
	private long commentGroup;
	
	//답변글 단계
	private long commentStep;
	
	//답변글의 들여쓰기
	private long indent;
	
	//부모댓글번호
	private long prnum;
	
	//부모댓글번호
	private long targetUserNum;
	
	//답글대상이름
	private String TargetUsername;
	
}
