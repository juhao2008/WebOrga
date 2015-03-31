package com.orga.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.CourseInfoDAO;
import com.orga.domain.CourseInfo;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class CourseInfoAction extends ActionSupport {

    /*�������Ҫ��ѯ������: �γ̱��*/
    private String courseNumber;
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public String getCourseNumber() {
        return this.courseNumber;
    }

    /*�������Ҫ��ѯ������: �γ�����*/
    private String courseName;
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseName() {
        return this.courseName;
    }

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

    /*ҵ������*/
    private CourseInfoDAO courseInfoDAO = new CourseInfoDAO();

    /*��������CourseInfo����*/
    private CourseInfo courseInfo;
    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }
    public CourseInfo getCourseInfo() {
        return this.courseInfo;
    }

    /*��ת�����CourseInfo��ͼ*/
    public String AddView() {
    	dumpMsg("AddView");
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���CourseInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCourseInfo() {
    	dumpMsg("AddCourseInfo");
        ActionContext ctx = ActionContext.getContext();
        courseInfo.setCourseNumber(HibernateUtil.generateRecordId("KC"));
        /*��֤�γ̱���Ƿ��Ѿ�����*/
        String courseNumber = courseInfo.getCourseNumber();
        CourseInfo db_courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);
        if(null != db_courseInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�ÿγ̱���Ѿ�����!"));
            return "error";
        }
        try {
            courseInfoDAO.AddCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCourseInfo��Ϣ*/
    public String QueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseNumber", courseNumber);
        ctx.put("courseName", courseName);
        return "query_view";
    }

    /*ǰ̨��ѯCourseInfo��Ϣ*/
    public String FrontQueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseNumber", courseNumber);
        ctx.put("courseName", courseName);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�CourseInfo��Ϣ*/
    public String ModifyCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������courseNumber��ȡCourseInfo����*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);
        ctx.put("courseInfo",  courseInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�CourseInfo��Ϣ*/
    public String FrontShowCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������courseNumber��ȡCourseInfo����*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);

        ctx.put("courseInfo",  courseInfo);
        return "front_show_view";
    }

    /*�����޸�CourseInfo��Ϣ*/
    public String ModifyCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            courseInfoDAO.UpdateCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��CourseInfo��Ϣ*/
    public String DeleteCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            courseInfoDAO.DeleteCourseInfo(courseNumber);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfoɾ��ʧ��!"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [CourseInfoAction] " + msg);
    }

}
