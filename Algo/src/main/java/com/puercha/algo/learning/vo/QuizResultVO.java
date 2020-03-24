package com.puercha.algo.learning.vo;

import java.sql.Timestamp;

import lombok.Data;
/**
 * @author Yongmin
 * 마무리문제 VO
 * 
 */
@Data
public class QuizResultVO {
	
	//결과번호
	private long resultNum;
	
	//문제번호
	private long quizNum;
	
	//유저번호
	private long userNum;
	
	//답
	private String answer;
	
	//정답여부
	private char status;
	
	//생성일시
	private Timestamp created_at;
	
	//수정일시
	private Timestamp updated_at;
	
}
