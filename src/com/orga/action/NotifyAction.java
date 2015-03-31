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
	/*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }
    /*�������Ҫ��ѯ������: ֪ͨID*/
	private int notifyId;
	public int getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}

	/*�������Ҫ��ѯ������: ֪ͨ����*/
	private String notifyTitle;
	
	/*�������Ҫ��ѯ������: ֪ͨ����*/
	private String notifyDate;
	/*�������Ҫ��ѯ������: ֪ͨ����-ѧУ*/
	private School notifySchool;
	/*�������Ҫ��ѯ������: ֪ͨ����-�༶*/
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
	
	/*����������ʱ����*/
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
        /*��ǰ��ѯ�������ܼ�¼��*/
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
