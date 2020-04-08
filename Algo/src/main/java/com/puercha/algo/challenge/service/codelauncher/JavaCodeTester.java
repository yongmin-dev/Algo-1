package com.puercha.algo.challenge.service.codelauncher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.puercha.algo.challenge.dao.ChallengeDAO;
import com.puercha.algo.challenge.vo.ChallengeCaseVO;
import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.challenge.vo.ChallengeVO;
import com.puercha.algo.common.psapi.PROCESS_MEMORY_COUNTERS_EX;
import com.puercha.algo.common.psapi.PsapiExt;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

@Service
@EnableAsync
public class JavaCodeTester implements CodeTester {
	private static final Logger logger = LoggerFactory.getLogger(JavaCodeTester.class);
	static final String DIR_NAME_RESULTS="results";
	
	public static final String KEY_PROCESSING_NANO_TIME = "nanoTime";
	public static final String KEY_MEMORY_USAGE= "memoryUsage";
	public static final String KEY_PASSES_LIMIT_MEMORY = "passesLimitMemory";
	public static final String KEY_PASSES_LIMIT_TIME = "passesLimitTime";
	public static final String KEY_MATCHED = "matched";
	public static final String KEY_PASSES_CASE = "passes";
//	MemoryMonitor memoryMonitor = new MemoryMonitor();
//	@Inject
//	MemoryMonitor memoryMonitor ;
	
	@Inject
	CaseFileService caseFileManager;
	
	@Inject
	ChallengeDAO challengeDAO;
	
	// 초기화
	@Override
	public void init () {
		// 모든 케이스가 준비되었는지 확인
		//
	}
	
	
	@Override
	public void prepareExecution() {
		
	}


	// 코드 컴파일
	public File compile(long resultNum, File dir, String code) {
		
		// 소스코드 쓰기
		String sourceCodePath = null;
		if(dir==null) {
			dir = new File(caseFileManager.getRootDir(),DIR_NAME_RESULTS);
		}
		File sourceCodeDir = new File(dir,String.valueOf(resultNum));
		if(!sourceCodeDir.exists()) {
			sourceCodeDir.mkdirs();
		}
		File sourceCodeFile = new File(sourceCodeDir,"Main.java");
		try {
			FileWriter fileWriter = new FileWriter(sourceCodeFile);
			fileWriter.write(code);
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sourceCodePath = sourceCodeFile.getAbsolutePath();		
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
		.getJavaFileObjectsFromStrings(Arrays.asList(sourceCodePath));
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
				compilationUnits);
		Boolean success = task.call();
		StringBuilder sb = new StringBuilder();
		// 컴파일 관련 메시지
		for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
			sb.append(String.format("Code: %s%n" + "Kind: %s%n" + "Position: %s%n" + "Start Position: %s%n"
							+ "End Position: %s%n" + "Source: %s%n" + "Message:  %s%n", diagnostic.getCode(),
							diagnostic.getKind(), diagnostic.getPosition(), diagnostic.getStartPosition(),
							diagnostic.getEndPosition(), diagnostic.getSource(), diagnostic.getMessage(null)
			));
		}
		try {
			fileManager.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info(String.format("result(%d): %b",resultNum,success ));		
		return sourceCodeDir;
	}
	
	@Async
	@Override
	//테스트 시작
	public void doTest(ChallengeResultVO result) {		
		// 컴파일 시작
		logger.info("doTest(ChallengeResultVO result) ");
		result.setStatus('C'); // 상태 변경
		result.setResultComment("Compiling");
		challengeDAO.updateChallengeResult(result);
		ChallengeVO challengeInfo = challengeDAO.selectOne(result.getCNum()); // 도전과제 VO
		long limitTime = challengeInfo.getLimitTime();
		long limitMemory = challengeInfo.getLimitMemory();
		long usedMemory = -1;
		long processingTime = -1;
		File sourceDir = compile(result.getResultNum(),null,result.getCode()); // 소스코드 위치
		
		File challengeDir = caseFileManager.getChallengeDir(result.getCNum()); // 도전과제 위치
		
		// 테스트 시작
		List<ChallengeCaseVO> list = challengeDAO.selectAllCaseMetaDatas(result.getCNum());
		logger.info("ChallengeCaseVO list:"+list.toString());
		Map<String,Object> failedResult = null;
		
		for(int i=0;i<list.size();i++) {
			ChallengeCaseVO caseMeta = list.get(i);
			caseFileManager.prepareCaseFile(caseMeta.getCNum(), caseMeta.getCaseNum());// 케이스 파일 준비
			// 출력 파일 만들기
			File output = new File(sourceDir,CaseFileService.NAME_OUTPUT_FILE);
			
			// case파일을 가져옴
			Map<String, File> caseFiles = 
					caseFileManager.getCaseFile(challengeDir,caseMeta.getCaseNum());
			File expected = caseFiles.get(CaseFileService.KEY_EXPECTED_OUTPUT_FILE);
			File input = caseFiles.get(CaseFileService.KEY_REGULAR_INPUT_FILE);
			// command 설정하기
			List<String> commandList = new ArrayList<String>();
			commandList.add("java");
			commandList.add("-classpath");
			commandList.add("\""+sourceDir.getAbsolutePath()+"\"");
			commandList.add("Main");
			
			logger.info("input exists:"+input.exists()+" "+input);
			Map<String,Object> caseResult = testOneCase(commandList,input, output, expected,limitMemory,limitTime);
			 
			boolean isPassed = (caseResult.get(KEY_PASSES_CASE)!=null) ? (boolean)caseResult.get(KEY_PASSES_CASE) : false;
			if(!isPassed) {
				failedResult  = caseResult;
				break;
			}
			// 테스트 도중 상태 
			result.setStatus('T');
			logger.info("percentage:"+String.format("%.2f %%", 100.0*(i+1)/list.size()));
			result.setResultComment(String.format("%.2f %%", 100.0*(i+1)/list.size()));
//			logger.info(result.toString());
			challengeDAO.updateChallengeResult(result);
			
			logger.info(String.format(
					"Matched: %b, Passes case: %b, "
					+ "Passes LimitTime: %b, Passes Limit Memory: %b, "
					+ "Memory Usage: %d %n", 
					caseResult.get(KEY_MATCHED),
					caseResult.get(KEY_PASSES_CASE),
					caseResult.get(KEY_PASSES_LIMIT_TIME),
					caseResult.get(KEY_PASSES_LIMIT_MEMORY),
					caseResult.get(KEY_MEMORY_USAGE)
					));
			usedMemory = Math.max(usedMemory,(long)caseResult.get(KEY_MEMORY_USAGE));
			processingTime = Math.max(processingTime,(long)caseResult.get(KEY_PROCESSING_NANO_TIME));
			
			
		}
		if(failedResult!=null) {
			result.setStatus('F');
			result.setResultComment("실패하였습니다~");
			logger.info(failedResult.toString());
		}else {
			result.setStatus('S');
			result.setResultComment("성공");
		}
		result.setProcessingTime(processingTime);
		result.setUsedMemory(usedMemory);
		challengeDAO.updateChallengeResult(result);		
		logger.info("완료");
	}


	// case하나를 테스트함
	@Override
	public Map<String,Object> testOneCase(List<String> commandList,File input, File output, File expected, long memoryLimit, long timeLimit) {
//		long memoryLimit = 500000; // 임시
//		long timeLimit = 2000; // 임시
		
		Map<String,Object> result = new HashMap<String, Object>();// 결과 저장
		long startNano = System.currentTimeMillis();// 시작 시간
		ProcessBuilder pb = new ProcessBuilder();		
		pb.command(commandList);		
		logger.info("command list:"+pb.command());
		pb.redirectInput(input);
		pb.redirectError(Redirect.PIPE);
		pb.redirectOutput(output);
		Process process = null;
		boolean passesLimitTime = false;
		boolean passesLimitMemory = false;
		boolean matched = false;
		int pid = -1;
		String errMsg = null;
		boolean passes = false;
		long memoryUsage = 0;
		try {
			
			process = pb.start();
//			pid = getPid(process); // pid를 가져옴		
			
			passesLimitTime = process.waitFor(timeLimit,TimeUnit.MILLISECONDS);			
			errMsg = getStringFromStream(process.getErrorStream());
			logger.info("errMsg:"+errMsg);
			if(!passesLimitTime) {
				process.destroy();	
				
			}else {				
				logger.info("process exec:"+process.exitValue());
				memoryUsage = getMemory(process);				
				if(memoryUsage>memoryLimit*1024*1024) {
					passesLimitMemory = false;
				}else {
					passesLimitMemory = true;
				}
				matched = checkDiffFiles(expected, output);
				if(matched) {
					passes = true;
				}
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		long endNano = System.currentTimeMillis(); // 끝 시작
		result.put(KEY_PROCESSING_NANO_TIME, endNano - startNano);
		result.put(KEY_MEMORY_USAGE,memoryUsage);
		result.put(KEY_PASSES_LIMIT_MEMORY, passesLimitMemory);
		result.put(KEY_MATCHED, matched);
		result.put(KEY_PASSES_LIMIT_TIME, passesLimitTime);
		result.put(KEY_PASSES_CASE,passes);
		if(errMsg != null) {
			result.put("errMsg",errMsg );			
		}
		return result; 
	}
	
	
	// 실행결과의 output 테스트함
	boolean checkDiffFiles(File expected, File actual) {
		Scanner expectedScan = null;
		Scanner actualScan = null;
		boolean result = true;
		try {
			expectedScan = new Scanner(expected);
			actualScan= new Scanner(actual);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		while(expectedScan.hasNextLine()&& actualScan.hasNextLine()) {
			String actualLine = actualScan.nextLine();
			String expectedLine = expectedScan.nextLine();
			if(!actualLine.equalsIgnoreCase(expectedLine)) {
				result = false;
				break;
			}		
		} // 무사히 통과하면 전부 일치함
		
		if(expectedScan.hasNextLine() || actualScan.hasNextLine()) { // 남은 줄이 있다면 불일치
			result = false;			
		}
		expectedScan.close();
		actualScan.close();		
		return result;
	}
	/**
	 * input스트림의 내용을 string으로 바꿈
	 * @param inputStream
	 * @return
	 */
	public String getStringFromStream(InputStream inputStream) {
		InputStreamReader inputReader;
		String result = null;
		try {
			inputReader = new InputStreamReader(inputStream,"MS949");
			char inputBuf[] = new char[1024];
			int nRead = 0;
			StringBuilder sb=  new StringBuilder();
			try {
				while(inputReader.ready()) {
					nRead  = inputReader.read(inputBuf);
					sb.append(inputBuf,0,nRead);
				}			
				result = sb.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	// pid가져오기 
	public int getPid(Process pc) {
		Field f = null;
		try {
			f = pc.getClass().getDeclaredField("handle");
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    f.setAccessible(true);
	    long handLong=0;
		try {
			handLong = f.getLong(pc);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Kernel32 kernel = Kernel32.INSTANCE;

	    WinNT.HANDLE handle = new WinNT.HANDLE();

	    handle.setPointer(Pointer.createConstant(handLong));

	    int pid = kernel.GetProcessId(handle);
	    return pid;
	}
	
	// 프로세스의 사용 메모리를 가져온다.
	static long getMemory(Process p){
		
		Field f = null;
		try {
			f = p.getClass().getDeclaredField("handle");
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    f.setAccessible(true);
	    long handLong=0;
		try {
			handLong = f.getLong(p);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Kernel32 kernel = Kernel32.INSTANCE;

	    WinNT.HANDLE handle = new WinNT.HANDLE();

	    handle.setPointer(Pointer.createConstant(handLong));
	    PROCESS_MEMORY_COUNTERS_EX processMemoryCountersEx = new PROCESS_MEMORY_COUNTERS_EX();
	    long dProcessMemory = 0;
    	if(PsapiExt.INSTANCE.GetProcessMemoryInfo(handle, processMemoryCountersEx, processMemoryCountersEx.size()))
    	{
    			dProcessMemory = (processMemoryCountersEx.PeakPagefileUsage.longValue()) ; // 0 or -1

    	}
		return dProcessMemory;
	}
}
