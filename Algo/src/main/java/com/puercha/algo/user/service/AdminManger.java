package com.puercha.algo.user.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.puercha.algo.user.dao.UserDAO;
import com.puercha.algo.user.vo.TutorApplicationVO;

@Service
public class AdminManger implements AdminService {

	@Inject
	UserDAO userDAO;
	
	
	@Override
	public long apply(TutorApplicationVO tutorApplication) {
		// TODO Auto-generated method stub
		tutorApplication.setApproval('p');
		int result = userDAO.insertApplication(tutorApplication);
		return tutorApplication.getApplicationNum();
	}

}
