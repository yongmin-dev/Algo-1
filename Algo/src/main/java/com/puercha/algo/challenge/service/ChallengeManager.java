package com.puercha.algo.challenge.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import com.puercha.algo.challenge.dao.ChallengeDAO;
import com.puercha.algo.challenge.service.codelauncher.CodeTester;
import com.puercha.algo.challenge.service.codelauncher.JavaCodeTester;
import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.challenge.vo.ChallengeVO;

public class ChallengeManager implements ChallengeService {
	@Inject
	ChallengeDAO challengeDAO;
	
	@Inject
	@Qualifier("javaCodeTester")
	CodeTester javaCodeTester;
	
	@Override
	public List<ChallengeVO> serachChallenges(long page, String keyword, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChallengeVO viewChallenges(long ChallengeNum) {
		// TODO Auto-generated method stub
		return null;
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
	 * @return
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
