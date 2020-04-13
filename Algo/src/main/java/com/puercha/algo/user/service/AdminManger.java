package com.puercha.algo.user.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.puercha.algo.common.PageManager;
import com.puercha.algo.common.RowCriteria;
import com.puercha.algo.user.dao.UserDAO;
import com.puercha.algo.user.vo.TutorApplicationVO;
import com.puercha.algo.user.vo.UserVO;

@Service
public class AdminManger implements AdminService {
	
	private final static Logger logger = LoggerFactory.getLogger(AdminManger.class);

	@Inject
	UserDAO userDAO;
	
	
	@Override
	public long apply(TutorApplicationVO tutorApplication) {
		logger.info("apply(TutorApplicationVO tutorApplication)");
		tutorApplication.setApproval('p');
		MultipartFile file =  tutorApplication.getFile();
		if(file!=null) {
			try {
				tutorApplication.setAttachmentData(file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tutorApplication.setAttachmentName(file.getName());
			tutorApplication.setAttachmentSize(file.getSize());
			tutorApplication.setAttachmentType(file.getContentType());
		}
		int result = userDAO.insertApplication(tutorApplication);
		logger.info("apply(TutorApplicationVO tutorApplication)");
		return tutorApplication.getApplicationNum();
	}


	/**
	 * 튜터 신청서 리스트
	 * @param page 신청서 페이지
	 * @return 신청서 리스트
	 */
	@Override
	public Map<String,Object> getApplicationList(long page) {
		Map<String,Object> datas = new HashMap<String, Object>();
		long totalRowNum = userDAO.getTotalApplicationNum();
		RowCriteria rc = new RowCriteria((int) page);
		PageManager pm = new PageManager(rc, totalRowNum );
		List<TutorApplicationVO> list = userDAO.selectAllTutorApplication(pm.getRc().getStartRec(), pm.getRc().getEndRec());
		datas.put("list", list);
		datas.put("pageManager",pm);
		return datas;
	}

	/**
	 * 모든 신청서의 개수를 가져온다.
	 * @return 신청서 총 개수
	 */

	@Override
	public long getTotalApplicationNum() {
		logger.info("getTotalApplicationNum()");
		return userDAO.getTotalApplicationNum();
	}


	@Override
	public int commitApproval(long applicationNum, char approval) {
		logger.info("comitApproval(long applicationNum, char approval)");
		TutorApplicationVO appli = userDAO.selectOneTutorApplication(applicationNum);
		if(appli!=null) {
			logger.info("application:"+appli);
			appli.setApproval(approval);
			if(approval=='a') {
				UserVO user = userDAO.selectUser(appli.getUserNum());
				logger.info("user:"+user);
				user.setType('T');
				userDAO.updateUser(user);
			}
			return userDAO.updateApplication(appli );			
		}
		return 0;
	}


	
	
}
