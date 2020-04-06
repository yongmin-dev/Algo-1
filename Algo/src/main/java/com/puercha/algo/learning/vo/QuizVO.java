package com.puercha.algo.learning.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import lombok.Data;

/**
 * @author Yongmin
 * 마무리문제 VO
 * 
 */
@Entity
@Data
public class QuizVO {

	//문제 번호
	private long quizNum;
	
	//단원 번호
	private long unitNum;
	
	//문제명
	private String title;
	
	//문제내용
	private String content;
	
	//난이도
	private long difficulty;
	
	//정답
	private String correctAnswer;
	
	//풀이
	private String solution;
	
	//생성일시
	private Date createdAt;
	
	//수정일시
	private Date updatedAt;
	
	//출제자
	private long userNum;
	
	//퀴즈 정답 선택지
	private List<QuizAnswerVO> answerList;
		
}
