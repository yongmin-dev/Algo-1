package com.puercha.algo.common;

import lombok.Data;



/**
 * 검색 기능 페이징
 * @author Yongmin
 *
 * 
 */
@Data
public class FindCriteria extends RowCriteria {

	//검색유형
	private String searchType;
	
	//검색어
	private String keyword;
	
	public FindCriteria(int reqPage) {
		super(reqPage);
	}
	
	public FindCriteria(int reqPage, String searchType, String keyword) {
		this(reqPage);
		this.searchType = searchType;
		this.keyword = keyword;
	}
	
}
