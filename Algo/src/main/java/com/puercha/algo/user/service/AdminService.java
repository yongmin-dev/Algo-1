package com.puercha.algo.user.service;

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
	
	
	
}
