package com.puercha.algo.learning.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author Yongmin
 * 단원 VO
 * 
 */
@Data
public class UnitVO {
	
	//단원
	private long unitNum;
	
	//과목번호
	private long subjectNum;
	
	//내용
	private String content;
	
	//제목
	private String title;
	
	//과목 내 챕터 번호
	private String chapterDepth;
	
	//생성 일시
	private Date createdAt;
	
	//수정 일시
	private Date updatedAt;
	
	private boolean passesUnit;// 통과여부
	private int totalQuiz; // 총 퀴즈 개수 
	private int passedQuiz; // 통과한 퀴즈 개수

}
