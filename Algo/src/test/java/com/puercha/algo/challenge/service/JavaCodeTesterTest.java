package com.puercha.algo.challenge.service;

import java.io.File;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.puercha.algo.challenge.dao.ChallengeDAO;
import com.puercha.algo.challenge.service.codelauncher.CodeTester;
import com.puercha.algo.challenge.service.codelauncher.MemoryMonitor;
import com.puercha.algo.challenge.vo.ChallengeResultVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class JavaCodeTesterTest {
	private static final Logger logger = LoggerFactory.getLogger(JavaCodeTesterTest.class);
	
//	@Inject
	MemoryMonitor monitor ;
	
	@Inject
	ChallengeDAO challengeDAO;
	
	String code = "import java.util.Scanner;\r\n" + 
			"\r\n" + 
			"public class Main {\r\n" + 
			"	public static void main(String[] args) {\r\n" + 
			"		Scanner sc = new Scanner(System.in);\r\n" + 
			"		int a = sc.nextInt();\r\n" + 
			"		int b = sc.nextInt();\r\n" + 
			"		try {\r\n" + 
			"			Thread.sleep(1000);\r\n" + 
			"		} catch (InterruptedException e) {\r\n" + 
			"			// TODO Auto-generated catch block\r\n" + 
			"			e.printStackTrace();\r\n" + 
			"		}\r\n" + 
			"		System.out.println(a+b);\r\n" + 
			"		\r\n" + 
			"	}\r\n" + 
			"}\r\n" ;
	
	@Inject
	CodeTester javaCodeTester;
	
	@Test
	@Disabled
	public void testCompile() {
		File file = new File("D:\\\\code");
		logger.info("file exists:" + file.exists());
//		CodeTester javaCodeTester = new JavaCodeTester(2000,2000000);
		javaCodeTester.compile(0,file, code);		
	}
	
	@Test
	public void testDoTest() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ChallengeResultVO result = new ChallengeResultVO();
		result.setCNum(21);
		result.setCode(code);
		result.setUserNum(1);
		result.setProcessingTime(-1);
		result.setUsedMemory(-1);
		result.setStatus('p');
		result.setResultComment("pending");		
		challengeDAO.insertChallengeResult(result);
		
				
		javaCodeTester.doTest(result);
	}
	
	
}
