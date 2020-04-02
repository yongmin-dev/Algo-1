package com.puercha.algo.challenge.service;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.puercha.algo.challenge.dao.ChallengeDAO;
import com.puercha.algo.challenge.service.codelauncher.CaseFileService;
import com.puercha.algo.challenge.vo.ChallengeCaseVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")

public class CaseFileManagerTest {
	final static Logger logger = LoggerFactory.getLogger(CaseFileManagerTest.class);
	@Inject
	@Qualifier("caseFileManager")
	CaseFileService caseFileService;
	
	@Inject
	ChallengeDAO challengeDAO;
	
	@Test
	@Disabled
	void testWritingFile() {
		String rootPath = File.separator;
		
		File dirFile = new File(rootPath,"test");		
		if(!dirFile.exists())
			dirFile.mkdir();
		
		File newFile = caseFileService.writeFile("System.out.println", dirFile, "hello");
		System.out.println(newFile);		
	}
	
	@Test
	@Disabled
	void testMakeChallengeDir() {
		caseFileService.makeChallengeDir(10114);		
	}
	
	@Test
	@Disabled
	@DisplayName("")
	void testWriteCaseFile() {
		List<ChallengeCaseVO> list = challengeDAO.selectAllCaseMetaDatas(21);
		for(ChallengeCaseVO challengeCase: list) {
			logger.info("case:"+challengeCase);
			caseFileService.writeCaseFile(challengeCase);
		}
		
	}
	
}

