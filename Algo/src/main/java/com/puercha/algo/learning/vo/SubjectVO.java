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
	
	//사진데이터
	private byte[] imageData;
	
	//사진 크기
	private long imageSize;
	
	//사진 타입
	private String imageType;
	
	//사진 이름
	private String imageName;
	
}
