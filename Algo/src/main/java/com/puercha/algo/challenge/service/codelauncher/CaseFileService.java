package com.puercha.algo.challenge.service.codelauncher;

import java.io.File;
import java.util.Map;

import com.puercha.algo.challenge.vo.ChallengeCaseVO;

public interface CaseFileService {
	public static final String KEY_REGULAR_INPUT_FILE="inputfile"; // 인풋 키	
	public static final String KEY_EXPECTED_OUTPUT_FILE="expectedOutput"; // 아웃풋키
	public static final String NAME_INPUT_DIR = "input";
	public static final String NAME_EXPECTED_OUTPUT_DIR = "expected";
	public static final String NAME_OUTPUT_FILE= "output.txt";
	/** 
	 * Case 파일쓰기
	 * @param caseData
	 * @param dir 
	 * @param fileName 파일이름
	 * @return 파일 쓴 이후 
	 */
	File writeFile(String caseData,File dir, String fileName);
	
	
	
	/**
	 * 케이스파일을 준비한다.
	 * @param caseNum 해당 케이스의 케이스번호
	 * @return 성공시 1
	 */
	int prepareCaseFile(long cNum, long caseNum);
	
	/**
	 * 도전과제의 케이스파일들을 준비한다.
	 * @param cNum
	 * @return 성공시 1
	 */
	int prepareAllCaseFiles(long cNum);
	
	/**
	 * 특정 케이스에 대한 input, expected 파일을 씁니다.
	 * @param challengeCase
	 * @return
	 */
	int writeCaseFile(ChallengeCaseVO challengeCase);
	
	/**
	 *  모든 케이스 파일 쓰기
	 * @param cNum 도전과제 번호
	 * @param dir 도전과제 저장 경로
	 * @return 성공 시 1
	 */
	int writeAllCase(long cNum, File dir);
	
	/**
	 * 해당 케이스에 대한 테스트파일들을 가져옴
	 * @param caseNum
	 * @return input과 expected 가 생김
	 */
	Map<String,File> getCaseFile(File challengeDir, long caseNum);
	
	/**
	 * root 폴더를 지정함
	 * @param dir 지정할 rootDir
	 */
	void setRootDir(File dir);
	
	/**
	 * 도전과제의 폴더를 생성함
	 * @param cNum
	 * @return 생성 후 dir폴더
	 */
	File makeChallengeDir(long cNum);
	
	/**
	 * 해당 케이스가 가능한지
	 * @param caseNum 케이스 번호
	 * @return 가능하면 true
	 */
	boolean isAvailableCaseFile(long caseNum);
	
	/**
	 * 해당 도전과제가 가능한 지
	 * @param cNum 도전과제 번호
	 * @return 가능하면 true
	 */
	boolean isAvailableChallengeFile(long cNum);
	
	/**
	 * 최신버전의 case 파일인지
	 * @param caseNum
	 * @return
	 */
	boolean isLatestCaseFile(long caseNum);
	
	/**
	 * 루트디렉토리의 File객체를 가져옴
	 * @return 루트 디렉토리의 File객체
	 */
	File getRootDir();
	
	/**
	 * 도전과제 번호에 해당하는 directory를 가져옴 
	 * @param ChallengeNum 도전과제 번호
	 * @return 도전과제 directory
	 */
	File getChallengeDir(long ChallengeNum);
	
}
