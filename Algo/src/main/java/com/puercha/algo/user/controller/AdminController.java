package com.puercha.algo.user.controller;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.puercha.algo.user.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final static Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Inject
	AdminService amdinManger;
	@GetMapping(path="/application-list")
	String getApplicationList(
		@RequestParam(name = "page",required = false, defaultValue = "1" ) long page,
		Model model
			) {
		
		Map<String,Object> datas = amdinManger.getApplicationList(page);
		model.addAttribute("datas",datas);
		return "/admin/tutorApplicationList";
	}
}
