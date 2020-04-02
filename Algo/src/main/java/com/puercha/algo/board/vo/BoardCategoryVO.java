package com.puercha.algo.board.vo;

import lombok.Data;

/**
 * @author Yongmin
 * 게시판 카테고리 VO
 * 
 */
@Data
public class BoardCategoryVO {
	
	//분류 번호
	private long categoryNum;
	
	//분류명
	private String name;
}
