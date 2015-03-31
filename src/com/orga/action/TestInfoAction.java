package com.orga.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.CourseScheduleDAO;
import com.orga.dao.StudentDAO;
import com.orga.dao.TestInfoDAO;
import com.orga.domain.CourseSchedule;
import com.orga.domain.CourseScheduleStudent;
import com.orga.domain.CourseTestInfo;
import com.orga.domain.Student;
import com.orga.domain.TestInfo;
import com.orga.utils.CommConst;
import com.orga.utils.CommUtil;

public class TestInfoAction extends ActionSupport{
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
    
    //界面显询条件
    private String testTitle;
    public String getTestTitle() {
		return testTitle;
	}
	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}
	private CourseSchedule courseSchedule;
	public CourseSchedule getCourseSchedule() {
		return courseSchedule;
	}
	public void setCourseSchedule(CourseSchedule courseSchedule) {
		this.courseSchedule = courseSchedule;
	}

	/*待操作的TestInfo对象*/
    private TestInfo testInfo;
    public TestInfo getTestInfo() {
		return testInfo;
	}
	public void setTestInfo(TestInfo testInfo) {
		this.testInfo = testInfo;
	}
	private TestInfoDAO testInfoDAO = new TestInfoDAO();
	
	public String QueryTestInfoIndex() {
		ActionContext ctx = ActionContext.getContext();
		CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
		try {
			String loginTeacherNumber = (String)ctx.getSession().get(CommConst.USER_ID);
			List<CourseSchedule> courseScheduleList = courseScheduleDAO.QueryCourseSchedule(0,
					null, null, null, loginTeacherNumber);
			ctx.put("courseScheduleList", courseScheduleList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return "query_index";
	}
	
	public String QueryTestInfoOverall() {
		ActionContext ctx = ActionContext.getContext();
		try {
			CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
			String loginTeacherNumber = (String)ctx.getSession().get(CommConst.USER_ID);
			List<CourseSchedule> courseScheduleList = courseScheduleDAO.QueryCourseSchedule(0,
					null, null, null, loginTeacherNumber);
			ctx.put("courseScheduleList", courseScheduleList);
			
			List<CourseTestInfo> courseTestList = new ArrayList<CourseTestInfo>();
			for(CourseSchedule courseSchedule : courseScheduleList) {
				int courseScheduleId = courseSchedule.getId();
				List<TestInfo> testInfoList = testInfoDAO.QueryTestInfo(null, courseScheduleId);
				
				CourseTestInfo courseTestInfo = new CourseTestInfo();
				courseTestInfo.setCourseSchedule(courseSchedule);
				courseTestInfo.setTestCount(testInfoList.size());
				courseTestList.add(courseTestInfo);
			}
	    	ctx.put("courseTestList", courseTestList);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return "query_overall";
	}
	
	public String QueryTestInfo() {
		this.dumpMsg("QueryTestInfo.........");
		if(currentPage == 0) currentPage = 1;
		if(testTitle == null) testTitle = "";
		int courseScheduleId = 0;
    	if(courseSchedule != null) {
    		courseScheduleId = courseSchedule.getId();
    	}
    	ActionContext ctx = ActionContext.getContext();
    	try {
    		List<TestInfo> testInfoList = testInfoDAO.QueryTestInfo(testTitle, courseScheduleId, currentPage);
        	testInfoDAO.CalculateTotalPageAndRecordNumber(testTitle, courseScheduleId);
        	totalPage = testInfoDAO.getTotalPage();
        	recordNumber = testInfoDAO.getRecordNumber();
        	
        	List<CourseSchedule> courseScheduleList = new ArrayList<CourseSchedule>();
        	for(TestInfo testInfo : testInfoList) {
        		if(!courseScheduleList.contains(testInfo.getCourseSchedule())) {
        			courseScheduleList.add(testInfo.getCourseSchedule());
        		}
        	}
        	
        	ctx.put("courseScheduleList", courseScheduleList);
        	ctx.put("testInfoList", testInfoList);
        	
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	ctx.put("testTitle", testTitle);
    	ctx.put("courseScheduleId", courseScheduleId);
    	
    	ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
		
		return "query_list";
	}
	
	public String QueryTestCourseSchedule() {
		
		return "";
	}
	
	public String AddView() {
		ActionContext ctx = ActionContext.getContext();
		try {
			String teacherNumber = (String)ctx.getSession().get(CommConst.USER_ID);
			CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
	        ArrayList<CourseSchedule> courseScheduleList = courseScheduleDAO.QueryCourseSchedule(0, null, null, null, teacherNumber);
	        ctx.put("courseScheduleList", courseScheduleList);
	        
	        List<CourseScheduleStudent> courseScheduleStudentList = new ArrayList<CourseScheduleStudent>();
	        for(CourseSchedule schedule : courseScheduleList) {
	        	String classNumber = schedule.getClassInfo().getClassNumber();
	        	StudentDAO studentDao = new StudentDAO();
	        	List<Student> studentList = studentDao.QueryStudentList(null, null, classNumber);
	        	
	        	CourseScheduleStudent tmp = new CourseScheduleStudent(schedule, studentList);
	        	courseScheduleStudentList.add(tmp);
	        }
	        ctx.put("courseScheduleStudentList", courseScheduleStudentList);
	        
			return "add_view";
		} catch (Exception ex) {
			ex.printStackTrace();
			ctx.put("error",  java.net.URLEncoder.encode(ex.getMessage()));
        	return "error";
		}
	}
	
	public String AddTestInfo() {
		
		return "";
	}
	
	private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [TestInfoAction] " + msg);
    }
}
