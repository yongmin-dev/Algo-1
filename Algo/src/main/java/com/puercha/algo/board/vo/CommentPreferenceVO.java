package com.puercha.algo.board.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author Yongmin
 * 댓글 선호도 VO
 * 
 */
@Data
public class CommentPreferenceVO {
	
	//선호도 번호
	private long preferenceNum;
	
	//댓글 번호
	private long commentNum;
	
	//투표자(회원ID)
	private String userNum;
	
	//댓글선호도
	private char preference;
	
	//투표일시
	private Timestamp created_at;
	
	//변경일시
	private Timestamp updated_at;
	
}
