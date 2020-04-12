package com.puercha.algo.learning.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.puercha.algo.common.FindCriteria;
import com.puercha.algo.common.PageManager;
import com.puercha.algo.common.RowCriteria;
import com.puercha.algo.learning.dao.LearningDAO;
import com.puercha.algo.learning.vo.QuizAnswerVO;
import com.puercha.algo.learning.vo.QuizResultVO;
import com.puercha.algo.learning.vo.QuizVO;
import com.puercha.algo.learning.vo.SubjectVO;
import com.puercha.algo.learning.vo.UnitVO;

@Service
public class LearningManager implements LearningService {

	private final static Logger logger = LoggerFactory.getLogger(LearningManager.class);

	@Inject
	LearningDAO learningDAO;

	// 과목보기(검색어
	@Override
	public List<SubjectVO> subjectList(String reqPage, String searchType, String keyword) {
		int l_reqPage = 0;

		// 요청페이지 정보가 없으면 1로 설정
		if (reqPage == null || reqPage.trim().isEmpty()) {
			l_reqPage = 1;
		} else {
			l_reqPage = Integer.parseInt(reqPage);
		}
		RowCriteria rowCriteria = new RowCriteria(l_reqPage);

		return learningDAO.selectAllSubjects(rowCriteria.getStartRec(), searchType, keyword);

	}
	
	

	// 페이지 제어
	@Override
	public PageManager getPageManager(String reqPage, String searchType, String keyword) {

		PageManager pm = null;
		FindCriteria fc = null;

		int totalRec = 0;
		int l_reqPage = 0;

		// 요청페이지 정보가 없으면 1로 초기화
		if (reqPage == null || reqPage.trim().isEmpty()) {
			l_reqPage = 1;
		} else {
			l_reqPage = Integer.parseInt(reqPage);
		}

		totalRec = learningDAO.countTotalRecord(searchType, keyword);

		fc = new FindCriteria(l_reqPage, searchType, keyword);
		pm = new PageManager(fc, totalRec);

		return pm;
	}

	// 단원 리스트 불러오기
	@Override
	public List<UnitVO> unitList(String subjectNum) {
		logger.info("unitList(String subjectNum)");
		return learningDAO.selectAllUnits(Integer.parseInt(subjectNum));
		
	}

	// 단원 내용 보기
	@Override
	public Map<String, Object> viewUnit(String unitNum) {
		UnitVO unitVO = learningDAO.selectOneUnit(Integer.parseInt(unitNum));

		Map<String, Object> map = new HashMap<>();
		map.put("UnitVO", unitVO);

		return map;
	}

	// 마무리문제 보기
	@Override
	public List<QuizVO> viewQuiz(String unitNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("unitNum", unitNum);

		return learningDAO.selectAllQuiz(Integer.parseInt(unitNum));
	}
	
	@Override
	public List<QuizAnswerVO> viewQuizAnswer(String quizNum) {
		return learningDAO.selectAllAnswer(Integer.parseInt(quizNum));
	}



	/**
	 * 마무리문제 답안 체크하기
	 * @param quizNum 문제 번호
	 * @param answers 답
	 * @return 결과 데이터
	 */
	@Override
	public Map<String, Object> checkAnswers(long quizNum, List<Long> answers, long userNum) {
		Map<String,Object> result = new HashMap<String, Object>();

		QuizResultVO quizResult = new QuizResultVO();
		quizResult.setStatus('f');
		QuizVO quiz = learningDAO.selectOneQuiz(quizNum);
		List<QuizAnswerVO> quizAnswers = learningDAO.selectAllAnswer(quizNum);
		logger.info("quizAnswers:"+quizAnswers);
		List<Long> correctAnswerList = new ArrayList<Long>();
		if(quizAnswers!=null && answers!=null && answers.size()>0 && quizAnswers.size()>0) {
			// 답 개수 같아야함
			
			char status = 't';
			for(int i=0;i<quizAnswers.size();i++) {					
				QuizAnswerVO answer = quizAnswers.get(i);
				if(answer.isCorrect()) {
					logger.info("correctAnswer:"+answer);
					if(!answers.contains(answer.getAnswerNum())) {
						status = 'f';
					}
									
					correctAnswerList.add(answer.getAnswerNum());
				}
			}
			quizResult.setStatus(status);
			
			//정답 개수가 같아야함
			if(correctAnswerList.size()!=answers.size())
				quizResult.setStatus('f');
		}
		
		result.put("quizResult", quizResult);
		result.put("quiz", quiz);
		result.put("correctAnswerList", correctAnswerList);
		quizResult.setAnswer(answers.toString());
		quizResult.setQuizNum(quizNum);
		quizResult.setUserNum(userNum);
		learningDAO.insertQuisResult(quizResult);
		return result;
	}
	

	

}
