package com.puercha.algo.learning.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author Yongmin
 * 단원완료 VO
 * 
 */
@Data
public class UnitCompletionVO {

	private long comNum;
	private long unitNum;
	private long userNum;
	private char status;
	private Timestamp created_at;
	private Timestamp updated_at;
	
}
