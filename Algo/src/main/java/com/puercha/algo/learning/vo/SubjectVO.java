package com.puercha.algo.learning.vo;

import java.sql.Timestamp;

import lombok.Data;



/**
 * @author Yongmin
 * 과목 VO
 * 
 */
@Data
public class SubjectVO {

	private long subjectNum;
	private String title;
	private Timestamp created_at;
	private Timestamp updated_at;
	private long userNum;
	
}
