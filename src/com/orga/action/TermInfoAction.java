package com.orga.action;

import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.TermInfoDAO;
import com.orga.domain.TermInfo;

public class TermInfoAction extends ActionSupport{
	/*界面查询的对象*/
    private int id;
    private String termName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	
	/*业务层对象*/
    private TermInfoDAO termInfoDAO = new TermInfoDAO();
    /*待操作的CourseSchedule对象*/
	private TermInfo termInfo;
	public TermInfo getTermInfo() {
		return termInfo;
	}
	public void setTermInfo(TermInfo termInfo) {
		this.termInfo = termInfo;
	}
    
	public String AddView() {
		return "add_view";
	}
	
	public String AddTermInfo() {
		return "add_success";
	}
	
	public String QueryTermList() {
		
		return "query_list_view";
	}
	
	public String ModifyTermInfoQuery() {
		
		return "modify_view";
	}

	public String ModifyTermInfo() {
		return "modify_success";
	}
	
	public String DeleteTermInfo() {
		return "delete_success";
	}
}
