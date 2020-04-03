package com.puercha.algo.learning.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.puercha.algo.learning.service.LearningService;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;
import com.puercha.algo.user.service.LoginService;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping("/learning")
public class LearningController {

	private static final Logger logger = LoggerFactory.getLogger(LearningController.class);

	@Inject
	LearningService learningService;
	@Inject
	LoginService loginService;

	// 이론학습메인(과목조회)
	@GetMapping({ "/list", "/list/{reqPage}", "/list/{searchType}/{keyword}", "list/{reqPage}/{searchType}/{keyword}" })
	public String learningList(@PathVariable(required = false) String reqPage,
			@PathVariable(required = false) String searchType, @PathVariable(required = false) String keyword,
			HttpSession session, Model model) {
//		UserVO user = (UserVO) session.getAttribute("user");
		logger.info("learning Controller 작동");

		// 과목 목록 불러오기		
		model.addAttribute("subjectList", learningService.subjectList(reqPage, searchType, keyword));
//		logger.info("list : " + model.getAttribute("list"));
		// 페이지 제어
		model.addAttribute("pm", learningService.getPageManager(reqPage, searchType, keyword));

		return "/learning/subjectList";
	}
	
	
	//과목열기
	@GetMapping("/subject/{subjectNum}")
	public String unitList(@PathVariable String subjectNum, HttpSession session, Model model) {
		model.addAttribute("unitList", learningService.unitList(subjectNum));
			
		
		return "/learning/unitContent";
	}
	
	
	//단원열기
	@GetMapping("/unit/{unitNum}")
	public String viewUnit(@PathVariable String unitNum, HttpSession session, Model model) {
		
		Map<String, Object> map = learningService.viewUnit(unitNum);
		UnitVO unitVO = (UnitVO)map.get("UnitVO");
		model.addAttribute("unitVO", unitVO);		
		return "/learning/unitContent";
	}
	

}
