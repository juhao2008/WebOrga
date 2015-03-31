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
import com.orga.dao.SchoolDAO;
import com.orga.domain.School;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class SchoolAction extends ActionSupport {

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

    private String schoolNumber;
    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }
    public String getSchoolNumber() {
        return schoolNumber;
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
    SchoolDAO schoolDAO = new SchoolDAO();

    /*��������School����*/
    private School school;
    public void setSchool(School info) {
        this.school = info;
    }
    public School getSchool() {
        return this.school;
    }
    
    /*��ת�����School��ͼ  left��������*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        //�ϼ����ܲ�����Ϣ
        return "add_view";
    }

    /*���School��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSchool() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤ѧУ����Ƿ��Ѿ�����*/
        school.setSchoolNumber(HibernateUtil.generateRecordId("SCH"));
        String schoolNumber = school.getSchoolNumber();
        School db_school = schoolDAO.GetSchoolByNumber(schoolNumber);
        if(null != db_school) {
            ctx.put("error",  java.net.URLEncoder.encode("��ѧУ����Ѿ�����!"));
            return "error";
        }
        try {
            schoolDAO.AddSchool(school);
            ctx.put("message",  java.net.URLEncoder.encode("School��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("School���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSchool��Ϣ*/
    public String QuerySchool() {
        if(currentPage == 0) currentPage = 1;
        List<School> schoolList = schoolDAO.QuerySchoolInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        schoolDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = schoolDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = schoolDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schoolList",  schoolList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*ǰ̨��ѯSchool��Ϣ*/
    public String FrontQuerySchool() {
        if(currentPage == 0) currentPage = 1;
        List<School> schoolList = schoolDAO.QuerySchoolInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        schoolDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = schoolDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = schoolDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schoolList",  schoolList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*
     * ��ת��modifyҳ��
     * ��ѯҪ�޸ĵ�School��Ϣ*/
    public String ModifySchoolQuery() {
        ActionContext ctx = ActionContext.getContext();
        
        /*��������schoolNumber��ȡSchool����*/
        School school = schoolDAO.GetSchoolByNumber(schoolNumber);
        dumpMsg("ModifySchoolQuery :: " + school.toString());
        ctx.put("school",  school);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�School��Ϣ*/
    public String FrontShowSchoolQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������schoolNumber��ȡSchool����*/
        School school = schoolDAO.GetSchoolByNumber(schoolNumber);

        ctx.put("school",  school);
        return "front_show_view";
    }

    /*�����޸�School��Ϣ*/
    public String ModifySchool() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	dumpMsg("ModifySchool :: " + school.toString());
            schoolDAO.UpdateSchool(school);
            ctx.put("message",  java.net.URLEncoder.encode("School��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("School��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��School��Ϣ*/
    public String DeleteSchool() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            schoolDAO.DeleteSchool(schoolNumber);
            ctx.put("message",  java.net.URLEncoder.encode("Schoolɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Schoolɾ��ʧ��! ���ܴ�����˼�¼���������ݡ�"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [SchoolAction] " + msg);
    }

}
