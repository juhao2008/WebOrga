package com.orga.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.CourseScheduleDAO;
import com.orga.dao.ScoreInfoDAO;
import com.orga.domain.CourseSchedule;
import com.orga.domain.ScoreInfo;
import com.orga.dao.StudentDAO;
import com.orga.domain.Student;
import com.orga.dao.CourseInfoDAO;
import com.orga.domain.CourseInfo;
import com.orga.utils.CommUtil;

public class ScoreInfoAction extends ActionSupport {

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

    /*界面层需要查询的属性: 学生对象*/
    private Student student;
    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return this.student;
    }

    /*界面层需要查询的属性: 课程对象*/
    private CourseSchedule courseSchedule;
    public CourseSchedule getCourseSchedule() {
		return courseSchedule;
	}
	public void setCourseSchedule(CourseSchedule courseSchedule) {
		this.courseSchedule = courseSchedule;
	}
	private int scoreId;
    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }
    public int getScoreId() {
        return scoreId;
    }

	/*业务层对象*/
    private ScoreInfoDAO scoreInfoDAO = new ScoreInfoDAO();

    /*待操作的ScoreInfo对象*/
    private ScoreInfo scoreInfo;
    public void setScoreInfo(ScoreInfo scoreInfo) {
        this.scoreInfo = scoreInfo;
    }
    public ScoreInfo getScoreInfo() {
        return this.scoreInfo;
    }

    /*跳转到添加ScoreInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	/*查询所有的Student信息*/
            StudentDAO studentDAO = new StudentDAO();
            List<Student> studentList = studentDAO.QueryAllStudentInfo();
            ctx.put("studentList", studentList);
            /*查询所有的CourseInfo信息*/
            CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
            List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfo();
            ctx.put("courseInfoList", courseInfoList);
            return "add_view";
        } catch (Exception ex) {
        	ex.printStackTrace();
        	ctx.put("error",  java.net.URLEncoder.encode("查询成绩相关信息失败!"));
            return "error";
        }
    }

    /*添加ScoreInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student student  = studentDAO.GetStudentByNumber(scoreInfo.getStudent().getStudentNumber());
            scoreInfo.setStudent(student);
            
//            CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
//            CourseSchedule courseSchedule = courseScheduleDAO.GetCourseScheduleById(scoreInfo.getCourseSchedule().getId());
//            scoreInfo.setCourseSchedule(courseSchedule);
            
            scoreInfoDAO.AddScoreInfo(scoreInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo添加失败!"));
            return "error";
        }
    }

    /*查询要修改的ScoreInfo信息*/
    public String ModifyScoreInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	/*根据主键scoreId获取ScoreInfo对象*/
            ScoreInfo scoreInfo = scoreInfoDAO.GetScoreInfoByScoreId(scoreId);

            StudentDAO studentDAO = new StudentDAO();
            List<Student> studentList = studentDAO.QueryAllStudentInfo();
            ctx.put("studentList", studentList);
            CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
            List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfo();
            ctx.put("courseInfoList", courseInfoList);
            ctx.put("scoreInfo",  scoreInfo);
            return "modify_view";
        } catch (Exception ex) {
        	ex.printStackTrace();
        	ctx.put("error",  java.net.URLEncoder.encode(ex.getMessage()));
        	return "error";
        }
    }

    /*查询要修改的ScoreInfo信息*/
    public String FrontShowScoreInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	/*根据主键scoreId获取ScoreInfo对象*/
            ScoreInfo scoreInfo = scoreInfoDAO.GetScoreInfoByScoreId(scoreId);

            StudentDAO studentDAO = new StudentDAO();
            List<Student> studentList = studentDAO.QueryAllStudentInfo();
            ctx.put("studentList", studentList);
            CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
            List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfo();
            ctx.put("courseInfoList", courseInfoList);
            ctx.put("scoreInfo",  scoreInfo);
            return "front_show_view";
        } catch (Exception ex) {
        	ex.printStackTrace();
        	ctx.put("error",  java.net.URLEncoder.encode(ex.getMessage()));
        	return "error";
        }
    }

    /*更新修改ScoreInfo信息*/
    public String ModifyScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	StudentDAO studentDAO = new StudentDAO();
            Student studentNumber = studentDAO.GetStudentByNumber(scoreInfo.getStudent().getStudentNumber());
            scoreInfo.setStudent(studentNumber);
            
//            CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
//            CourseSchedule courseSchedule = courseScheduleDAO.GetCourseScheduleById(scoreInfo.getCourseSchedule().getId());
//            scoreInfo.setCourseSchedule(courseSchedule);
            
            scoreInfoDAO.UpdateScoreInfo(scoreInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除ScoreInfo信息*/
    public String DeleteScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            scoreInfoDAO.DeleteScoreInfo(scoreId);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo删除失败!"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [ScoreInfoAction] " + msg);
    }

}
