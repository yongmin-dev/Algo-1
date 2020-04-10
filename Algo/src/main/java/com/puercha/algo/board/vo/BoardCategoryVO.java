package com.puercha.algo.board.vo;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author Yongmin
 * 게시판 카테고리 VO
 * 
 */

@Data
public class BoardCategoryVO {
	
	//분류 번호
	@Min(value = 1,message = "메뉴선택하세요")
	private long categoryNum;
	
	//분류명
	private String name;
}
