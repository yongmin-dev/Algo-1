package com.puercha.algo.learning.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author Yongmin
 * 단원완료 VO
 * 
 */
@Data
public class UnitCompletionVO {
	
	//완료번호
	private long comNum;
	
	//과목번호
	private long unitNum;
	
	//유저번호
	private long userNum;
	
	//완료상태
	private char status;
	
	//생성일시
	private Date createdAt;
	
	//수정일시
	private Date updatedAt;
	
}
