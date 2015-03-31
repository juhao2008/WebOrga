package com.orga.action;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.ParentDAO;
import com.orga.dao.StudentDAO;
import com.orga.domain.Parent;
import com.orga.domain.Student;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class ParentAction extends ActionSupport {
	
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

    /*�������Ҫ��ѯ������: ѧ����Ϣ*/
    private String parentNumber;
    public String getParentNumber() {
		return parentNumber;
	}
	public void setParentNumber(String parentNumber) {
		this.parentNumber = parentNumber;
	}
	
	private String studentNumber;
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	/*�������Ҫ��ѯ������: ѧ����Ϣ*/
	private Student student;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	/*��������Parent����*/
	private Parent parent;
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	private ParentDAO parentDAO = new ParentDAO();
	public String AddView() {
		ActionContext ctx = ActionContext.getContext();
		try {
			StudentDAO studentDAO = new StudentDAO();
			Student student = studentDAO.GetStudentByNumber(studentNumber);
			ctx.put("student", student);
			
			ParentDAO parentDAO = new ParentDAO();
			ArrayList<Parent> parentList = parentDAO.QueryStudentParent(studentNumber, null);
			ctx.put("parentList", parentList);
			
			return "add_view";
		} catch (Exception ex) {
			ex.printStackTrace();
			ctx.put("error",  java.net.URLEncoder.encode("��ѯѧ�������Ϣʧ��!"));
            return "error";
		}
	}
	
	public String AddAndModifyView() {
		ActionContext ctx = ActionContext.getContext();
		try {
			ParentDAO parentDAO = new ParentDAO();
			ArrayList<Parent> parentList = parentDAO.QueryStudentParent(studentNumber, null);
			if(parentList != null && parentList.size() > 0) {
				ctx.put("parentList", parentList);
				return "modify_view";
			} else {
				StudentDAO studentDAO = new StudentDAO();
				Student student = studentDAO.GetStudentByNumber(studentNumber);
				ctx.put("student", student);
				return "add_view";
			}
		} catch (Exception ex) {
			ctx.put("error",  java.net.URLEncoder.encode("���ѧ�������Ϣʧ��!"));
            return "error";
		}
	}
	
	public String AddParent() {
		DumpMsg("AddParent");
		ActionContext ctx = ActionContext.getContext();
		parent.setParentNumber(HibernateUtil.generateRecordId("JZ"));
		
		String parentNumber = parent.getParentNumber();
		Parent parentDB = parentDAO.QueryStudentParent(parentNumber);
		if(null != parentDB) {
            ctx.put("error",  java.net.URLEncoder.encode("�üҳ�����Ѿ�����!"));
            return "error";
        }
		try {
			StudentDAO studentDAO = new StudentDAO();
			Student student = studentDAO.GetStudentByNumber(parent.getStudent().getStudentNumber());
			parent.setStudent(student);
			
			parentDAO.AddStudentParent(parent);
			ctx.put("message",  java.net.URLEncoder.encode("Parent��ӳɹ�!"));
			return "add_success";
		}catch(Exception ex) {
			ex.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Parent���ʧ��!"));
            return "error";
		}
	}
	
	public String QueryParentListView() {
		
		return "query_view";
	}
	
	public String ModifyParentQuery() {
		ActionContext ctx = ActionContext.getContext();
		Parent parent = parentDAO.QueryStudentParent(parentNumber);
		ctx.put("parent", parent);
		return "modify_view";
	}
	
	public String ModifyParent() {
		DumpMsg("ModifyParent");
		ActionContext ctx = ActionContext.getContext();
		try {
			StudentDAO studentDAO = new StudentDAO();
			Student student = studentDAO.GetStudentByNumber(parent.getStudent().getStudentNumber());
			parent.setStudent(student);
			
			parentDAO.UpdateParent(parent);
			ctx.put("message",  java.net.URLEncoder.encode("Parent��Ϣ���³ɹ�!"));
			return "modify_success";
		} catch (Exception ex) {
			ex.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Parent��Ϣ����ʧ��!"));
            return "error";
		}
	}
	
	public String DeleteParent() {
		return "delete_success";
	}
	
	private void DumpMsg(String msg) {
		System.out.println(CommUtil.getCurrentDateTimeStr() + " [ ParentAction] " + msg);
	}
	
}
