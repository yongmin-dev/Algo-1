package com.puretea.algo.board_post.vo;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.puretea.algo.app_user.vo.Data;
import com.puretea.algo.app_user.vo.Entity;

@Data
public class Board_PostVO {

	// =====================================
	// NUMBER => long
	// VARCHAR2 => String
	// CHAR => String
	// DATE => import java.util.Date
	// TIMESTAMP => import java.sql.Timestamp;
	// =====================================

	// 게시글 번호
	private long post_num;

	// 분류 카테고리
	private long category_num;

	// 제목
	@NotNull
	@Size(min = 4, max = 50, message = "5~50자 이내로 작성하세요")
	private String title;

	// 사용자 번호
	private long user_num;

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
	private long post_group;

	// 답변글의 단계
	private long post_step;

	// 답변글 들여쓰기
	private long indent;

}
