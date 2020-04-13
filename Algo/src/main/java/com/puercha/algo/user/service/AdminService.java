package com.puercha.algo.user.service;

import java.util.Map;

import com.puercha.algo.user.vo.TutorApplicationVO;

/**
 * 관리자 기능 정의
 * @author Hyeonuk
 *
 */
public interface AdminService {
	
	/**
	 * 튜터 신청
	 * @param tutorApplication 튜터 신청서 vo
	 * @return 성공 시 새로 생성된 신청서의 번호
	 */
	long apply(TutorApplicationVO tutorApplication);
	/**
	 * 튜터 신청서 리스트
	 * @param page 신청서 페이지
	 * @return 신청서 리스트
	 */
	Map<String,Object> getApplicationList(long page);
	
	/**
	 * 모든 신청서의 개수를 가져온다.
	 * @return 신청서 총 개수
	 */
	long getTotalApplicationNum();

	int commitApproval(long applicationNum, char approval);
	
}
