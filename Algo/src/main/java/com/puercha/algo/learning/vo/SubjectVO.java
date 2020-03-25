package com.puercha.algo.learning.vo;

import java.util.Date;

import lombok.Data;



/**
 * @author Yongmin
 * 과목 VO
 * 
 */
@Data
public class SubjectVO {

	
	//과목번호
	private long subjectNum;
	
	//과목명
	private String title;
	
	//생성일시
	private Date createdAt;
	
	//수정일시
	private Date updatedAt;
	
	//출제자
	private long userNum;
	
}
