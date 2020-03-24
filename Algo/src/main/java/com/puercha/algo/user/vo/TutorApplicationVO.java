package com.puercha.algo.user.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author Yongmin
 * 튜터신청서VO
 * 
 */
@Data
public class TutorApplicationVO {
	
	//신청번호
	private long applicationNum;
	
	//유저번호
	private long userNum;
	
	//제목
	private String title;
	
	//제목
	private String content;
	
	//첨부파일
	private byte[] attachment;
	
	//승인여부
	private char approval;
	
	//생성일시
	private Timestamp created_at;
	
	//변경일시
	private Timestamp updated_at;
}
