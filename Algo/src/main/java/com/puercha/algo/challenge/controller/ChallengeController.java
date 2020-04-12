package com.puercha.algo.challenge.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puercha.algo.challenge.service.ChallengeService;
import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.challenge.vo.ChallengeVO;
import com.puercha.algo.user.service.LoginService;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping(path = {"/challenge"})
public class ChallengeController {
	private final static Logger logger = LoggerFactory.getLogger(ChallengeController.class);
	public final static String KEY_CHALLENGE_LIST_DATAS = "challengeListDatas";
	@Inject
	ChallengeService challengeService;
	
	@Inject
	LoginService loginService;
	
//	/challenge/list
	@GetMapping(value="/list")
	String getChallengeList(		
		@RequestParam(name = "page", required = false, defaultValue = "1") 
		long page,
		@RequestParam(name = "type", required = false) 
		String type,
		@RequestParam(name = "keyword", required = false)
		String keyword,
		Model model 
			) {
		String logMsg = String.format("request page: %d, type: %s, keyword: %s", page,type,keyword);
		logger.info(logMsg);
		
		Map<String,Object> datas = challengeService.serachChallenges(page, keyword, type);
		model.addAttribute(KEY_CHALLENGE_LIST_DATAS, datas);
		
		return "challenge/challengeList";
	}
	
	
	
//	/challenge/{cNum}
	@GetMapping(value="/{cNum}")
	String getChallengeSolvingForm(
		@PathVariable(name = "cNum") long cNum,
		Model model
			) {
		
		String logMsg = String.format("cNum: %d",cNum);
		logger.info(logMsg);
		// 도전문제 내용 불러오기 
		ChallengeVO challenge =  challengeService.viewChallenges(cNum);
		model.addAttribute("challenge",challenge);
		return "challenge/challengeSolving";
	}
	
	//	/challenge/code/{cNum}
	// 코드 제출
	@PostMapping(path="/code/{cNum}", produces = "application/json")
	@ResponseBody
	ResponseEntity<Map<String,Object>> testCode(
		@PathVariable(name = "cNum") long cNum,
		@RequestParam(value = "code", required = true) String code,
		HttpSession session
			) {
		ResponseEntity<Map<String,Object>> res = null;
		Map<String,Object> datas = new HashMap<String,Object>();
		String logMsg = String.format("cNum: %d code:%s",cNum, code);
		UserVO user = loginService.getLoggedInUser(session);
		// 테스트 실행
		ChallengeResultVO result = challengeService.doTest(cNum, user.getUserNum(), code);
		logger.info(logMsg);
		datas.put("result",result);
		res = new ResponseEntity<Map<String,Object>>(datas,HttpStatus.OK);
		return res;
	}
	
//	/challenge/result/realtime/{resultNum}
	@GetMapping(path="/result/realtime/{resultNum}", produces="text/event-stream; charset=utf-8")
	ResponseEntity<String> getResult(
		@PathVariable(name="resultNum") long resultNum
			){
		ObjectMapper objectMapper= new ObjectMapper();
		
		ResponseEntity<String> res = null;
		ChallengeResultVO result = challengeService.getResult(resultNum);
		String msgFormat = "retry:300%n%ndata:%s%n%n%n";
		String data ="";
		try {
			data = objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		logger.info("result:"+result);
		String resultMsg = String.format(msgFormat, data );
		res = new ResponseEntity<String> (resultMsg,HttpStatus.OK);		
	
		return res;
	}	
	
	@GetMapping("/hotchallenges")
	ResponseEntity<List<Map<String,Object>>> getHotChallenges(
		@RequestParam(name = "num",defaultValue = "5") long num
			){
		ResponseEntity<List<Map<String, Object>>> res = null;
		List<Map<String,Object>> list = challengeService.getHotChalllenges(num);
		res = new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);
		return res;
	}
}
