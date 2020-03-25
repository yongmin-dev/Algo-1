package com.puercha.algo.learning.vo;

import java.util.Date;

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
	private char isCorrect;
	
	//생성일시
	private Date createdAt;
	
	//수정일시
	private Date updatedAt;
	
}
