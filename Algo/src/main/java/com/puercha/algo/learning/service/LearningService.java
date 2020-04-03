package com.puercha.algo.learning.service;

import java.util.List;
import java.util.Map;

import com.puercha.algo.common.PageManager;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;

/**
 * 이론학습 기능 정의 
 * @author Hyeonuk
 *
 */
public interface LearningService {
	
	//과목 리스트 보기
	//리스트 + 검색타입, 검색어
	List<SubjectVO> subjectList(String reqPage, String searchType, String keyword);
	
	//페이지제어
	PageManager getPageManager(String reqPage, String searchType, String keyword);
	
	//단원 불러오기
	List<UnitVO> unitList(String subjectNum);
	
	//단원 내용 불러오기
	Map<String, Object> viewUnit(String unitNum);
	
	
	
	
}
