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

    /*�������Ҫ��ѯ������: ѧ������*/
    private Student student;
    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return this.student;
    }

    /*�������Ҫ��ѯ������: �γ̶���*/
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

	/*ҵ������*/
    private ScoreInfoDAO scoreInfoDAO = new ScoreInfoDAO();

    /*��������ScoreInfo����*/
    private ScoreInfo scoreInfo;
    public void setScoreInfo(ScoreInfo scoreInfo) {
        this.scoreInfo = scoreInfo;
    }
    public ScoreInfo getScoreInfo() {
        return this.scoreInfo;
    }

    /*��ת�����ScoreInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	/*��ѯ���е�Student��Ϣ*/
            StudentDAO studentDAO = new StudentDAO();
            List<Student> studentList = studentDAO.QueryAllStudentInfo();
            ctx.put("studentList", studentList);
            /*��ѯ���е�CourseInfo��Ϣ*/
            CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
            List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfo();
            ctx.put("courseInfoList", courseInfoList);
            return "add_view";
        } catch (Exception ex) {
        	ex.printStackTrace();
        	ctx.put("error",  java.net.URLEncoder.encode("��ѯ�ɼ������Ϣʧ��!"));
            return "error";
        }
    }

    /*���ScoreInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯҪ�޸ĵ�ScoreInfo��Ϣ*/
    public String ModifyScoreInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	/*��������scoreId��ȡScoreInfo����*/
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

    /*��ѯҪ�޸ĵ�ScoreInfo��Ϣ*/
    public String FrontShowScoreInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	/*��������scoreId��ȡScoreInfo����*/
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

    /*�����޸�ScoreInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ScoreInfo��Ϣ*/
    public String DeleteScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            scoreInfoDAO.DeleteScoreInfo(scoreId);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfoɾ��ʧ��!"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [ScoreInfoAction] " + msg);
    }

}
