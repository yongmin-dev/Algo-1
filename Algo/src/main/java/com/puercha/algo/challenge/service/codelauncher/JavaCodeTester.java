package com.puercha.algo.challenge.service.codelauncher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

//@Service
public class JavaCodeTester implements CodeTester {
//	MemoryMonitor memoryMonitor = new MemoryMonitor();
	@Inject
	MemoryMonitor memoryMonitor ;
	public JavaCodeTester(long timeLimit, long memoryLimit) {
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
	}
	private long timeLimit; // 시간 제한
	private long memoryLimit; // 메모리 제한
	private String code;
	private File compiledProgram;
	// 초기화
	@Override
	public void init () {
		// 모든 케이스가 준비되었는지 확인
		//
	}
	
	
	@Override
	public void preapareExecution() {
		
	}


	// 코드 컴파일
	private void complie() {
		
	}
	// case하나를 테스트함
	@Override
	public Map<String,Object> testOneCase(File input, File output, File expected) {
		
		Map<String,Object> result = new HashMap<String, Object>();// 결과 저장
		long startNano = System.currentTimeMillis();// 시작 시간
		List<String> list = new LinkedList<String>(); // 명령어
		ProcessBuilder pb = new ProcessBuilder();
		list.add("java");
		pb.command(list);		
		pb.redirectInput(input);
		pb.redirectError(Redirect.PIPE);
		pb.redirectOutput(output);
		Process process = null;
		boolean passesLimitTime = false;
		boolean passesLimitMemory = false;
		boolean matched = false;
		int pid = -1;
		String errMsg = null;
		try {
			process = pb.start();
			pid = getPid(process);
			errMsg = getStringFromStream(process.getErrorStream());
			passesLimitTime = process.waitFor(timeLimit,TimeUnit.MILLISECONDS);			
			if(!passesLimitTime) {
				process.destroy();				
			}else {				
				long memoryUsage = memoryMonitor.getMemoryUsage(pid);
				if(memoryUsage>memoryLimit) {
					passesLimitMemory = false;
				}else {
					passesLimitMemory = true;
				}
				matched = checkDiffFiles(expected, output);
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		long endNano = System.currentTimeMillis(); // 끝 시작
		result.put("Time", endNano - startNano);
		result.put("memoryUsage",memoryMonitor.getMemoryUsage(pid));
		result.put("passesLimitMemory", passesLimitMemory);
		result.put("matched ", matched);
		result.put("passesLimitTime", passesLimitTime);
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
		InputStreamReader inputReader = new InputStreamReader(inputStream);
		String result = null;
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
	
}
