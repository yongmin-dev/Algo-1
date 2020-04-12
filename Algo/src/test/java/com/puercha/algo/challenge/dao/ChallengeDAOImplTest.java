package com.puercha.algo.challenge.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.puercha.algo.challenge.vo.ChallengeCaseVO;
import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.challenge.vo.ChallengeVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ChallengeDAOImplTest {
	final static Logger logger = LoggerFactory.getLogger(ChallengeDAOImplTest.class); 
	@Inject
	ChallengeDAO challengeDAO;

	/* Create */
//	 도전과제 생성
	@Test
	@Disabled
	public void TestInsertChallenge() {
		ChallengeVO challenge = new ChallengeVO();
		challenge.setContent("input a와 input b를 더한 값을 출력하라");
		challenge.setLimitMemory(500000000);
		challenge.setLimitTime(2000);
		challenge.setTitle("a와 b를 더하라!");
		challenge.setUserNum(1);
		int cnt = challengeDAO.insertChallenge(challenge);
		assertEquals(1, cnt);
		logger.info(challenge.toString());
	}
	
	// 도전과제 테스트 케이스 생성
	@Test
	@DisplayName("도전과제 테스트 케이스 생성")
	@Disabled
	public void testInsertTestCase() {
		ChallengeCaseVO testCase = new ChallengeCaseVO();
		testCase.setCNum(21);
		testCase.setInput("5 3");
		testCase.setOutput("8");
		testCase.setUserNum(1);
		int cnt = challengeDAO.insertTestCase(testCase);
		assertEquals(1, cnt);
		logger.info(testCase.toString());
	}

	@Test
	@Disabled
	@DisplayName("도전과제 결과 생성")
	public void insertChallengeResult() {
		ChallengeResultVO result = new ChallengeResultVO();
		result.setCNum(21);
		result.setProcessingTime(-1);
		result.setUsedMemory(-1);
		result.setUserNum(1);		
		result.setCode("import java.util.Scanner;\r\n" + 
				"\r\n" + 
				"public class Main {\r\n" + 
				"	public static void main(String[] args) {\r\n" + 
				"		Scanner sc = new Scanner(System.in);\r\n" + 
				"		int a = sc.nextInt();\r\n" + 
				"		int b = sc.nextInt();\r\n" + 
				"		try {\r\n" + 
				"			Thread.sleep(5000);\r\n" + 
				"		} catch (InterruptedException e) {\r\n" + 
				"			// TODO Auto-generated catch block\r\n" + 
				"			e.printStackTrace();\r\n" + 
				"		}\r\n" + 
				"		System.out.println(a+b);\r\n" + 
				"		\r\n" + 
				"	}\r\n" + 
				"}\r\n" + 
				"");
		result.setResultComment("panding");
		result.setStatus('P');
		int cnt = challengeDAO.insertChallengeResult(result);
		assertEquals(1, cnt);
		logger.info(result.toString());
	}


	/* Read */
	// 도전과제 row 1개 열람

	public void testSelectOne(long cNum) {

	}

	// 도전과제 목록 검색
	public void testSelectAllChallenge(long startRowNum, long endRowNum, String type, String keyword) {
		
	}

	// 도전과제 결과 보기
	public void testSelectOneResult(long resultNum) {

	}

	/**
	 * 전체 도전과제 결과 보기
	 * 
	 * @param startRowNum 시작 행
	 * @param endRowNum   끝 행
	 * @return 도전과제결과 List
	 */
	public void testSelectAllResult(long startRowNum, long endRowNum) {

	}

	/**
	 * 문제 내 전체 도전과제 결과 보기
	 * 
	 * @param startRowNum 시작 행
	 * @param endRowNum   끝 행
	 * @param cNum        도전과제 번호
	 * @return 도전과제결과 List
	 */
	public void testSelectAllResult(long startRowNum, long endRowNum, long cNum) {

	}

	/**
	 * 문제에서 내가 제출한 도전과제 결과 보기
	 * 
	 * @param startRowNum 시작 행
	 * @param endRowNum   끝 행
	 * @param cNum        도전과제 번호
	 * @param userNum     사용자 번호
	 * @return 도전과제결과 List
	 */
	public void testSelectAllResult(long startRowNum, long endRowNum, long cNum, long userNum) {

	}

//	ChallengeResultVO getLastResult(long cNum);

	// 도전과제의 결과 중 가장 최근 과제 결과를 가져옴
	public void getLastResult(long cNum, long userNum) {

	}

	// 모든 도전과제의 모든 결과 개수를 가져옴
	public void getCountTotalResult() {

	}

	// 도전과제의 모든 결과 개수를 가져옴
	public void getCountTotalResult(long cNum) {

	}

	// 케이스 번호로 케이스 데이터 하나 열람
	public void selectOneCase(long caseNum) {

	}

	@Test
	@DisplayName("도전과제내 모든 케이스를 가져옴")
	public void testSelectAllCase() {
		long cNum = 21;
		List<ChallengeCaseVO> list =  challengeDAO.selectAllCaseMetaDatas(cNum);
		assertNotNull(list);
		logger.info("meta data list:"+list);
		list =  challengeDAO.selectAllCase(cNum);
		assertNotNull(list);
		logger.info("list:"+list);
	}

	/* Update */
	// 도전과제 변경
	public void updateChallenge(ChallengeVO challenge) {

	}

	// 도전과제 통과 수 증가
	public void increaseChallengePassNum(long cNum) {

	}

	// 도전과제 통과 수 변경

	public void updatedPassNum(long cNum, long passNum) {

	}

	// 도전과제 결과 변경
	public void updateChallengeResult(ChallengeResultVO result) {

	}

	// 도전과제 테스트케이스 변경
	public void updateChallengeCase(ChallengeCaseVO testCase) {

	}

	/* Delete */
	// 도전과제 삭제
	public void deleteChallenge(long cNum) {

	}

	// 도전과제 결과 삭제
	public void deleteChallengeReseult(long resultNum) {
		
	}

	// 도전과제 테스트케이스 삭제
	 
	public void deleteChallengeCase(long caseNum) {
		
	}

	// 도전과제에 포함된 모든 테스트케이스삭제
	public void deleteAllCase(long cNum) {
		
	}

	@Test
	public void testSelectHotChallenge() {
		logger.info("challenges :"+challengeDAO.selectHotChallenge(4));
	}
	
	@Test
	public void testSelectTopRanker() {
		
		logger.info("rankers:"+challengeDAO.selectTopRanker(5));
	}
}
