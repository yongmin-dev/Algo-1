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
import com.puercha.algo.learning.vo.UnitCompletionVO;
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
		UnitVO unitVO = learningDAO.selectOneUnit(Integer.parseInt(unitNum), 0);

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


	/** 
	 * 과목리스트를 가져온다
	 * @param reqPage 요청 페이지 
	 * @param searchType 검색 타입
	 * @param keyword 검색어
	 * @param userNum 사용자번호
	 * @return 검색결과
	 */
	@Override
	public Map<String, Object> getSubjectList(String reqPage, String searchType, String keyword, long userNum) {
		logger.info("getSubjectList(String reqPage, String searchType, String keyword, long userNum)");
		Map<String, Object> datas = new HashMap<String, Object>();
		PageManager pageManager = getPageManager(reqPage, searchType, keyword);
		datas.put("pageManager", pageManager);
		List<SubjectVO> list =  learningDAO.selectAllSubjects(pageManager.getRc().getStartRec(), searchType, keyword,userNum);
		datas.put("list", list);
		return datas;
	}


	/**
	 * 단원의 마무리문제 전체 통과 여부 조회
	 * @param unitNum 단원 번호
	 * @param userNum 사용자 번호 
	 * @return 통과여부,총문제 수, 통과문제 수 등
	 */
	@Override
	public Map<String, Object> getUnitProgress(long unitNum, long userNum) {
		logger.info("getUnitProgress(long unitNum, long userNum)");
		return learningDAO.selectUnitProgress(unitNum, userNum);
	}


	/**
	 * 단원 내용 불러오기
	 * @param unitNum 단원 번호
	 * @return 단원 VO
	 */
	@Override
	public UnitVO getUnitContent(long unitNum) {
		logger.info("getUnitContent(long unitNum)");
		return learningDAO.selectOneUnit(unitNum, 0);
	}
	

	/**
	 * 단원 내용 불러오기(사용자의 진척도도 불러온다)
	 * @param unitNum 단원 번호
	 * @param userNum 사용자 번호
	 * @return 단원 VO
	 */
	@Override
	public UnitVO getUnitContent(long unitNum, long userNum) {
		logger.info("getUnitContent(long unitNum, long userNum)");
		return learningDAO.selectOneUnit(unitNum, userNum);
	}



	/**
	 * 단원 리스트 불러오기
	 * @param subjectNum 과목 번호
	 * @param userNum 사용자 번호
	 * @return 단원 VO의 리스트
	 */
	@Override
	public List<UnitVO> getUnitList(long subjectNum, long userNUm) {
		logger.info("getUnitList(long subjectNum)");
		return learningDAO.selectAllUnitMetadatas(subjectNum, userNUm);
	}


	/**
	 * 단원 완료여부를 체크후 단원완료 데이터를 생성 한다.
	 * @param unitNum 단원 번호 
	 * @return 단완 완료시 완료번호를 리턴함(0이 아닌 수)
	 */	
	@Override
	public long checkCompletion(long unitNum, long userNum) {
		logger.info("checkCompletion(long unitNum)");
		if(unitNum>0 && userNum>0) {			
			Map<String,Object> result= learningDAO.selectUnitProgress(unitNum, userNum);
			logger.info("result:"+result);
			if(result.containsKey("passesUnit")&& result.get("passesUnit") != null) {
				if("T".equals((String)result.get("passesUnit"))) {
					UnitCompletionVO unitCompletion = new UnitCompletionVO();
					unitCompletion.setStatus('C');
					unitCompletion.setUnitNum(unitNum);
					unitCompletion.setUserNum(userNum);
					learningDAO.insertUnitCompletion(unitCompletion);
					return unitCompletion.getComNum();
				}
				
			}		
		}
		return 0;
	}


	/**
	 * 마무리 문제 리스트를 가져온다.
	 * @param unitNum 단원 번호
	 * @return 마무리문제 VO 리스트 
	 */
	@Override
	public List<QuizVO> getQuizList(long unitNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitNum", unitNum);

//		return learningDAO.selectAllQuiz(Integer.parseInt(unitNum));
		return learningDAO.selectAllQuiz(unitNum);
	}


	/**
	 * 마무리문제의 메타정보들을 가져온다.
	 * @param unitNum 검색할 단원
	 * @param userNum 사용자 번호
	 * @return 메타정보 리스트
	 */
	@Override
	public List<Map<String, Object>> getQuizMatadatas(long unitNum, long userNum) {
		logger.info("getQuizMatadatas(long unitNum, long userNum)");
		
		return learningDAO.selectAllPassFailOfQuiz(unitNum, userNum);
	}



	/**
	 * 다음 단원번호를 가져온다.
	 * @param unitNum 현재 단원번호
	 * @return 다음 단원번호
	 */	
	@Override
	public long getNextUnitNum(long unitNum) {
		logger.info("getNextUnitNum(long unitNum)");
		return learningDAO.getNextUnitNum(unitNum);
	}	

	

}
