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
import com.puercha.algo.common.RowCriteria;

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
		
		return challengeDAO.selectOneResult(resultNum);
	}

	/**
	 * 전체 랭킹을 반환함
	 * @param page 랭킹 페이지
	 * @param cNum 도전문제 번호
	 * @param type 타입
	 * @return 랭킹의 리스트 및 페이지 정보
	 */
	@Override
	public Map<String,Object> getRankingList(long page, long cNum, String type) {
		RowCriteria RowCriteria = new FindCriteria((int)page);
		long totalRows = 0;
		totalRows = challengeDAO.getCountTotalRank(cNum);
		PageManager pageManager = new PageManager(RowCriteria, totalRows);
		long startRowNum = pageManager.getRc().getStartRec();
		long endRowNum = pageManager.getRc().getEndRec();
		Map<String,Object> datas =  new HashMap<>();
		List<ChallengeResultVO> list = challengeDAO.selectAllRanks(startRowNum, endRowNum, cNum, type);
		datas.put("list",list);
		datas.put("pageInfo",pageManager);
		return datas;
	}

	

	
	/**
	 * 랭킹을 검색함
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @param type 검색타입 (메모리기준,사용시간기준)
	 * @return 내 랭킹 결과 (등수, 도전과제결과객체)
	 */
//	@Override
//	public Map<String, Object> getRank(long cNum, long userNum, String type) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * user의 랭킹을 검색함
	 * @param cNum 도전과제 번호
	 * @param userNum 사용자 번호
	 * @param type 검색타입 (메모리기준,사용시간기준)
	 * @return 내 랭킹 결과 VO
	 */
	@Override
	public ChallengeResultVO getMyRank(long cNum, long userNum, String type) {
		logger.info("getMyRank(long cNum, long userNum, String type)");
		return challengeDAO.selectOneRank( cNum,  userNum,  type);
	}

	/**
	 * 핫한 문제들을 가져옴
	 * @param num 문제 개수
	 * @return 문제 리스트 
	 */
	@Override
	public List<Map<String, Object>> getHotChalllenges(long num) {
		logger.info("getHotChalllenges(long num)");
		return challengeDAO.selectHotChallenge(num);
	}

	/**
	 * 탑 랭커들의 정보를 가져옴
	 * @param rankers 랭커의 수
	 * @return 랭커 정보의 리스트
	 */
	@Override
	public List<Map<String, Object>> getTopRankers(long rankersNum) {
		logger.info("getTopRankers(long rankersNum)");
		return challengeDAO.selectTopRanker(rankersNum);
	}

	
	
	
}
