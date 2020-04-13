package com.puercha.algo.content.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.puercha.algo.content.service.ContentManagingService;
import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;
import com.puercha.algo.user.service.LoginService;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping("/content/learning")
public class LearningContentController {

	private static final Logger logger = LoggerFactory.getLogger(LearningContentController.class);

	@Inject
	ContentManagingService contentManager;

	@Inject
	LoginService loginService;

	@GetMapping
	public String getChallengeManagingPage() {
		// tutor회원만 가능하도록
		return "content/learningContentManager";
	}

	// 과목 리스트
	@GetMapping(path = "/subject/list", produces = "application/json")
	public ResponseEntity<List<SubjectVO>> getSubjectList(HttpSession session) {
		ResponseEntity<List<SubjectVO>> res = null;

		UserVO sessionUser = loginService.getLoggedInUser(session);
		List<SubjectVO> list = contentManager.getUserSubjectList(sessionUser.getUserNum());
		if (list != null) {
			res = new ResponseEntity<List<SubjectVO>>(list, HttpStatus.OK);
		} else {
			res = new ResponseEntity<List<SubjectVO>>(list, HttpStatus.BAD_REQUEST);
		}
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
	
	
	// 이미지 변경 팝업 열기
	@GetMapping(path="/subject/image/editor/{subjectNum}")
	public String getImageEditor(
		@PathVariable("subjectNum") long subjectNum,
		Model model
			) {
		SubjectVO subject =  contentManager.getSubject(subjectNum);
		if(subject.getImageData() != null && subject.getImageSize() >0)
			model.addAttribute("hasImage", true);
		model.addAttribute("subjectNum", subjectNum);
		return "content/image-change-popup";
	}
	
	// 이미지 변경하기
	@PostMapping(path="/subject/image/{subjectNum}")
	public ResponseEntity<String> updateSubjectImage(
			@PathVariable("subjectNum") long subjectNum,
			@RequestParam(name = "file") MultipartFile file
		){
		int result = 0;
		if(file!=null) {
			result = contentManager.changeImage(subjectNum, file);
		}
		ResponseEntity<String> res = null;
		
		logger.info("file:"+file);
		return res;
	}
	
	// 빈 과목 생성
	@PostMapping(path = "/subject/new", produces = "application/json")
	public ResponseEntity<Map<String, Object>> createNewSubject(HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		UserVO sessionUser = loginService.getLoggedInUser(session);
		long newSubjectNum = contentManager.createEmptySubject(sessionUser.getUserNum());
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("newSubjectNum", newSubjectNum);
		res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		return res;
	}

	// 과목 삭제
	@DeleteMapping(path = "/subject/{subjectNum}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> deleteSubject(
			@PathVariable(name = "subjectNum") long subjectNum,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> body = new HashMap<String, Object>();
		UserVO sessionUser = loginService.getLoggedInUser(session);
		int result = contentManager.deleteSubject(subjectNum, sessionUser.getUserNum());
		if (result != 1) {
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			body.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		}

		return res;
	}

	// 과목 제목 수정
	@PutMapping(path = "/subject", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> updateSubjectTitle(
			@RequestBody Map<String, Object> requestBody,
//			@PathVariable(name = "subjectNum") long subjectNum, 
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> body = new HashMap<String, Object>();
		UserVO sessionUser = loginService.getLoggedInUser(session);
		if (sessionUser == null) {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		}
//		logger.info("requestBody:"+requestBody);
//		logger.info("requestBody.get(\"subjectNum\") instanceof String:"+ ( (requestBody.get("subjectNum")) instanceof Integer));
		long subjectNum = (Integer)requestBody.get("subjectNum");
		String title = (String) requestBody.get("title");
		logger.info("subjectNum :" + subjectNum );
		logger.info("title:" + title);
		int result = contentManager.updateSubjectTitle(subjectNum, title, sessionUser.getUserNum());
		logger.info("result:"+result);
		if (result != 1) {
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		} else {
			body.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		}
		return res;
	}

	// 단원 리스트
	@GetMapping(path = "/unit/list/{subjectNum}", produces = "application/json")
	public ResponseEntity<List<UnitVO>> getUnitList(
			@PathVariable(name = "subjectNum") long subjectNum,
			HttpSession session
			) {
		ResponseEntity<List<UnitVO>> res = null;

		List<UnitVO> list = contentManager.getUnitList(subjectNum);
		if (list != null) {
			res = new ResponseEntity<List<UnitVO>>(list, HttpStatus.OK);
		} else {
			res = new ResponseEntity<List<UnitVO>>(list, HttpStatus.BAD_REQUEST);
		}
		return res;
	}

	// 빈 단원 생성
	@PostMapping(path = "/unit/new/{subjectNum}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> createNewUnit(
			@PathVariable(name = "subjectNum") long subjectNum,
			@RequestBody Map<String, Object> requestBody,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		String chapterDepth = null;
		chapterDepth = (String) requestBody.get("chapterDepth");
		long newUnitNum = contentManager.createEmptyUnit(subjectNum,chapterDepth);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("newUnitNum:", newUnitNum);
		res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		return res;
	}

	// 단원 삭제
	@DeleteMapping(path = "/unit/{unitNum}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> deleteUnit(
			@PathVariable(name = "unitNum") long unitNum,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> body = new HashMap<String, Object>();
		UserVO sessionUser = loginService.getLoggedInUser(session);
		int result = contentManager.deleteUnit(unitNum, sessionUser.getUserNum());
		if (result < 0) {
			
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			body.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		}

		return res;
	}

	// 단원 제목 수정
	@PutMapping(path = "/unit/title/{unitNum}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> updateUnitTitle(
			@RequestBody Map<String, Object> requestBody,
			@PathVariable(name = "unitNum") long unitNum, HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> body = new HashMap<String, Object>();
		UserVO sessionUser = loginService.getLoggedInUser(session);
		if (sessionUser == null) { // 세션확인
			return new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		}
		String title = (String) requestBody.get("title");
		logger.info("title:" + title);
		int result = contentManager.updateUnitTitle(unitNum, title, sessionUser.getUserNum());
		if (result != 1) {
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		} else {
			body.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		}
		return res;
	}
	
	// 단원 뎁스 수정
	@PutMapping(path = "/unit/depth/{unitNum}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> updateUnitDepth(
			@RequestBody Map<String, Object> requestBody,
			@PathVariable(name = "unitNum") long unitNum, HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> body = new HashMap<String, Object>();
		UserVO sessionUser = loginService.getLoggedInUser(session);
		if (sessionUser == null) {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		}
		String chapterDepth = (String) requestBody.get("chapterDepth");
		logger.info("chapterDepth:" + chapterDepth);
		int result = contentManager.updateUnitDepth(unitNum, chapterDepth, sessionUser.getUserNum());
		if (result != 1) {
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		} else {
			body.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		}
		return res;
	}
	
	
	// 단원 편집화면
	@GetMapping("/unit/editor/{unitNum}")
	public String getUnitEditorPage(
			@PathVariable(name = "unitNum") long unitNum, 
			HttpSession session,
			Model model) {
		UnitVO unit = contentManager.getUnitContent(unitNum);
		logger.info("편집할 단원:"+unit);
		model.addAttribute("unit", unit);
		return "content/unitContentEditor";
	}

	// 단원 편집 적용
	@PostMapping(path = "/unit/edit")
	public String updateUnit(
			@ModelAttribute("unit") UnitVO unit, 
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		logger.info("unit:"+unit);
		// 나중에 오류여부 체크
		int result = contentManager.updateUnit(unit);
		return "redirect:/content/learning";
	}

	// 마무리문제 리스트
	@GetMapping(path = "/quiz/list", produces = "application/json")
	public ResponseEntity<List<QuizVO>> getQuizList(
			@RequestParam(name = "unitNum", required = true) long unitNum) {
		ResponseEntity<List<QuizVO>> res = null;
		List<QuizVO> list = contentManager.getQuizList(unitNum);
		logger.info("unitNum:" + unitNum);
		res = new ResponseEntity<List<QuizVO>>(list, HttpStatus.OK);
		return res;
	}

	// 마무리문제 내용
	@GetMapping(path = "/quiz/{quizNum}", produces = "application/json")
	public ResponseEntity<QuizVO> getQuiz(
			@PathVariable(name = "quizNum") long quizNum) {
		ResponseEntity<QuizVO> res = null;
		QuizVO quiz = contentManager.getQuiz(quizNum);
		logger.info("quizNum:" + quizNum);
		res = new ResponseEntity<QuizVO>(quiz, HttpStatus.OK);
		return res;
	}

	// 빈 마무리문제 생성
	@PostMapping(path = "/quiz/new/{unitNum}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> createNewQuiz(
			@PathVariable(name = "unitNum", required = true) long unitNum,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		logger.info("createNewQuiz():" + unitNum);
		UserVO sessionUser = loginService.getLoggedInUser(session);

		long newQuizNum = contentManager.createEmptyQuiz(unitNum,sessionUser.getUserNum());
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("newQuizNum ", newQuizNum);
		res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		return res;
	}

	// 마무리문제 삭제
	@DeleteMapping(path = "/quiz/{quizNum}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> deleteQuiz(
			@PathVariable(name = "quizNum") long quizNum,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> body = new HashMap<String, Object>();
		UserVO sessionUser = loginService.getLoggedInUser(session);
		int result = contentManager.deleteQuiz(quizNum,sessionUser.getUserNum());
		if (result != 1) {
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			body.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		}

		return res;
	}

	// 마무리문제 수정
	@PutMapping(path = { "/quiz/{quizNum}" }, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> updateQuiz(
			@PathVariable(name = "quizNum") long quizNum,
			@RequestBody Map<String, Object> reqBody,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> resBody = new HashMap<String, Object>();
		logger.info("updateQuiz():" + reqBody);
		
		int result = contentManager.updateQuiz(quizNum,reqBody);
		if (result != 1) {
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			resBody.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(resBody, HttpStatus.OK);
		}
		return res;
	}

	// 마무리문제 답안 리스트
	@GetMapping(path = "/answer/list", produces = "application/json")
	public ResponseEntity<List<QuizAnswerVO>> getAnswerList(
			@RequestParam(name = "quizNum", required = true) long quizNum) {
		ResponseEntity<List<QuizAnswerVO>> res = null;
		List<QuizAnswerVO> list = contentManager.getAnswerList(quizNum);
		logger.info("quizNum:" + quizNum);
		res = new ResponseEntity<List<QuizAnswerVO>>(list,HttpStatus.OK);
		return res;
	}

	// 새 답안 생성
	@PostMapping(path = "/answer/new/{quizNum}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> createNewAnswer(
			@PathVariable(name = "quizNum", required = true) long quizNum,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		logger.info("createNewAnswer():" + quizNum);
		long newAnswerNum = contentManager.createEmptyAnswer(quizNum); 
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("newAnswerNum",newAnswerNum);
		res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		return res;
	}
	// 마무리문제 답안 내용
	@GetMapping(path = "/answer/{answerNum}", produces = "application/json")
	public ResponseEntity<QuizAnswerVO> getQuizAnswer(
			@PathVariable(name = "answerNum") long answerNum) {
		ResponseEntity<QuizAnswerVO> res = null;
		QuizAnswerVO answer = contentManager.getAnswer(answerNum);
		logger.info("answerNum:" + answerNum);
		res = new ResponseEntity<QuizAnswerVO>(answer, HttpStatus.OK);
		return res;
	}

	// 마무리문제 답안 삭제
	@DeleteMapping(path = "/answer/{answerNum}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> deleteAnswer(
			@PathVariable(name = "answerNum") long answerNum,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> body = new HashMap<String, Object>();
		int result = contentManager.deleteAnswer(answerNum);
		if (result != 1) {
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			body.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		}

		return res;
	}
	
	
	// 마무리문제 답 수정
	@PutMapping(path = { "/answer/{answerNum}"}, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> updateAnswer(
			@PathVariable(name = "answerNum" ) long answerNum,
			@RequestBody Map<String, Object> reqBody,
			HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> resBody = new HashMap<String, Object>();		
		int result = contentManager.updateAnswer(answerNum,reqBody);
		logger.info("updateAnswer():" + reqBody);
		
		if (result != 1) {
			res = new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			resBody.put("msg", "success");
			res = new ResponseEntity<Map<String, Object>>(resBody, HttpStatus.OK);
		}
		return res;
	}
		
		
		
}
