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
	
	private long subjectNum; // 과목번호	
	
	private String title; // 과목명	
	
	private Date createdAt; // 생성일시	
	
	private Date updatedAt; // 수정일시	
	
	private long userNum; // 출제자	
	
	private String  username; 
	
	private byte[] imageData; // 사진데이터	
	 
	private long imageSize; // 사진 크기	
	 
	private String imageType; // 사진 타입	
	
	private String imageName; // 사진 이름

	private double progressRate; // 진척도
	
	@Override
	public String toString() {
		return "SubjectVO [subjectNum=" + subjectNum + ", title=" + title + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", userNum=" + userNum + ", imageSize=" + imageSize + ", imageType=" + imageType + ", progressRate="+ progressRate
				+ ", imageName=" + imageName + "]";
	}
	
	
}
