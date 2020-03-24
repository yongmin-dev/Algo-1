package com.puercha.algo.board.vo;

import java.sql.Timestamp;

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
	// TIMESTAMP => import java.sql.Timestamp;
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
	private Timestamp created_at;
	
	//수정일
	private Timestamp updated_at;
	
	//본문내용
	private String content;
	
	//답글그룹
	private long commentGroup;
	
	//답변글 단계
	private long comment_step;
	
	//답변글의 들여쓰기
	private long indent;
	
	//부모댓글번호
	private long prnum;
	
	//부모댓글번호
	private long target_userNum;
	
	//답글대상이름
	private String Target_username;
	
}
