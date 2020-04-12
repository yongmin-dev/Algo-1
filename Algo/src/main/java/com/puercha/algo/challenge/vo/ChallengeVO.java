package com.puercha.algo.challenge.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author Yongmin 
 * 도전문제 VO
 */
@Data
public class ChallengeVO {

	// 도전과제번호
	private long cNum;

	// 출제자 번호
	private long userNum;

	// 출제자 이름
	private String username;
	
	// 도전과제 내용
	private String content;

	// 제한메모리사용량
	private long limitMemory;

	// 제한시간
	private long limitTime;

	// 제목
	private String title;

	// 통과한 사람수
	private long passNum;

	private long challenger; // 도전자 수
	
	// 생성 일시
	private Date createdAt;

	// 수정일시
	private Date updatedAt;

}
