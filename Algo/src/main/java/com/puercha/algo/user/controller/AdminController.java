package com.puercha.algo.user.controller;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.puercha.algo.user.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final static Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Inject
	AdminService adminManger;
	
	@GetMapping(path="/application-list")
	String getApplicationList(
		@RequestParam(name = "page",required = false, defaultValue = "1" ) long page,
		Model model
			) {
		
		Map<String,Object> datas = adminManger.getApplicationList(page);
		model.addAttribute("datas",datas);
		return "/admin/tutorApplicationList";
	
	}
	
	
	
	@PutMapping(path="/application/aproval")
	ResponseEntity<String> commitApproval(
		@RequestBody Map<String,Object> requestParams
			){
		logger.info("requestParams:"+requestParams);
		ResponseEntity<String> res = null;
		long applicationNum = 0 ;
		if ( requestParams.containsKey("applicationNum")) {
			applicationNum = Long.valueOf((String) requestParams.get("applicationNum"));
		}
		char approval = 'p';
		if(  requestParams.containsKey("approval")) {			
			approval = ((String) requestParams.get("approval")).charAt(0);
		}
		int result = 0;
		result = adminManger.commitApproval(applicationNum, approval );
		if(result !=0)
			res = new ResponseEntity<String>("success",HttpStatus.OK);
		else 
			res = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		return res;
	}
}
