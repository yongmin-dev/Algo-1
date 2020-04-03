package com.puercha.algo.challenge.service.codelauncher;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.puercha.algo.challenge.vo.ChallengeResultVO;

public interface CodeTester {
	/**
	 * 객체를 초기화함
	 */
	void init ();
	void prepareExecution();
	Map<String,Object> testOneCase(List<String> commandList,File input, File output, File expected, long memoryLimit, long timeLimit);
	File compile(long resultNum, File dir, String code);
	void doTest(ChallengeResultVO result);

}
