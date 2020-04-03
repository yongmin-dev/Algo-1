package com.puercha.algo.challenge.service.codelauncher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.puercha.algo.challenge.dao.ChallengeDAO;
import com.puercha.algo.challenge.vo.ChallengeCaseVO;
@Service
public class CaseFileManager implements CaseFileService {
	final static Logger logger = LoggerFactory.getLogger(CaseFileManager.class);
	@Inject
	ChallengeDAO challengeDAO;
	
	
	File rootDir;//root 디렉터리
	
	public File getRootDir() {
		return rootDir;
	}

	public CaseFileManager() {
		Properties properties = new Properties();
		ClassPathResource classPathResource = new ClassPathResource("config"+File.separator+"code-tester.properties");
		logger.info("file:"+ classPathResource.getFilename());
		try {
			properties.load(new FileInputStream(classPathResource.getFile()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("rootDir: "+properties.getProperty("rootDir"));
		this.rootDir = new File(properties.getProperty("rootDir"));  
	}
	
	/** 
	 * Case 파일쓰기
	 * @param caseData
	 * @param dir
	 * @return 성공 시 작성한 파일 
	 */
	@Override
	public File writeFile(String caseData, File dir, String fileName) {
		File caseFile = new File(dir.getAbsolutePath()+File.separator + fileName);		
		BufferedWriter writer = null; 		
		try {
			writer = new BufferedWriter(new FileWriter(caseFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(writer!=null) {
			try {
				writer.write(caseData);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return caseFile;
	}

	/**
	 * 케이스파일을 준비한다.
	 * @param caseNum 해당 케이스의 케이스번호
	 * @return 성공시 1
	 */
	@Override
	public int prepareCaseFile(long cNum, long caseNum) {
		File challengeDir = new File(rootDir,String.valueOf(cNum)); // 도전과제 디렉토리
		if (!challengeDir.exists()) {
			challengeDir.mkdirs();
		}
		File inputDir = new File(challengeDir,NAME_INPUT_DIR); // 인풋 디렉토리
		if(!inputDir.exists()) { 
			inputDir.mkdirs();
		}
		File expectedDir = new File(challengeDir,NAME_EXPECTED_OUTPUT_DIR); // 예상 답안
		if(!expectedDir.exists()) {
			expectedDir.mkdirs();
		}
		File inputCaseFile = new File(inputDir,String.valueOf(caseNum)); // 인풋 데이터
		File expectedCaseFile = new File(expectedDir,String.valueOf(caseNum)); // 예상 답안 데이터
		if(!inputCaseFile.exists()||!expectedCaseFile.exists()) {
			ChallengeCaseVO challengeCase = challengeDAO.selectOneCase(caseNum);
			writeCaseFile(challengeCase);
		}
		if(challengeDir.exists() && inputCaseFile.exists() && expectedCaseFile.exists()) // 다 존제하면
			return 1;
		return 0;
	}

	/**
	 * 도전과제의 케이스파일들을 준비한다.
	 * @param cNum
	 * @return 성공시 1
	 */
	@Override
	public int prepareAllCaseFiles(long cNum) {
		List<ChallengeCaseVO> list = challengeDAO.selectAllCaseMetaDatas(cNum);
		
		return 0;
	}

	/**
	 * 특정 케이스에 대한 input, expected 파일을 씁니다.
	 * @param challengeCase
	 * @return 성공 시 1
	 */
	@Override
	public int writeCaseFile(ChallengeCaseVO challengeCase) {
		// root에서 input및 expect dir열기
		long cNum = challengeCase.getCNum();
		long caseNum = challengeCase.getCaseNum();
		File challengeDir = new File(rootDir,String.valueOf(cNum)); // 도전과제 경로
//		logger.info("challengeDir exists:"+challengeDir.exists());
		File inputDir = new File(challengeDir,NAME_INPUT_DIR); // 입력
		inputDir.mkdirs();
		if(challengeCase.getInput()==null || challengeCase.getOutput()==null) {
			challengeCase = challengeDAO.selectOneCase(caseNum);
		}
		writeFile(challengeCase.getInput(), inputDir, String.valueOf(caseNum));
//		logger.info("inputDir exists:"+inputDir.exists());
		File expectedDir = new File(challengeDir,NAME_EXPECTED_OUTPUT_DIR); // 예상결과
		expectedDir.mkdirs();
		writeFile(challengeCase.getOutput(), expectedDir, String.valueOf(caseNum));
//		logger.info("expectedDir exists:"+expectedDir.exists());
		
		return 1;
	}
	
	

	/**
	 *  모든 케이스 파일 쓰기
	 * @param cNum 도전과제 번호
	 * @param dir 도전과제 저장 경로
	 * @return 성공 시 1
	 */
	@Override
	public int writeAllCase(long cNum, File dir) {
		List<ChallengeCaseVO> list = challengeDAO.selectAllCaseMetaDatas(cNum);
		for(ChallengeCaseVO challengeCase : list) {
			writeCaseFile(challengeCase);
		}
		return 1;
	}

	/**
	 * 해당 케이스에 대한 테스트파일들을 가져옴
	 * @param caseNum
	 * @return input과 expected 가 생김
	 */
	@Override
	public Map<String, File> getCaseFile(File challengeDir,long caseNum) {
		File inputDir = new File(challengeDir,NAME_INPUT_DIR);
		File expectedDir = new File(challengeDir,NAME_EXPECTED_OUTPUT_DIR);
		File inputFile = new File(inputDir,String.valueOf(caseNum));
		File expectedFile = new File(expectedDir,String.valueOf(caseNum));
		Map<String, File> files = new HashMap<String, File> ();
		files.putIfAbsent(KEY_REGULAR_INPUT_FILE, inputFile);
		files.putIfAbsent(KEY_EXPECTED_OUTPUT_FILE, expectedFile);
		return files;
	}

	/**
	 * root 폴더를 지정함
	 * @param Dir 지정할 rootDir
	 */
	@Override
	public void setRootDir(File dir) { 
		this.rootDir = dir;
		
	}

	/**
	 * 도전과제의 폴더를 생성함
	 * @param cNum
	 */
	@Override
	public File makeChallengeDir(long cNum) {
		File dir = null;
		dir = new File(this.rootDir,String.valueOf(cNum));
		logger.info(dir.toString());
		dir.mkdir();
		logger.info(String.valueOf(dir.exists()));
		File inputDir = new File(dir,NAME_INPUT_DIR);
		inputDir.mkdir();
		File expectedDir = new File(dir,NAME_EXPECTED_OUTPUT_DIR);
		expectedDir.mkdir();
		return  dir;
	}

	/**
	 * 해당 케이스가 가능한지
	 * @param caseNum 케이스 번호
	 * @return 가능하면 true
	 */
	@Override
	public boolean isAvailableCaseFile(long caseNum) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 해당 도전과제가 가능한 지
	 * @param cNum 도전과제 번호
	 * @return 가능하면 true
	 */
	@Override
	public boolean isAvailableChallengeFile(long cNum) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 최신버전의 case 파일인지
	 * @param caseNum
	 * @return
	 */
	@Override
	public boolean isLatestCaseFile(long caseNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public File getChallengeDir(long ChallengeNum) {
		File challengeDir = new File(rootDir,String.valueOf(ChallengeNum));
		return challengeDir;
	}

	
}
