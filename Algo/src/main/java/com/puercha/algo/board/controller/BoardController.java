package com.puercha.algo.board.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.puercha.algo.board.service.BoardService;
import com.puercha.algo.board.vo.AttachmentVO;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;
import com.puercha.algo.user.service.LoginService;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	BoardService boardService;
	@Inject
	LoginService loginService;

	@ModelAttribute
	public void getBoardCategory(Model model) {
		List<BoardCategoryVO> boardCategoryVO = boardService.getCategory();
		model.addAttribute("boardCategoryVO", boardCategoryVO);
	}

	// 목록보기
	@GetMapping({ "/list", "/list/{reqPage}", "/list/{reqPage}/{searchType}/{keyword}" })
	public String list(@PathVariable(required = false) String reqPage,
			@PathVariable(required = false) String searchType, @PathVariable(required = false) String keyword,
			HttpSession session, Model model) {

		UserVO userVO = (UserVO) session.getAttribute("user");
		// 게시글목록
		model.addAttribute("list", boardService.list(reqPage, searchType, keyword));
		// 페이지제어
		model.addAttribute("pm", boardService.getPageManager(reqPage, searchType, keyword));
		return "board/board";
	}

	// 게시글보기
	@GetMapping("/post/{postNum}/{returnPage}")
	public String view(@ModelAttribute @PathVariable String returnPage, @PathVariable String postNum, Model model,
			HttpServletRequest request) {

		logger.info("게시글 보기 :" + postNum);
		Map<String, Object> map = boardService.view(postNum);
		BoardPostVO boardPostVO = (BoardPostVO) map.get("boardPostVO");
		logger.info("controller" + boardPostVO);
		model.addAttribute("boardPostVO", boardPostVO);
		UserVO userVO = loginService.getLoggedInUser(request.getSession());
		model.addAttribute("userVO", userVO);

		List<AttachmentVO> attachmentVO = null;
		if (map.get("attachmentVO") != null) {
			attachmentVO = (List<AttachmentVO>) map.get("attachmentVO");
			model.addAttribute("attachmentVOs", attachmentVO);
		}
		return "/board/postView";
	}

	// 첨부파일 다운로드/
	@GetMapping("selectFile/{fid}")
	public ResponseEntity<byte[]> getFile(@PathVariable String fid) {
		AttachmentVO attachmentVO = boardService.viewFile(fid);
//		logger.info("getFile" + attachmentVO.toString());

		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = attachmentVO.getFtype().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(Integer.parseInt(attachmentVO.getFsize()));

		// 첨부파일이 한글일 경우 깨짐 방지
		String fileName = null;

		try {
			fileName = new String(attachmentVO.getFname().getBytes("euc-kr"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		headers.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<byte[]>(attachmentVO.getFdata(), headers, HttpStatus.OK);

	}

	// 게시글 작성양식
	@GetMapping("/posting/{returnPage}")
	public String posting(@ModelAttribute @PathVariable String returnPage, Model model, HttpServletRequest request) {

		BoardPostVO boardPostVO = new BoardPostVO();
		// UserVO userVO = (UserVO)request.getSession().getAttribute("userInfo");
		UserVO userVO = loginService.getLoggedInUser(request.getSession());

		logger.info("userVO :" + userVO);

		boardPostVO.setUserName(userVO.getUsername());
		boardPostVO.setUserNum(userVO.getUserNum());

		model.addAttribute("boardPostVO", boardPostVO);
		model.addAttribute("userVO", userVO);

		return "/board/posting";
	}

	// 게시글 작성하기
	@PostMapping("/posting/{returnPage}")
	public String write(@PathVariable String returnPage, @Valid @ModelAttribute("boardPostVO") BoardPostVO boardPostVO,
			BindingResult result, HttpSession session) {

		logger.info("게시글 작성 :" + boardPostVO.toString());

		if (result.hasErrors()) {
			logger.info("resultError");
			return "board/posting";
		}

		UserVO userVO = loginService.getLoggedInUser(session);

		logger.info("게시글 작성  userInfo" + userVO);
		boardPostVO.setUserName(userVO.getUsername());
		boardPostVO.setUserNum(userVO.getUserNum());

		logger.info("게시글 작성 2" + boardPostVO.toString());
		boardService.write(boardPostVO);

		return "redirect:/board/post/" + boardPostVO.getPostNum() + "/" + returnPage;
	}

	// 게시글 삭제
	@GetMapping("/delete/{postNum}/{returnPage}")
	public String delete(@PathVariable String returnPage, @PathVariable String postNum, Model model) {

		// 1) 게시글 및 첨부파일 삭제
		boardService.deleteFiles(postNum);
		boardService.delete(postNum);

		// 2) 게시글 목록 가져오기
		model.addAttribute("list", boardService.list());

		return "redirect:/board/list/" + returnPage;
	}

	// 첨부파일 1건 삭제
	@DeleteMapping("file/{fid}")
	@ResponseBody
	public ResponseEntity<String> deleteFile(@PathVariable String fid) {
		int cnt = 0;

		cnt = boardService.deleteFile(fid);

		if (cnt == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FAILED_DEPENDENCY);
		}

	}

	// 게시글 수정
	@PostMapping("/modifying/{postNum}/{returnPage}")
	public String modify(@PathVariable String returnPage, @PathVariable String postNum,
			@Valid @ModelAttribute("boardPostVO") BoardPostVO boardPostVO, BindingResult result) {
		if (result.hasErrors()) {
			return "/board/postView";
		}
		logger.info("게시글 수정 내용: " + boardPostVO.toString());
		boardService.modify(boardPostVO);

		return "redirect:/board/post/" + boardPostVO.getPostNum() + "/" + returnPage;
	}

	// 답글달기 양식
	@GetMapping("reply/{postNum}/{returnPage}")
	public String replyForm(@ModelAttribute @PathVariable String returnPage, @PathVariable String postNum,
			Model model) {

		Map<String, Object> map = boardService.view(postNum);
		BoardPostVO boardPostVO = (BoardPostVO) map.get(BoardService.KEY_BOARD_VO);

		boardPostVO.setTitle("->[답글]" + boardPostVO.getTitle());
		boardPostVO.setContent("->[본문] " + boardPostVO.getContent());
		model.addAttribute("boardPostVO", boardPostVO);

		return "/board/posting";
	}

	// 답글처리
	@PostMapping("/reply/{returnPage}")
	public String reply(@PathVariable String returnPage, @Valid @ModelAttribute("boardPostVO") BoardPostVO boardPostVO,
			BindingResult result, HttpServletRequest request, HttpSession session) {
		logger.info("답글달기 실행 :" + boardPostVO.toString());
		if (result.hasErrors()) {
			return "/board/posting";
		}
		UserVO userVO = (UserVO) session.getAttribute("user");
		boardPostVO.setUserNum(userVO.getUserNum());
		boardPostVO.setUserName(userVO.getUsername());
		boardService.reply(boardPostVO);

		return "redirect:/board/list/" + returnPage;
	}

	@GetMapping(value = "/test", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Map<String, Object>> testJson(HttpSession session) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> myjson = new HashMap();
		UserVO userVO = (UserVO) session.getAttribute("userInfo");
		myjson.put("user", userVO);
		myjson.put("hello", "world!");
		res = new ResponseEntity<Map<String, Object>>(myjson, HttpStatus.OK);
		return res;
	}

}
