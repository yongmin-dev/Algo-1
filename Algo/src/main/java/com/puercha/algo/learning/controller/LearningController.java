package com.puercha.algo.learning.controller;

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

import com.puercha.algo.content.service.ContentManagingService;
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
	ContentManagingService contentManager;
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
		List<UnitVO> list = learningService.unitList(subjectNum);
		model.addAttribute("unitList", list);
		if(list.size()>0)
			model.addAttribute("unitVO", list.get(0));
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
		model.addAttribute("unit",learningService.viewUnit(unitNum).get("UnitVO"));
		model.addAttribute("quizList", learningService.viewQuiz(unitNum));

		return "/learning/quiz";

	}

	// 마무리 문제 정답확인
	@PostMapping(path = "/quiz/{quizNum}/check"
//			,consumes = "multipart/form-data"
			,produces = "application/json;charset=UTF-8")
	public ResponseEntity<Map<String,Object>> checkAnswers(
			@PathVariable(name = "quizNum") long quizNum, 
			@RequestParam("quiz-answers") List<Long> answers,
			HttpSession session, 
			Model model
			
			) {
		logger.info("reqBody:"+answers);
//		model.addAttribute("quizAnswer", learningService.)
		UserVO user = loginService.getLoggedInUser(session);
		if(user==null)
			return new ResponseEntity<Map<String,Object>>(HttpStatus.UNAUTHORIZED);
		Map<String,Object> responseBody  = new HashMap<String, Object>();
		ResponseEntity<Map<String,Object>> res = null;
		responseBody  = learningService.checkAnswers(quizNum, answers, user.getUserNum());
		res = new ResponseEntity<Map<String,Object>>(responseBody,HttpStatus.OK);
		return res;

	}
	
	// 과목 이미지
	@GetMapping(path="/subject/image/{subjectNum}")
	public ResponseEntity<byte[]> getSubjectImage(
		@PathVariable(name = "subjectNum") long subjectNum
			){
		ResponseEntity<byte[]> res = null;
		SubjectVO subject = contentManager.getSubject(subjectNum);
		if(subject!=null && 
				subject.getImageSize()>0 && 
				subject.getImageType().startsWith("image") &&
				subject.getImageData().length>0) {
			
			res = new ResponseEntity<byte[]>(subject.getImageData(),HttpStatus.OK);
		}else {
			res = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	

}
