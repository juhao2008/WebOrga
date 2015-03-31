package com.orga.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.NotifyDAO;
import com.orga.domain.News;
import com.orga.domain.School;
import com.orga.domain.ClassInfo;
import com.orga.domain.Notify;

public class NotifyAction extends ActionSupport{
	/*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }
    /*界面层需要查询的属性: 通知ID*/
	private int notifyId;
	public int getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}

	/*界面层需要查询的属性: 通知标题*/
	private String notifyTitle;
	
	/*界面层需要查询的属性: 通知日期*/
	private String notifyDate;
	/*界面层需要查询的属性: 通知对象-学校*/
	private School notifySchool;
	/*界面层需要查询的属性: 通知对象-班级*/
	private ClassInfo notifyClass;
	
	public String getNotifyTitle() {
		return notifyTitle;
	}

	public void setNotifyTitle(String notifyTitle) {
		this.notifyTitle = notifyTitle;
	}

	public String getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}

	public School getNotifySchool() {
		return notifySchool;
	}

	public void setNotifySchool(School notifySchool) {
		this.notifySchool = notifySchool;
	}

	public ClassInfo getNotifyClass() {
		return notifyClass;
	}

	public void setNotifyClass(ClassInfo notifyClass) {
		this.notifyClass = notifyClass;
	}
	
	/*待操作的临时对象*/
	private Notify notify;
	public Notify getNotify() {
		return notify;
	}
	public void setNotify(Notify notify) {
		this.notify = notify;
	}
	private NotifyDAO notifyDAO = new NotifyDAO();
	
	
	public String QueryNotify() {
		if(currentPage == 0) currentPage = 1;
        if(notifyTitle == null) notifyTitle = "";
        String classNumber = null;
        if(notifyClass != null)
        	classNumber = notifyClass.getClassNumber();
        
        List<Notify> notifyList = notifyDAO.QueryNotifyInfo(notifyTitle, classNumber, notifyDate, currentPage);
        notifyDAO.CalculateTotalPageAndRecordNumber(notifyTitle, classNumber, notifyDate);
        totalPage = notifyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = notifyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("notifyList",  notifyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("notifyTitle", notifyTitle);
        ctx.put("notifyDate", notifyDate);
		return "query_view";
	}
	
	public String AddView() {
		return "add_view";
	}
	
	public String AddNotify() {
		
		return "add_success";
	}
	
	public String ModifyNotifyView() {
		return "modify_view";
	}
	
	public String ModifyNotify() {
		return "modify_success";
	}
	
	public String DeleteNotify() {
		return "delete_success";
	}
	

}
