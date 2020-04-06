package com.puercha.algo.challenge.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author Yongmin
 * 도전문제 결과 VO
 * 
 */
@Data
public class ChallengeResultVO {

	//결과번호
	private long resultNum;
	
	//제출한답
	private String code;
	
	//메모리 사용량
	private long usedMemory;
	
	//수행시간
	private long processingTime;
	
	//도전과제번호
	private long cNum;
	
	//유저 번호
	private long userNum;
	

	
	//결과 상태
	private char status;
	
	//결과에 대한 코멘트
	private String resultComment;
	
	//생성 일시
	private Date createdAt;
	
	//수정 일시
	private Date updatedAt;


	/* 랭킹 관련 */
	// 사용자 이름
	private String username;
	// 랭크
	private long rankNum;
}
