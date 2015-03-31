package com.orga.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.AssignmentDAO;
import com.orga.dao.ClassInfoDAO;
import com.orga.dao.CourseInfoDAO;
import com.orga.dao.CourseScheduleDAO;
import com.orga.dao.StudentDAO;
import com.orga.dao.TeacherDAO;
import com.orga.dao.TermInfoDAO;
import com.orga.dao.TestInfoDAO;
import com.orga.domain.Assignment;
import com.orga.domain.ClassInfo;
import com.orga.domain.CourseInfo;
import com.orga.domain.CourseSchedule;
import com.orga.domain.Student;
import com.orga.domain.Teacher;
import com.orga.domain.TermInfo;
import com.orga.domain.TestInfo;
import com.orga.utils.CommConst;
import com.orga.utils.CommUtil;

public class CourseScheduleAction extends ActionSupport{
	private static final long serialVersionUID = 1909516591154687859L;
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
    /*界面查询的对象*/
    private int id;
    private ClassInfo classInfo;
    private TermInfo termInfo;
    private Teacher teacherInfo;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ClassInfo getClassInfo() {
		return classInfo;
	}
	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
	public TermInfo getTermInfo() {
		return termInfo;
	}
	public void setTermInfo(TermInfo termInfo) {
		this.termInfo = termInfo;
	}
	public Teacher getTeacherInfo() {
		return teacherInfo;
	}
	public void setTeacherInfo(Teacher teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	/*业务层对象*/
    private CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
    /*待操作的CourseSchedule对象*/
    private CourseSchedule courseSchedule;
	public CourseSchedule getCourseSchedule() {
		return courseSchedule;
	}
	public void setCourseSchedule(CourseSchedule courseSchedule) {
		this.courseSchedule = courseSchedule;
	}
	/**
	 * for teacher and Charge
	 * @return
	 */
	public String CourseScheduleOverall() {
		ActionContext ctx = ActionContext.getContext();
		String loginTeacherNumber = (String)ctx.getSession().get(CommConst.USER_ID);
		if (currentPage == 0) currentPage = 1;
		try {
			List<CourseSchedule> courseScheduleList = courseScheduleDAO.QueryCourseSchedule(0,
					null, null, null, loginTeacherNumber);
			ctx.put("courseScheduleList", courseScheduleList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		ServletRequest request=ServletActionContext.getRequest();
    	String iframeFlag = request.getParameter("iframe");
    	DumpMsg("iframe=" + iframeFlag);
    	if(CommUtil.isNotNull(iframeFlag)) {
    		return "cs_overall";
    	} else {
    		return "cs_index";
    	}
	}
	
	public String CourseScheduleDetail() {
		CourseSchedule courseSchedule = courseScheduleDAO.GetCourseScheduleById(id);
		ActionContext ctx = ActionContext.getContext();
		ctx.put("courseSchedule", courseSchedule);
		return "cs_detail";
	}
	
	/**
	 * for admin
	 * @return
	 */
	public String QueryCourseScheduleList() {
		ActionContext ctx = ActionContext.getContext();
		String schoolNumber = (String)ctx.getSession().get("schoolNumber");
		if (currentPage == 0) currentPage = 1;
		String classNumber = null, termNumber = null, teacherNumber = null;
		if (classInfo != null) {
			classNumber = classInfo.getClassNumber();
		}
		if (termInfo != null) {
			termNumber = termInfo.getTermNumber();
		}
		if (teacherInfo != null) {
			teacherNumber = teacherInfo.getTeacherNumber();
		}
		try {
			List<CourseSchedule> courseScheduleList = courseScheduleDAO.QueryCourseSchedule(schoolNumber, 
					classNumber, null, termNumber, teacherNumber, currentPage);
			courseScheduleDAO.CalculateTotalPageAndRecordNumber(classNumber, null, termNumber, teacherNumber);
			totalPage = courseScheduleDAO.getTotalPage();
			recordNumber = courseScheduleDAO.getRecordNumber();
			
			ctx.put("courseScheduleList", courseScheduleList);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		TermInfoDAO termInfoDAO = new TermInfoDAO();
		ArrayList<TermInfo> termList = termInfoDAO.QueryAllTermInfo();

		ctx.put("currentPage", currentPage);
		ctx.put("totalPage", totalPage);
		ctx.put("recordNumber", recordNumber);
		ctx.put("termList", termList);

		return "query_list_view";
	}
	
	public String AddView() {
		ActionContext ctx = ActionContext.getContext();
		String schoolNumber = (String)ctx.getSession().get("schoolNumber");
		
		try {
			ClassInfoDAO classInfoDAO = new ClassInfoDAO();
			ArrayList<ClassInfo> classInfoList = classInfoDAO.QueryClassInfo(null, schoolNumber, null, null);
			ctx.put("classInfoList", classInfoList);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
		ArrayList<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfo();
		ctx.put("courseInfoList", courseInfoList);
		
		TermInfoDAO termInfoDAO = new TermInfoDAO();
		ArrayList<TermInfo> termInfoList = termInfoDAO.QueryAllTermInfo();
		ctx.put("termInfoList", termInfoList);
		
		TeacherDAO teacherDAO = new TeacherDAO();
		teacherDAO.QueryTeacherInfo(null, schoolNumber);
		
		return "add_view";
	}
	
	public String AddCourseSchedule() {
		ActionContext ctx = ActionContext.getContext();
		try {
			ClassInfoDAO classInfoDAO = new ClassInfoDAO();
			ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNumber(courseSchedule.getClassInfo().getClassNumber());
			courseSchedule.setClassInfo(classInfo);
			
			CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
			CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseSchedule.getCourseInfo().getCourseNumber());
			courseSchedule.setCourseInfo(courseInfo);
			
			TermInfoDAO termInfoDAO = new TermInfoDAO();
			TermInfo termInfo = termInfoDAO.GetTermInfoByTermNumber(courseSchedule.getTermInfo().getTermNumber());
			courseSchedule.setTermInfo(termInfo);
			
			TeacherDAO teacherDAO = new TeacherDAO();
			Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(courseSchedule.getTeacherInfo().getTeacherNumber());
			courseSchedule.setTeacherInfo(teacher);
			
			courseScheduleDAO.AddCourseSchedule(courseSchedule);
			ctx.put("message",  java.net.URLEncoder.encode("CourseSchedule添加成功!"));
            return "add_success";
		} catch (Exception e) {
			e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseSchedule添加失败!"));
            return "error";
		}
	}

	public String ModifyCourseScheduleQuery() {
		CourseSchedule courseSchedule = courseScheduleDAO.GetCourseScheduleById(id);
		ActionContext ctx = ActionContext.getContext();
		ctx.put("courseSchedule", courseSchedule);
		return "modify_view";
	}
	
	public String ModifyCourseSchedule() {
		ActionContext ctx = ActionContext.getContext();
		try {
			ClassInfoDAO classInfoDAO = new ClassInfoDAO();
			ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNumber(courseSchedule.getClassInfo().getClassNumber());
			courseSchedule.setClassInfo(classInfo);
			
			CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
			CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseSchedule.getCourseInfo().getCourseNumber());
			courseSchedule.setCourseInfo(courseInfo);
			
			TermInfoDAO termInfoDAO = new TermInfoDAO();
			TermInfo termInfo = termInfoDAO.GetTermInfoByTermNumber(courseSchedule.getTermInfo().getTermNumber());
			courseSchedule.setTermInfo(termInfo);
			
			TeacherDAO teacherDAO = new TeacherDAO();
			Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(courseSchedule.getTeacherInfo().getTeacherNumber());
			courseSchedule.setTeacherInfo(teacher);
			
			courseScheduleDAO.UpdateCourseSchedule(courseSchedule);
			ctx.put("message",  java.net.URLEncoder.encode("CourseSchedule信息更新成功!"));
            return "modify_success";
		} catch (Exception ex) {
			ex.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseSchedule信息更新失败!"));
            return "error";
		}
	}
	
	/**
	 * Show course schedule details
	 * @return
	 */
	public String CourseScheduleQueryDetail() {
		ActionContext ctx = ActionContext.getContext();
		try {
			CourseSchedule courseSchedule = courseScheduleDAO.GetCourseScheduleById(id);
			ctx.put("courseSchedule", courseSchedule);
			
			StudentDAO studentDAO = new StudentDAO();
			ArrayList<Student> classStudentList = (ArrayList<Student>) studentDAO
					.QueryStudentList(null, null, courseSchedule.getClassInfo().getClassNumber());
			ctx.put("classStudentCount", classStudentList.size());
			
			AssignmentDAO assignmentDAO = new AssignmentDAO();
			ArrayList<Assignment> assignmentList = assignmentDAO.QueryAssignments(null, courseSchedule.getId());
			ctx.put("assignmentCount", assignmentList.size());

			TestInfoDAO testInfoDAO = new TestInfoDAO();
			ArrayList<TestInfo> testInfoList = testInfoDAO.QueryTestInfo(null, courseSchedule.getId());
			ctx.put("testInfoCount", testInfoList.size());
			
			return "cs_detail";
		} catch (Exception ex) {
			ex.printStackTrace();
			ctx.put("error",  java.net.URLEncoder.encode("查询课程相关信息失败!"));
            return "error";
		}
	}
	
	public String DeleteCourseSchedule() {
		ActionContext ctx = ActionContext.getContext();
        try { 
        	courseScheduleDAO.DeleteCourseSchedule(id);
            ctx.put("message",  java.net.URLEncoder.encode("CourseSchedule删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseSchedule删除失败!"));
            return "error";
        }
	}
	
	private void DumpMsg(String smg) {
		System.out.println(CommUtil.getCurrentDateTimeStr() + " [CourseScheduleAction] " + smg);
	}
	
}
