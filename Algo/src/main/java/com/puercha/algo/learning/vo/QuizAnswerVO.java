package com.puercha.algo.learning.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author Yongmin
 * 마무리문제 답안 VO
 * 
 */
@Data
public class QuizAnswerVO {

	//답변 번호
	private long answerNum;
	
	//문제 번호
	private long quizNum;
	
	//선택지 내용
	private String content;
	
	//정답여부
	private char is_correct;
	
	//생성일시
	private Timestamp created_at;
	
	//수정일시
	private Timestamp updated_at;
	
}
