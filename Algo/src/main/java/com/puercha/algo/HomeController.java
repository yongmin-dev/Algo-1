package com.puercha.algo;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.puercha.algo.board.service.BoardService;
import com.puercha.algo.challenge.service.ChallengeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject
	ChallengeService challengeManager;
	@Inject
	BoardService boardService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		// 상위 문제
		List<Map<String,Object>> hotChallenges = challengeManager.getHotChalllenges(5);
		model.addAttribute("hotChallenges", hotChallenges );
		
		// 상위 랭커
		List<Map<String,Object>> rankers = challengeManager.getTopRankers(5);
		model.addAttribute("rankers", rankers);
		
		// 최근 게시글
		Map<String,Object> boardListDatas= boardService.getBoardList(1, null, null);
		model.addAttribute("boardListDatas", boardListDatas);
		String formattedDate = dateFormat.format(date);		
		model.addAttribute("serverTime", formattedDate );
		
		return "main";
	}
	
}
