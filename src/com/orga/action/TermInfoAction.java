package com.orga.action;

import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.TermInfoDAO;
import com.orga.domain.TermInfo;

public class TermInfoAction extends ActionSupport{
	/*�����ѯ�Ķ���*/
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
	
	/*ҵ������*/
    private TermInfoDAO termInfoDAO = new TermInfoDAO();
    /*��������CourseSchedule����*/
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
