package com.puercha.algo.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.puercha.algo.board.service.BoardService;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;
import com.puercha.algo.user.vo.UserVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	BoardService boardService;

	@ModelAttribute
	public void getBoardCategory(Model model) {
		List<BoardCategoryVO> boardCategoryVO = boardService.getCategory();
		model.addAttribute("boardCategoryVO", boardCategoryVO);
	}

	// 게시글 작성양식
	@GetMapping("/writeForm/{returnPage}")
	public String writeForm(@ModelAttribute @PathVariable String returnPage, Model model, HttpServletRequest request) {
		model.addAttribute("boardPostVO", new BoardPostVO());
		return "/board/writeForm";
	}

	// 게시글 작성하기
	@PostMapping("/write/{returnPage}")
	public String write(@PathVariable String returnPage, @Valid @ModelAttribute("boardPostVO") BoardPostVO boardPostVO,
			BindingResult result, HttpServletRequest request) {

		logger.info("게시글 작성 :" + boardPostVO.toString());
	
		if(result.hasErrors()) {
			return "/board/writeForm";
		}
		
		UserVO userVO = (UserVO)request.getSession().getAttribute("user");
		userVO.setEmail(userVO.getEmail());
		userVO.setUsername(userVO.getUsername());
		
		logger.info("게시글 작성 2" + boardPostVO.toString());
		boardService.insert(boardPostVO);
		
		return "redirect:/board/view/"+returnPage+"/"+boardPostVO.getPostNum();
	}

	//목록보기
	@GetMapping({"/list", "/list/{reqPage}", "/list/{reqPage}/{searchType}/{keyword}"})
	public String listAll(
			@PathVariable(required=false) String reqPage,
			@PathVariable(required=false) String searchType,
			@PathVariable(required=false) String keyword,
			HttpSession session,
			Model model) {
		UserVO userVO = (UserVO)session.getAttribute("user");
		//게시글목록
		model.addAttribute("list", boardService.selectList(reqPage, searchType, keyword));
		//페이지제어
		model.addAttribute("pm", boardService.getPageManager(reqPage, searchType, keyword));
		return "board/list";
	}
			
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
