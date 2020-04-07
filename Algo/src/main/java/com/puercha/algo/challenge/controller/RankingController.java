package com.puercha.algo.challenge.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.puercha.algo.challenge.service.ChallengeService;
import com.puercha.algo.challenge.vo.ChallengeResultVO;
import com.puercha.algo.common.PageManager;
import com.puercha.algo.user.service.LoginService;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping("/ranking")
public class RankingController {
	private final static Logger logger = LoggerFactory.getLogger(RankingController.class);
	@Inject
	ChallengeService challengeService;
	
	@Inject
	LoginService loginService;
		
	
	@GetMapping("/{cNum}/{page}")
	public String getRankingPage(
		@PathVariable(value="cNum") long cNum,
		@PathVariable(value="page") long page,
		@RequestParam(value="type",required = true, defaultValue = "T") String type,
		HttpSession session,
		Model model
			){
		UserVO user = loginService.getLoggedInUser(session);
		logger.info(String.format("cNum: %d, page: %d, type: %s", cNum,page,type));
		// 랭킹 리스트 출력
		Map<String,Object> datas = challengeService.getRankingList(page,cNum,type);
		List<ChallengeResultVO> list = (List<ChallengeResultVO>) datas.get("list");
		PageManager pageInfo = (PageManager) datas.get("pageInfo");
		model.addAttribute("list", list);// 랭킹 리스트
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("cNum", cNum);
		model.addAttribute("type", type);
		model.addAttribute("curPage", page);
		ChallengeResultVO myBestResult = challengeService.getMyRank(cNum, user.getUserNum(), type);
		model.addAttribute("myBestResult",myBestResult );
		return "challenge/ranking";
	}
	
	// 사용안함
//	@GetMapping(path="/{cNum}/{type}",produces = "application/json;charset=UTF-8")
	public ResponseEntity<Map<String,Object>> getUserRanking(
			@PathVariable(value="cNum") long cNum,
			@PathVariable(value="type") String type,
			HttpSession session
			){
		UserVO user = loginService.getLoggedInUser(session);
		logger.info(String.format("cNum: %d, userNum: %d, type: %s", cNum,user.getUserNum(),type));
		ResponseEntity<Map<String,Object>>  res = null;
//		challengeService.getRanking(cNum,user.getUserNum(),type);
//		res = new ResponseEntity<Map<String,Object>>();
		
		
		return res ;
	}
	
	
}
