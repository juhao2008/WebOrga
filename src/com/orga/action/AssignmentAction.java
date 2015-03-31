package com.orga.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.AssignmentDAO;
import com.orga.dao.CourseScheduleDAO;
import com.orga.dao.StudentDAO;
import com.orga.domain.CourseSchedule;
import com.orga.domain.Student;
import com.orga.domain.Assignment;
import com.orga.utils.CommConst;
import com.orga.utils.CommUtil;

public class AssignmentAction extends ActionSupport{

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
    
    //������ѯ����
    private int assignmentId; 
    public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	private String assignmentName;
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	private CourseSchedule courseSchedule;
	public CourseSchedule getCourseSchedule() {
		return courseSchedule;
	}
	public void setCourseSchedule(CourseSchedule courseSchedule) {
		this.courseSchedule = courseSchedule;
	}
	//end

    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    
    /*��������Assignment����*/
    private Assignment assignment;
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	/*��ת�����CourseSchedule��ͼ*/
    public String AddView() {
    	dumpMsg("AddView");
        ActionContext ctx = ActionContext.getContext();
        
        String userRole = (String)ctx.getSession().get(CommConst.USER_ROLE);
        String userNumber = (String)ctx.getSession().get(CommConst.USER_NAME);
        String classNumber = (String)ctx.getSession().get("classNumber");;
        
        CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
        try {
        	 ArrayList<CourseSchedule> courseScheduleList = courseScheduleDAO.QueryCourseSchedule(0, classNumber, null, null, userNumber);
             ctx.put("courseScheduleList", courseScheduleList);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return "add_view";
    }
	
	/*���Assignment��Ϣ*/
    public String AddAssignment() {
    	ActionContext ctx = ActionContext.getContext();
        try {
            CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
            CourseSchedule courseInfo = courseScheduleDAO.GetCourseScheduleById(assignment.getCourseSchedule().getId());
            assignment.setCourseSchedule(courseInfo);
            
            assignmentDAO.AddAssignment(assignment);
            ctx.put("message",  java.net.URLEncoder.encode("Assignment��ӳɹ�!","gbk"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            try {
				ctx.put("error",  java.net.URLEncoder.encode("Assignment���ʧ��!","gbk"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
            return "error";
        }
    }
	
	/*��ѯAssignment��Ϣ*/
    public String QueryAssignment() {
    	if(currentPage == 0) currentPage = 1;
    	if(assignmentName == null)
    		assignmentName = "";
    	int courseScheduleId = 0;
    	if(courseSchedule != null) {
    		courseScheduleId = courseSchedule.getId();
    	}
        List<Assignment> assignmentList = assignmentDAO.QueryAssignments(assignmentName, courseScheduleId, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        assignmentDAO.CalculateTotalPageAndRecordNumber(assignmentName, courseScheduleId);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = assignmentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = assignmentDAO.getRecordNumber();
        
        List<CourseSchedule> courseScheduleList = new ArrayList<CourseSchedule>();
        for(Assignment item : assignmentList) {
        	if(!courseScheduleList.contains(item.getCourseSchedule()))
        		courseScheduleList.add(item.getCourseSchedule());
        }
        ActionContext ctx = ActionContext.getContext();
        ctx.put("assignmentList",  assignmentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        //�����ѯ����
        ctx.put("courseScheduleList",  courseScheduleList);
        ctx.put("assignmentName", assignmentName);
        ctx.put("courseScheduleId",  courseScheduleId);//for option 'selected'
        return "query_list";
    }
    
    public String ModifyView() {
    	Assignment assignmentData = assignmentDAO.GetAssignmentById(assignmentId);
    	ActionContext ctx = ActionContext.getContext();
    	ctx.put("assignmentData", assignmentData);
    	
    	return "modify_view";
    }

	public String ModifyAssignment() {
		ActionContext ctx = ActionContext.getContext();
		try {
			CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
            CourseSchedule courseInfo = courseScheduleDAO.GetCourseScheduleById(assignment.getCourseSchedule().getId());
            assignment.setCourseSchedule(courseInfo);
            
			assignmentDAO.UpdateAssignment(assignment);
			ctx.put("message", java.net.URLEncoder.encode("CourseSchedule��Ϣ���³ɹ�!", "gbk"));
			return "modify_success";
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				ctx.put("error", java.net.URLEncoder.encode("CourseSchedule��Ϣ����ʧ��!", "gbk"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return "error";
		}
	}
    
    public String DeleteAssignment() {
    	ActionContext ctx = ActionContext.getContext();
    	try {
			assignmentDAO.DeleteAssignment(assignmentId);
			ctx.put("message",  java.net.URLEncoder.encode("��ҵɾ���ɹ�!", "gbk"));
            return "delete_success";
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ctx.put("error",  java.net.URLEncoder.encode("��ҵɾ��ʧ��!", "gbk"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
            return "error";
		}
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [AssignmentAction] " + msg);
    }
	
}
