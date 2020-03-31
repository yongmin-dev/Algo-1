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

import com.puercha.algo.user.vo.UserVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class UserDAOImplTest {
	
	private final static Logger logger
	= LoggerFactory.getLogger(UserDAOImplTest.class);

	@Inject
	@Qualifier("userDAOImpl")
	UserDAO userDAO;

	@Test
	@DisplayName("사용자등록")
	@Disabled
	void joinUser() {
		UserVO userVO = new UserVO();
		

		userVO.setEmail("skyslas2k@nater.com");
		userVO.setPw("123456");
		userVO.setTel("010-1234-5678");
		userVO.setUsername("폰은정");
		userVO.setType('S');
		userVO.setGender("남");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1996);
		cal.set(Calendar.MONTH, 05);
		cal.set(Calendar.DAY_OF_MONTH, 12);		
		userVO.setBirth(cal.getTime());
		userVO.setAddress("울산");
		int cnt = userDAO.insertUser(userVO);
		assertEquals(1,cnt);	
	}
	
	@Test
	@DisplayName("사용자수정")
	@Disabled
	void modfyUser() {
		UserVO userVO = new UserVO();
		userVO.setUserNum(21);
		userVO.setTel("010-9999-2313");
		userVO.setUsername("테스트으으");
		userVO.setGender("남");
		userVO.setAddress("서울");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1996);
		cal.set(Calendar.MONTH, 05);
		cal.set(Calendar.DAY_OF_MONTH, 12);		
		userVO.setBirth(cal.getTime());
		userVO.setEmail("skyslask@nate.com2");
		int cnt = userDAO.updateUser(userVO);
		assertEquals(1,cnt);
	}
	@Test
	@DisplayName("회원 전체조회")
	@Disabled
	void selectAllUser() {
		List<UserVO> list = userDAO.selectAllUser();
		assertNotNull(list);
		logger.info(list.toString());
	}
	@Test
	@DisplayName("회원 개별조회")
	@Disabled
	void selectUser() {
		UserVO userVO = userDAO.selectUser("test@test.com");
		assertEquals("test@test.com", userVO.getEmail());
		logger.info(userVO.toString());
	}
	@Test
	@DisplayName("회원 개별조회2")
	@Disabled
	void selectUser2() {
		UserVO userVO = userDAO.selectUser("test@test.com", "admin1234");
		assertEquals("test@test.com", userVO.getEmail());
		assertEquals("admin1234", userVO.getPw());
		logger.info(userVO.toString());
	}
	@Test
	@DisplayName("비밀번호 변경")
	@Disabled
	void changePW() {
		String oldPw = "123456";
		String newPw = "1234";
		int cnt = userDAO.changePW(1,oldPw ,newPw );
		UserVO userVO = userDAO.selectUser(1);
		assertEquals(1, cnt);
		assertNotEquals(oldPw ,userVO.getPw());
		assertEquals(newPw,userVO.getPw());
	}
	@Test
	@DisplayName("비밀번호 변경2")
	@Disabled
	void changePW2() {
		String oldPw = "1234";
		String newPw = "11111";
		int cnt = userDAO.changePW("test@test.com", oldPw, newPw);
		UserVO userVO = userDAO.selectUser(1);
		assertEquals("test@test.com", cnt);
		assertNotEquals(oldPw, userVO.getPw());
		assertEquals(newPw,userVO.getPw());
	}
	@Test
	@DisplayName("회원 탈퇴")
	@Disabled
	void outUser() {
		int cnt = userDAO.deleteUser(30);
		assertEquals(1,cnt);
	}
	@Test
	@DisplayName("회원 탈퇴2")
	@Disabled
	void outUser2() {
		int cnt = userDAO.deleteUser("skyslas2k@nater.com", "123456");
		assertEquals(1,cnt);
	}
}
