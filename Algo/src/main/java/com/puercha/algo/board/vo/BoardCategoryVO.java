package com.puercha.algo.board.vo;

import javax.persistence.Entity;

import lombok.Data;

/**
 * @author Yongmin
 * 게시판 카테고리 VO
 * 
 */
@Entity
@Data
public class BoardCategoryVO {
	
	//분류 번호
	private long categoryNum;
	
	//분류명
	private String name;
}
