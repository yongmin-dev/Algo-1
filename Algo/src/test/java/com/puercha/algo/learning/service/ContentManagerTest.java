package com.puercha.algo.learning.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.puercha.algo.content.service.ContentManagingService;
import com.puercha.algo.learning.dao.LearningDAO;
import com.puercha.algo.learning.vo.QuizVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ContentManagerTest {
	private static final Logger logger = LoggerFactory.getLogger(ContentManagerTest.class);
	
	@Inject
	ContentManagingService contentManager;
	
	@Inject
	LearningDAO learningDAO;
	
	@Test
	public void updateQuiz() {
		Map<String,Object> datas = new HashMap<String, Object>();
		datas.put("title", "제목입니다~");
		datas.put("content", "내용입니다~");
		datas.put("difficulty",2);
		datas.put("correctAnswer","ee");
		datas.put("solution","풀이입니다~");
		
//		int result = contentManager.updateQuiz(5, map);
		long quizNum = 5; 
		QuizVO quiz = learningDAO.selectOneQuiz(quizNum);		
		if(quiz != null ) {
			Set<String> keySet = datas.keySet();
			for(String key :keySet) {
				Object object = datas.get(key);
				String methodName = "set"+ key.substring(0,1).toUpperCase()+key.substring(1);
				Method[] methods = quiz.getClass().getDeclaredMethods();
				for(Method method : methods) {
					if(method.getName().equals(methodName) ){
						try {
							method.invoke(quiz, object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
			logger.info(quiz.toString());
//			return learningDAO.updateQuiz(quiz);
		}
	}
}
