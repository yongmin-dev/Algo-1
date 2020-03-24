package com.puercha.algo.board.vo;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 
 * @author Yongmin
 * 게시글 VO
 */

@Data
public class BoardPostVO {

	// =====================================
	// NUMBER => long
	// VARCHAR2 => String
	// CHAR => String
	// DATE => import java.util.Date
	// TIMESTAMP => import java.sql.Timestamp;
	// =====================================

	// 게시글 번호
	private long postNum;

	// 분류 카테고리
	private long categoryNum;

	// 제목
	@NotNull
	@Size(min = 4, max = 50, message = "5~50자 이내로 작성하세요")
	private String title;

	// 사용자 번호
	private long userNum;

	// 작성자 이름
	private String username;

	// 작성일
	@JsonFormat(pattern = "yyyyMMdd")
	private Timestamp created_at;

	// 수정일
	@JsonFormat(pattern = "yyyyMMdd")
	private Timestamp updated_at;

	// 조회수
	private long hit;

	// 본문내용
	@NotNull
	@Size(min = 4, max = 50, message = "20자 이상 작성하세요")
	private String content;

	// 답글그룹
	private long postGroup;

	// 답변글의 단계
	private long post_step;

	// 답변글 들여쓰기
	private long indent;

}
