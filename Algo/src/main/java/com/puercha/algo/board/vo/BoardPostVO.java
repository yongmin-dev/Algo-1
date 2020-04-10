package com.puercha.algo.board.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 
 * @author Yongmin
 * 게시글 VO
 */

@Entity
@Data
public class BoardPostVO {

	// =====================================
	// NUMBER => long
	// VARCHAR2 => String
	// CHAR => String
	// DATE => import java.util.Date
	// Date => import java.util.Date;
	// =====================================

	// 게시글 번호
	private long postNum;

	// 분류 카테고리
	@Valid
	private BoardCategoryVO category;
	
	
//	private long categoryNum;
		
	
	// 제목
	@NotNull
	@Size(min = 4, max = 50, message = "5~50자 이내로 작성하세요")
	private String title;

	// 사용자 번호
	private long userNum;

	// 작성자 이름
	private String userName;

	// 작성일
	@JsonFormat(pattern = "yyyyMMdd")
	private Date createdAt;

	// 수정일
	@JsonFormat(pattern = "yyyyMMdd")
	private Date updatedAt;

	// 조회수
	private long hit;

	// 본문내용
	@NotNull
	@Size(min = 4, max = 50, message = "20자 이상 작성하세요")
	private String content;

	// 답글그룹
	private long postGroup;

	// 답변글의 단계
	private long postStep;

	// 답변글 들여쓰기
	private long indent;
	
//	@Nullable
	private List<MultipartFile> files;

}
