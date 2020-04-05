package com.puercha.algo.user.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.puercha.algo.user.service.LoginService;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static final Logger logger
	= LoggerFactory.getLogger(LoginController.class);

	
	
	@Inject
	LoginService loginService;	
	
	//로그인 양식
	@GetMapping("/signing-in")
	public String getLoginForm(
			@RequestParam(value="next",required=false)String next,
			Model model) {
		logger.info("next:"+next);
		if(next == null) {
			next="/";
		}		
		model.addAttribute("next",next);
		
		return "user/signin";
	}
	//로그인 처리
	@PostMapping("/sign-in")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("pw") String pw,
			@RequestParam(value="next", required = false) String next,
			HttpSession session,
			Model model) {
		
		logger.info("email="+email);
		logger.info("pw="+pw);
		logger.info("next="+next);
		
		UserVO user = loginService.loginUser(email, pw, session);
		if(next == null)
			next= "/";
		if(user == null) {
			model.addAttribute("svr_msg","가입된 사용자정보가 없습니다. 비밀번호와 이메일을 확인해주세요");
			return "/user/signin";			
		}else {
			return "redirect:"+next;
		}
	}
	//로그아웃
	@GetMapping("/log-out")
	public String logout(HttpSession session) {
		loginService.logoutUser(session);
//		session.invalidate();
		return "redirect:/";
	}
	
}
