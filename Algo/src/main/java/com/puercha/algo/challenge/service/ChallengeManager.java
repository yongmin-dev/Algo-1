package com.puercha.algo.challenge.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.puercha.algo.challenge.dao.ChallengeDAO;
import com.puercha.algo.challenge.service.codelauncher.CodeTester;
import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.challenge.vo.ChallengeVO;
import com.puercha.algo.common.FindCriteria;
import com.puercha.algo.common.PageManager;

@Service
public class ChallengeManager implements ChallengeService {
	private static final Logger logger = LoggerFactory.getLogger(ChallengeManager.class);
	@Inject
	ChallengeDAO challengeDAO;
	
	@Inject
	@Qualifier("javaCodeTester")
	CodeTester javaCodeTester;
	
	@Override
	public Map<String,Object> serachChallenges(long page, String keyword, String type) {
		Map<String,Object> searchingResult = new HashMap<String,Object> (); // 조회 결과
		
		// 도전과제 모든 행 개수 (검색 시 에도 같듬)
		long totalRowNum =  challengeDAO.getCountTotalChallenges(type, keyword); // 총 개수
		FindCriteria fc = new FindCriteria((int)page);
		fc.setKeyword(keyword);
		fc.setSearchType(type);
		PageManager pageManager = new PageManager(fc, totalRowNum);// page메니저
		searchingResult.put(KEY_PAGE_INFO, pageManager); // 결과에 추가
		logger.info("pageManager:"+pageManager);
		// 조회
		List<ChallengeVO> list = challengeDAO.selectAllChallenge(fc.getStartRec(), fc.getEndRec(), type, keyword);
		searchingResult.put(KEY_CHALLENGE_LIST, list);
		
		
		return searchingResult;
	}

	@Override
	public ChallengeVO viewChallenges(long challengeNum) {		
		return challengeDAO.selectOne(challengeNum);
	}

	@Override
	public int createdNewChallenge(ChallengeVO challenge) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteChallenge(long ChallengeNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 코드의 테스트 실행
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @param code 코드 
	 * @return 성공시 생성된 도전과제 결과 VO 반환
	 */
	@Override
	public ChallengeResultVO doTest(long cNum, long userNum, String code) {
		// result데이터 생성
		ChallengeResultVO result = createNewResult(cNum,userNum,code);
		// 테스트 시작
		javaCodeTester.doTest(result);		
		return result;
	}
	/**
	 * VO생성 및 DB에 insert함
	 * @param cNum
	 * @param userNum
	 * @param code
	 * @return 성공 시 1
	 */
	private ChallengeResultVO createNewResult(long cNum, long userNum, String code) {
		ChallengeResultVO result = new ChallengeResultVO();
		result.setCNum(cNum);
		result.setCode(code);
		result.setUserNum(userNum);
		result.setProcessingTime(-1);
		result.setUsedMemory(-1);
		result.setStatus('p');
		result.setResultComment("pending");		
		challengeDAO.insertChallengeResult(result);
		return result;
	}
	/**
	 * 코드 실행 결과
	 * @param resultNum 결과 번호
	 * @return 도전과제 결과 VO
	 */	
	@Override
	public ChallengeResultVO getResult(long resultNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
