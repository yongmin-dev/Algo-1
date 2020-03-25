package com.puercha.algo.common;


/**
 * Record 페이징
 * @author Yongmin
 *
 * 
 */
public class RowCriteria {
	
	//요청페이지
	private int reqPage;
	
	//한 페이지에 보여줄 레코드 수
	private int numPerPage;
	
	private final int NUM_PER_PAGE = 10;
	
	public RowCriteria(int reqPage) {
		this.reqPage = reqPage;
		this.numPerPage = NUM_PER_PAGE;
	}

	public int getReqPage() {
		return reqPage;
	}

	public void setReqPage(int reqPage) {
		this.reqPage = reqPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	
	//시작레코드
	//(요청페이지 - 1) * 한페이지에 보여줄 레코드 수 +1
	public int getStartRec() {
		return (this.reqPage-1) * this.NUM_PER_PAGE+1;
	}
	
	//종료레코드
	//요청페이지 * 한페이지에 보여줄 레코드 수
	public int getEndRec() {
		return this.reqPage*this.numPerPage;
	}
	
	@Override
	public String toString() {
		return "RecordCriteria [reqPage=" + reqPage + ", numPerPage=" + numPerPage + ", NUM_PER_PAGE=" + NUM_PER_PAGE + "]";
	}
	
}
