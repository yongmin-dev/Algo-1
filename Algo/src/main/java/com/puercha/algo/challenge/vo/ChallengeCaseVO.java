package com.puercha.algo.challenge.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author Yongmin 
 * 도전문제 케이스 VO
 */
@Data
public class ChallengeCaseVO {

	// 케이스 번호
	private long caseNum;

	// 도전과제 번호
	private long cNum;

	// 입력
	private String input;

	// 출력
	private String output;

	// 답안작성자
	private long userNum;

	// 생성일시
	private Date createdAt;

	// 수정일시
	private Date updatedAt;

}
