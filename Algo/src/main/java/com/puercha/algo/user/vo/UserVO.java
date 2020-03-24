package com.puercha.algo.user.vo;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Yongmin
 * 사용자 VO
 * 
 */
@Data
public class UserVO {

	// =====================================
	// NUMBER => long
	// VARCHAR2 => String
	// CHAR => String
	// DATE => import java.util.Date
	// TIMESTAMP => import java.sql.Timestamp;
	// =====================================

	// 유저번호
	private long number;

	// 이메일 정규식 포함
	@NotNull
	@Pattern(regexp = "^\\w+@\\w+\\.\\w+(\\.\\w+)?$", message = "다음과 같은 형식으로 입력해주세요 aaa.@bbb.com")
	private String email;

	// 비밀번호 정규식 포함
	@NotNull
	@Size(min = 6, max = 12, message = "비밀번호는 6~12자리로 입력해주세요.")
	private String pw;

	// 전화번호 정규식 포함
	// @NotNull
	@Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "다음과 같은 형식으로 입력해주세요 010-1234-5678")
	private String tel;

	// 별명 정규식 포함
	@NotNull
	@Pattern(regexp = "^[0-9a-zA-Z가-힣]{4,20}$", message = "한글,영문,숫자  // 한글 10자, 영문 20자 이내로 사용해주세요")
	private String username;

	// 성별
	private char gender;

	// 생년월일 yyyymmdd
	private Date birth;

	// 생성일시
	@JsonFormat(pattern = "yyyyMMdd")
	private Timestamp created_at; // (setmethod따로 만들어?)

	// 수정 일시
	@JsonFormat(pattern = "yyyyMMdd")
	private Timestamp updated_at;

	// 유형(학습자, 튜터)
	private char type;

}
