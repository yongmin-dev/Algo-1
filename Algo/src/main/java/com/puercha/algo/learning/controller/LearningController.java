package com.puercha.algo.learning.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.puercha.algo.learning.service.LearningService;
import com.puercha.algo.learning.vo.SubjectVO;

@Controller
public class LearningController {

	private static final Logger logger = LoggerFactory.getLogger(LearningController.class);

	@Inject
	LearningService learningService;

	// 이론학습 메인페이지
	@GetMapping("/learning")
	public String learning() {
		return "/learning";
	}

	// 과목 받기
	@ModelAttribute
	public void getSubject(Model model) {
		logger.info("getSubject");
	//	List<SubjectVO> subjectVO = learningService.getSubject();
	//	model.addAttribute("subjectVO", subjectVO);
	}

	
	
}
