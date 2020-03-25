package com.puercha.algo.challenge.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author Yongmin 
 * 도전문제 VO
 */
@Data
public class ChanllengeVO {

	// 도전과제번호
	private long cNum;

	// 출제자
	private long userNum;

	// 도전과제 내용
	private String content;

	// 제한메모리사용량
	private long limit_memory;

	// 제한시간
	private long limit_time;

	// 제목
	private String title;

	// 통과한 사람수
	private long passNum;

	// 생성 일시
	private Timestamp created_at;

	// 수정일시
	private Timestamp updated_at;

}
