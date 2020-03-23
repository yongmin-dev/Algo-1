package com.puretea.algo.rboard.vo;

import java.sql.Timestamp;

@Data
public class RboardVO {

	// =====================================
	// NUMBER => long
	// VARCHAR2 => String
	// CHAR => String
	// DATE => import java.util.Date
	// TIMESTAMP => import java.sql.Timestamp;
	// =====================================
	
	//댓글번호
	private long comment_num;
	
	//게시글 번호
	private long post_num;
	
	//작성자ID
	private	String user_num;
	
	//작성자이름(별칭)
	private	String username;
	
	//작성일
	private Timestamp created_at;
	
	//수정일
	private Timestamp updated_at;
	
	//본문내용
	private String content;
	
	//답글그룹
	private long comment_group;
	
	//답변글 단계
	private long comment_step;
	
	//답변글의 들여쓰기
	private long indent;
	
	//부모댓글번호
	private long prnum;
	
	//부모댓글번호
	private long target_user_num;
	
	//답글대상이름
	private String Target_username;
	
}
