package com.puercha.algo.learning.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author Yongmin
 * 마무리문제 VO
 * 
 */
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
	private String correct_answer;
	
	//풀이
	private String solution;
	
	//생성일시
	private Timestamp created_at;
	
	//수정일시
	private Timestamp updated_at;
	
	//출제자
	private long userNum;
		
}
