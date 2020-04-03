package com.puercha.algo.common;


/**
 * 페이징
 * @author Yongmin
 * 현재 페이지에 보여줄 페이지 계산
 * 시작, 종료, 다음, 이전,  마지막 페이지, 처음페이지
 */
public class PageManager{
	
	//한 페이지에 보여줄 페이지수
	private int pageNumPerPage;
	
	//한 페이지의 시작페이지
	private int startPage;
	
	//한페이지의 종료 페이지
	private int endPage;
	
	//전체 레코드 수
	private long totalRec;
	
	//최종페이지
	private int finalEndPage;
	
	//이전페이지
	private boolean prev;
	
	//다음페이지
	private boolean next;
	
	//한페이지에 보여줄 레코드 수
	private RowCriteria rc;
	
	private final int PAGE_NUM_PER_PAGE = 10;
	
	public PageManager(RowCriteria rc, long totalRec) {
		this.rc = rc;
		this.totalRec = totalRec;
		this.pageNumPerPage = PAGE_NUM_PER_PAGE;
		initPaging();
	}
	
	public void initPaging() {
		//요청 페이지의 종료페이지 계산 : 올림(요청페이지/한페이지에보여줄 페이지수)*한페이지에보여줄 페이지수
		this.endPage = (int)Math.ceil((this.rc.getReqPage() / (double)this.pageNumPerPage)) * this.pageNumPerPage;
		
		//요청페이지의 시작페이지 계산 : 한페이지의 종료페이지 - 한페이지에 보여줄 페이지수 + 1
		this.startPage = this.endPage - this.pageNumPerPage + 1;
		
		//최종페이지 계산 : 올림(전체레코드수/한페이지에보여줄레코드수)
		this.finalEndPage = (int)Math.ceil((totalRec / (double)this.rc.getNumPerPage()));
		if(this.finalEndPage < this.endPage) {
			this.endPage = this.finalEndPage;
		}
		
	}
	
	public int getStartPage() {
		return this.startPage;
	}
	
	public int getEndPage() {
		return this.endPage;
	}
	
	public int getFinalEndpage() {
		return this.finalEndPage;
	}

	
	//이전 페이지 보여줄지 판단 : 요청페이지의 시작페이지 1이 아닌 경우만 보여줌
	public boolean isPrev() {
		return this.getStartPage() == 1 ? false:true;
	}
	
	//다음페이지 보여줄 지 판단 : 전체 레코드가 요청페이지의 종료페이지보다 큰 경우 보여줌
	public boolean isNext() {
		return this.totalRec > this.getEndPage() * rc.getNumPerPage() ? true:false;
	}
	
	public RowCriteria getRc() {
		return rc;
	}
	
	public FindCriteria getFC() {
		if(rc instanceof FindCriteria) {
			return (FindCriteria)rc;
		}
		return null;
	}
	@Override
	public String toString() {
		return "PageCriteria [pageNumPerPage=" + pageNumPerPage + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", totalRec=" + totalRec + ", finalEndPage=" + finalEndPage + ", prev=" + prev + ", next=" + next + ", rc="
				+ rc  + "]";
	}
}





