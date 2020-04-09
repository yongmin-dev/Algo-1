package com.puercha.algo.learning.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.http.converter.json.*;

import com.puercha.algo.learning.service.LearningService;
import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizVO;
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
		UserVO user = (UserVO) session.getAttribute("user");
		logger.info("learning Controller 작동");

		// 과목 목록 불러오기
		model.addAttribute("subjectList", learningService.subjectList(reqPage, searchType, keyword));
		// 페이지 제어
		model.addAttribute("pm", learningService.getPageManager(reqPage, searchType, keyword));

		return "/learning/subjectList";
	}


	// 과목열기
	@GetMapping("/subject/{subjectNum}")
	public String unitList(@PathVariable String subjectNum, HttpSession session, Model model) {
		model.addAttribute("unitList", learningService.unitList(subjectNum));

		return "/learning/unitContent";
	}

	// 단원열기
	@GetMapping("/unit/{subjectNum}/{unitNum}")
	public String viewUnit(@PathVariable String unitNum, @PathVariable String subjectNum, HttpSession session, Model model) {
		
		model.addAttribute("unitList", learningService.unitList(subjectNum));
		Map<String, Object> map = learningService.viewUnit(unitNum);
		UnitVO unitVO = (UnitVO) map.get("UnitVO");
		model.addAttribute("unitVO", unitVO);
		return "/learning/unitContent";
	}

	// 마무리문제풀기화면
	@GetMapping("/quiz/{unitNum}")
	public String viewQuiz(@PathVariable String unitNum, HttpSession session, Model model) {

		model.addAttribute("quizList", learningService.viewQuiz(unitNum));

		return "/learning/quiz";

	}

	// 마무리 문제 정답확인
	@PostMapping("/quiz/{unitNum}/check")
	public String checkAnswers(@PathVariable String unitNum, HttpSession session, Model model,
			QuizAnswerVO quizAnswerVO) {

//		model.addAttribute("quizAnswer", learningService.)

		
		return null;

	}

}
