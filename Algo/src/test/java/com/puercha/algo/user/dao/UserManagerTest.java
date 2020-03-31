package com.puercha.algo.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.puercha.algo.user.service.UserService;
import com.puercha.algo.user.vo.UserVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class UserManagerTest {

	private final static Logger logger
	= LoggerFactory.getLogger(UserManagerTest.class);
	
	@Inject
	@Qualifier("userManager")
	UserService userService;
	
	@Test
	@DisplayName("계정생성")	
	@Disabled
	 void joinUser() {
		UserVO userVO = new UserVO();
	
		userVO.setEmail("skyslask@naver.com");
		userVO.setPw("12345");
		userVO.setTel("010-1154-5262");
		userVO.setUsername("테스트으");
		userVO.setType('S');
		userVO.setGender("남");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 05);
		cal.set(Calendar.DAY_OF_MONTH, 12);
		userVO.setBirth(cal.getTime());
		userVO.setAddress("울산");
		int cnt = userService.joinUser(userVO);
		assertEquals(1,cnt);
	}
	@Test
	@DisplayName("사용자수정")
	@Disabled
	void modfyUser() {
		UserVO userVO  = new UserVO();
		userVO.setUserNum(41);
		userVO.setTel("010-4231-1242");
		userVO.setUsername("머대리");
		userVO.setGender("남");
		userVO.setAddress("서울");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 05);
		cal.set(Calendar.DAY_OF_MONTH, 12);
		userVO.setBirth(cal.getTime());
		userVO.setEmail("skyslask@naver.com");
		int cnt = userService.modifyUser(userVO);
		assertEquals(1,cnt);
	}
	@Test
	@DisplayName("회원 전체 조회")
	@Disabled
	void selectAllUser() {
		List<UserVO> list = userService.selectAllUser();
		assertNotNull(list);
		logger.info(list.toString());
	}
	@Test
	@DisplayName("회원 개별조회")
	@Disabled
	void selectUser() {
		UserVO userVO = userService.selectUser("skyslask@naver.com");
		assertEquals("skyslask@naver.com",userVO.getEmail());
		logger.info(userVO.toString());
	}
	@Test
	@DisplayName("비밀번호 변경")
	@Disabled
	void changePW() {
		String oldPw= "12345";
		String newPw= "112233";
		int cnt = userService.changePW(41, oldPw, newPw);
		UserVO userVO = userService.selectUser("skyslask@naver.com");
		assertEquals(1,cnt);
		assertNotEquals(oldPw,userVO.getPw());
		assertEquals(newPw,userVO.getPw());
	}
	
}
