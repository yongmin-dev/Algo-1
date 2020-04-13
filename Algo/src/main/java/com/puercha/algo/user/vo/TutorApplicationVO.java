package com.puercha.algo.user.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * @author Yongmin
 * 튜터신청서VO
 * 
 */
@Data
public class TutorApplicationVO {
	
	//신청번호
	private long applicationNum;
	
	//유저번호
	private long userNum;
	private String username; // 사용자명
	//제목
	private String title;
	
	//내용
	private String content;
	
	//첨부파일
	private byte[] attachmentData;
	
	//첨부파일 크기
	private long attachmentSize;
	
	//첨부파일 이름
	private String attachmentName;
	
	//첨부파일 타입
	private String attachmentType;
	
	//승인여부
	private char approval;
	
	//생성일시
	private Date createdAt;
	
	//변경일시
	private Date updatedAt;
	
	//첨부파일(업로드시)
	private MultipartFile file;
	
	public String getApprovalAsString() {
		return ""+this.approval;
	}
}
