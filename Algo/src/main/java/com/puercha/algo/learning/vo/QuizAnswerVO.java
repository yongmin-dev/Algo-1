package com.puercha.algo.learning.vo;

import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.Target;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

/**
 * @author Yongmin
 * 마무리문제 답안 VO
 * 
 */
@Data
@Entity
public class QuizAnswerVO {


	//문제 번호
	private long quizNum;
	
	//답변 번호
	private long answerNum;
		
	//선택지 내용
	private String content;
	
	//정답여부
	@Getter @Setter
	private boolean isCorrect;
	
	//생성일시
	private Date createdAt;
	
	//수정일시
	private Date updatedAt;
	
	/**
	 *  character로 부터 correct를 set함
	 * @param c 문자형이 T이면 'true' 나머지는 'false'
	 */
	public void setCorrectFromCharacter(char c) {
		if(c=='t' || c=='T') {
			this.isCorrect = true;
		}
		else {
			this.isCorrect =false;
		}
	}
	/**
	 * String으로 부터 correct를 set함
	 * @param correct 문자열이 'true'이거나 't'이면 'true' 나머지는 'false' (대소문자 상관없음)
	 */
	public void setCorrectFromString(String correct) {
		if(correct.toUpperCase().equals("TRUE") ||
			correct.toUpperCase().equals("T")
				) {
			this.isCorrect = true;
		} else {
			this.isCorrect = false;
		}
	}
	public void setCorrectness(boolean correctness) {
		this.isCorrect = correctness;
	}
//	@Tolerate
	public String getCorrectAsString() {
		if(this.isCorrect)
			return "t";
		else 
			return "f";					
	}
}
