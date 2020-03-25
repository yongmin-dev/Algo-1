package com.puercha.algo.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.puercha.algo.user.vo.UserVO;
/**
 * UserDAO 인터페이스를 구현하는 클래스
 * @author Youdong
 *
 */
@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger
	  =  LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Inject
	private SqlSession sqlSession;
	
	/* Create */
	//사용자 생성
	@Override
	public int insertUser(UserVO userVO) {
		logger.info("UserDAOImpl.insertUser(UserVO userVO) 호출됨!");
		return sqlSession.insert("mappers.UserDAO-mapper.insertUser",userVO);
	}
	/* Read */
	//사용자 전체조회
	@Override
	public List<UserVO> selectAllUser() {
		logger.info("UserDAOImpl.selectAllUser() 호출됨!");
		return sqlSession.selectList("mappers.UserDAO-mapper.selectAllUser");
	}
	//사용자 개별조회
	@Override
	public UserVO selectUser(String email) {
		logger.info("UserDAOImpl.selectUser(String email) 호출됨!");
		Map<String,Object> params = new HashMap<>();
		params.put("email", email);
		return sqlSession.selectOne("mappers.UserDAO-mapper.selectUser",params);
	}
	@Override
	public UserVO selectUser(String email, String pw) {
		logger.info("UserDAOImpl.selectUser(String email, String pw) 호출됨!");
		Map<String,Object> paras = new HashMap<>();
		paras.put("email",email);
		paras.put("pw",pw);
		return sqlSession.selectOne("mappers.UserDAO-mapper.selectUser",paras);
	}
	/* Update */
	// 사용자 수정
	@Override
	public int updateUser(UserVO userVO) {
		logger.info("UserDAOImpl.updateUser(UserVO userVO) 호출됨!");
		return sqlSession.update("mappers.UserDAO-mapper.updateUser",userVO);
	}
	//사용자 비밀번호변경
	@Override
	public int changePW(long userNum, String oldPw, String newPw) {
		logger.info("UserDAOImpl.changePW(long userNum, String oldPw, String newPw) 호출됨!");
		Map<String,Object> pass = new HashMap<>();
		pass.put("userNum",userNum);
		pass.put("oldPw",oldPw);
		pass.put("newPw",newPw);
		return sqlSession.update("mappers.UserDAO-mapper.changePW",pass);
	}
	/* Delete */
	// 사용자 삭제
	@Override
	public int deleteUser(String email, String pw) {
		logger.info("UserDAOImpl.deleteUser(String email, String pw) 호출됨!");
		Map<String,Object> delt = new HashMap<>();
		delt.put("email",email);
		delt.put("pw",pw);
		return sqlSession.delete("mappers.UserDAO-mapper.deleteUser", delt);
	}

	@Override
	public int deleteUser(long userNum) {
		logger.info("UserDAOImpl.deleteUser(long userNum) 호출됨!");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userNum", userNum);
		return sqlSession.delete("mappers.UserDAO-mapper.deleteUser",params);
	}

}
