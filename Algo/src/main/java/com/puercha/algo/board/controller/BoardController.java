package com.puercha.algo.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.puercha.algo.board.service.BoardService;
import com.puercha.algo.board.vo.BoardCategoryVO;
import com.puercha.algo.board.vo.BoardPostVO;

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

}
