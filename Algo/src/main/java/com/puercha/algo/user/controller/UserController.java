package com.puercha.algo.user.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.puercha.algo.common.Code;
import com.puercha.algo.user.service.UserService;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping("/userNum")
public class UserController {

	private static final Logger logger
		= LoggerFactory.getLogger(UserController.class);
	
	@Inject
	UserService userService;
	
	@ModelAttribute
	public void UserData(Model model) {
	//지역
	
	//성별
	List<Code> gender = new ArrayList<>();
	gender.add(new Code("남","남자"));
	gender.add(new Code("여","여자"));
	model.addAttribute("gender",gender);
	}
	//사용자생성 양식
	@RequestMapping("/signupForm")
	public String UserJoinForm(Model model) {
		model.addAttribute("uvo",new UserVO());
		return "user/signupForm";
	}
	//사용자등록
	@RequestMapping("/join")
	public String userJoin(
			@Valid @ModelAttribute("uvo") UserVO userVO,
			BindingResult result,
			Model model) {
		logger.info(userVO.toString());
		
		//1)유효성 오류체크 중 오류가 발견되면 회원가입 페이지로 이동
		if(result.hasErrors()) {
			return "user/signupForm";
		}
		//2)회원 중복체크
		if(userService.selectUser(userVO.getEmail()) != null) {
			model.addAttribute("svr_msg","중복된 아이디입니다!");
			return "user/signupForm";
		}
		//3)회원 가입처리
		int cnt = userService.joinUser(userVO);
		if(cnt == 1) {
			return "user/siginForm";
		}else {
			return "redirect:/";
		}
	}
	//사용자수정 양식
	@GetMapping("/profileForm")
	public String profileForm(@PathVariable String email, Model model) {
		
		//1) 현재 로그인한 사용자정보 읽어오기
		UserVO userVO = userService.selectUser(email);
		logger.info("userVO:" + userVO);
		//비밀번호 제거
		userVO.setPw(null);
		model.addAttribute("uvo",userVO);
	
		return "user/profileForm";
	}
	//사용자수정
	@PostMapping("/profile")
	public String profile(
			@Valid @ModelAttribute("uvo") UserVO userVO,
			BindingResult result,
			HttpSession session,
			Model model) {
		logger.info("/profile호출됨!");
		//유효성 체크
		if(result.hasErrors()) {
			logger.info(result.getAllErrors().toString());
			//비밀번호 제거
			userVO.setPw(null);
			return "user/profileForm";
		}		
		//사용자정보 수정
		int cnt = userService.modifyUser(userVO);
		logger.info("수정처리결과 :"+ cnt);
		
		//세션정보 수정
		session.removeAttribute("user");
		session.setAttribute("user",userVO);
		return "redirect:/user/profileForm"+userVO.getEmail();
	}
	//사용자 탈퇴 처리
	@PostMapping("/out")
	public String out(
			@RequestParam("email") String email,
			@RequestParam("pw") String pw,
			HttpSession session, Model model) {
		int cnt = userService.outUser(email, pw);
		if(cnt == 1) {
			session.invalidate();
			return "redirect:/";
		}
		model.addAttribute("svr_msg", "비밀번호가 잘못되었습니다!");
		return "user/outForm";
	}
	//비밀번호변경 화면
	@GetMapping("/findPWForm")
	public String findPWForm() {
		return "user/findPWForm";
	}
	//비밀번호 변경
	@PostMapping(value="/changePW", produces ="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<Map> chagePW(
			@RequestParam("userNum") long userNum,
			@RequestParam("oldPW") String oldPw,
			@RequestParam("newPW") String newPw			
			){
		ResponseEntity<Map> res =null;
		
		int cnt = userService.changePW(userNum, oldPw, newPw);
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		if(cnt == 1) {
			map.put("success",true);
			res = new ResponseEntity<Map>(map, HttpStatus.OK);
		}else {
			map.put("success",false);
			res = new ResponseEntity<Map>(map, HttpStatus.OK);
		}		
		return res;
	}
}
