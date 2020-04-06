package com.puercha.algo.content.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.puercha.algo.challenge.vo.ChallengeCaseVO;
import com.puercha.algo.challenge.vo.ChallengeVO;
import com.puercha.algo.content.service.ContentManagingService;
import com.puercha.algo.user.service.LoginService;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping(value = "/content/challenge")
public class ChallengeContentContoller {
	private static final Logger logger = LoggerFactory.getLogger(ChallengeContentContoller.class);
	
	@Inject
	LoginService loginService;
	@Inject
	ContentManagingService contentManager;
	// 도전과제 관리 화면
	@GetMapping
	public String getChallengeManagingPage() {
		// tutor회원만 가능하도록
		return "content/challengeContentManger";
	}
	
	// 도전과제 리스트
	@GetMapping(path="/list",produces = "application/json")
	public ResponseEntity<List<ChallengeVO>> getChallengeList(
		HttpSession session
			) {
		ResponseEntity<List<ChallengeVO>> res = null;
		
		UserVO sessionUser = loginService.getLoggedInUser(session);
		List<ChallengeVO> list = contentManager.getChallengeList(sessionUser.getUserNum());
		
		if(list != null) {
			res = new ResponseEntity<List<ChallengeVO>>(list,HttpStatus.OK);			
		}else {
			res = new ResponseEntity<List<ChallengeVO>>(list,HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	
	// 빈 도전과제 생성
	@PostMapping(path="/new", produces="application/json")
	public ResponseEntity<Map<String,Object>> createNewChallenge(
		HttpSession session
			){
		ResponseEntity<Map<String,Object>> res = null;
		UserVO sessionUser = loginService.getLoggedInUser(session);
		logger.info("sessionUser:"+sessionUser);
		long newChallengeNum = contentManager.createEmptyChallenge(
				sessionUser.getUserNum(),
				sessionUser.getUsername()
				);
		Map<String,Object> body = new HashMap<String,Object> ();
		body.put("newChallengeNum",  newChallengeNum );
		res = new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
		return res;
	}
	
	// 도전과제 삭제
	@DeleteMapping(path="/{cNum}", produces="application/json")
	public ResponseEntity<Map<String,Object>> deleteChallenge(
			@PathVariable(name = "cNum") long cNum,
			HttpSession session
			){
		ResponseEntity<Map<String,Object>> res = null;
		Map<String,Object> body = new HashMap<String,Object> ();
		UserVO sessionUser = loginService.getLoggedInUser(session);
		int result = contentManager.deleteChallenge(cNum, sessionUser.getUserNum());
		if(result!=1) {
			
			res = new ResponseEntity<Map<String,Object>>(HttpStatus.UNAUTHORIZED);
		}else {
			body.put("msg","success");
			res = new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
		}
		
		return res;
	}
	
	
	// 도전과제 제목 수정
	@PutMapping(path="/{cNum}",consumes = "application/json", produces="application/json")
	public ResponseEntity<Map<String,Object>> updateChallengeTitle(
			@RequestBody Map<String,Object> requestBody, 
			@PathVariable(name = "cNum") long cNum,
			HttpSession session
			){
		ResponseEntity<Map<String,Object>> res = null;
		Map<String,Object> body = new HashMap<String,Object> ();
		UserVO sessionUser = loginService.getLoggedInUser(session);
		if(sessionUser==null) {
			return new ResponseEntity<Map<String,Object>>(HttpStatus.UNAUTHORIZED);
		}
		String title = (String)requestBody.get("title");
		logger.info("title:"+title);
		int result = contentManager.updateChallengeTitle(cNum, title, sessionUser.getUserNum());
		if(result!=1) {			
			res = new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}else {
			body.put("msg","success");
			res = new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
		}		
		return res;
	}
	
	// 도전과제 편집화면
	@GetMapping("/editor/{cNum}")
	public String getChallengeEditorPage(
		@PathVariable(name = "cNum") long cNum,
		HttpSession session,
		Model model
			) {		
		ChallengeVO challenge = contentManager.getChallengeContent(cNum);
		model.addAttribute("challenge", challenge);
		return "content/challengeContentEditor";
	}

	// 도전과제 편집 적용
//	@PutMapping(path="/edit",consumes = "application/json" , produces = "application/json" )
	@PostMapping(path="/edit" )
//	public ResponseEntity<Map<String,Object>> updateChallengeTitle(
	public String updateChallengeTitle(
//			@RequestBody ChallengeVO challenge,
			@ModelAttribute("challenge") ChallengeVO challenge, 
			HttpSession session		
			){
		ResponseEntity<Map<String,Object>> res = null;
		Map<String,Object> body = new HashMap<String,Object> ();
		int result = contentManager.updateChallenge(challenge);
		return "redirect:/content/challenge";
	}
	
	// 테스트케이스 리스트 
	@GetMapping(path="/case/list",produces = "application/json")
	public ResponseEntity<List<ChallengeCaseVO>> getCaseList(
		@RequestParam(name = "cNum",required = true) long cNum
			){
		ResponseEntity<List<ChallengeCaseVO>> res = null;
		List<ChallengeCaseVO> list = contentManager.getCaseList(cNum);
		logger.info("cNum:"+cNum);
		res = new ResponseEntity<List<ChallengeCaseVO>>(list, HttpStatus.OK);
		return res;
	}
	// 테스트케이스 내용
	@GetMapping(path="/case/{caseNum}", produces="application/json")
	public ResponseEntity<ChallengeCaseVO> getCase(
		@PathVariable(name = "caseNum") long caseNum
			){
		ResponseEntity<ChallengeCaseVO> res = null;
		ChallengeCaseVO challengeCase = contentManager.getCase(caseNum);
		logger.info("caseNum:"+caseNum);
		res = new ResponseEntity<ChallengeCaseVO>(challengeCase, HttpStatus.OK);
		return res;
	}
	
	// 빈 케이스 생성
	@PostMapping(path="/case/new", produces="application/json")
	public ResponseEntity<Map<String,Object>> createNewCase(
		@RequestParam(name = "cNum",required = true) long cNum,
		HttpSession session
			){
		ResponseEntity<Map<String,Object>> res = null;
		UserVO sessionUser = loginService.getLoggedInUser(session);
		logger.info("createNewCase():"+cNum);
		long newCaseNum = contentManager.createEmptyCase(cNum,sessionUser.getUserNum());
		Map<String,Object> body = new HashMap<String,Object> ();
		body.put("newCaseNum ",  newCaseNum );
		res = new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
		return res;
	}
	
	// 케이스 삭제
	@DeleteMapping(path="/case/{caseNum}", produces="application/json")
	public ResponseEntity<Map<String,Object>> deleteCase(
			@PathVariable(name = "caseNum") long caseNum,
			HttpSession session
			){
		ResponseEntity<Map<String,Object>> res = null;
		Map<String,Object> body = new HashMap<String,Object> ();
//		UserVO sessionUser = loginService.getLoggedInUser(session);
		int result = contentManager.deleteCase(caseNum);
		if(result!=1) {
			
			res = new ResponseEntity<Map<String,Object>>(HttpStatus.UNAUTHORIZED);
		}else {
			body.put("msg","success");
			res = new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
		}
		
		return res;
	}
	
	
	// 케이스 수정
	@PutMapping(path= {"/case/{caseNum}","/case"}, consumes = "application/json", produces="application/json")
	public ResponseEntity<Map<String,Object>> updateCase(
			@RequestBody Map<String,Object> challengeCase,
			HttpSession session
			){
		ResponseEntity<Map<String,Object>> res = null;
		Map<String,Object> body = new HashMap<String,Object> ();
//		UserVO sessionUser = loginService.getLoggedInUser(session);
		logger.info("updateCase():"+challengeCase);
		int result = 0; 
		result = contentManager.updateCase(
				Long.valueOf((String)challengeCase.get("caseNum")),				
				(String)challengeCase.get("input"),
				(String)challengeCase.get("output")
				);
		if(result!=1) {			
			res = new ResponseEntity<Map<String,Object>>(HttpStatus.UNAUTHORIZED);
		}else {
			body.put("msg","success");
			res = new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
		}		
		return res;
	}
}
