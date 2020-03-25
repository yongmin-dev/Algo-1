package com.puercha.algo.learning.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.puercha.algo.learning.service.LearningService;
import com.puercha.algo.learning.vo.SubjectVO;


@Controller
@RequestMapping("/learning")
public class LearningController {

	private static final Logger logger = LoggerFactory.getLogger(LearningController.class);
	
	//학습 처음페이지 시작
	
	
	@Inject
	LearningService learningService;
	
	
	
	@ModelAttribute
	public void getSubject(Model model) {
		logger.info("게시판 컨트롤러 시작");
		List<SubjectVO> subjectVO = learningService.getSubject();
		model.addAttribute("subjectVO", subjectVO);
	}
	
	
	
}
