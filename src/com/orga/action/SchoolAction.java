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

    private String schoolNumber;
    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }
    public String getSchoolNumber() {
        return schoolNumber;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    SchoolDAO schoolDAO = new SchoolDAO();

    /*待操作的School对象*/
    private School school;
    public void setSchool(School info) {
        this.school = info;
    }
    public School getSchool() {
        return this.school;
    }
    
    /*跳转到添加School视图  left导航到这*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        //上级主管部门信息
        return "add_view";
    }

    /*添加School信息*/
    @SuppressWarnings("deprecation")
    public String AddSchool() {
        ActionContext ctx = ActionContext.getContext();
        /*验证学校编号是否已经存在*/
        school.setSchoolNumber(HibernateUtil.generateRecordId("SCH"));
        String schoolNumber = school.getSchoolNumber();
        School db_school = schoolDAO.GetSchoolByNumber(schoolNumber);
        if(null != db_school) {
            ctx.put("error",  java.net.URLEncoder.encode("该学校编号已经存在!"));
            return "error";
        }
        try {
            schoolDAO.AddSchool(school);
            ctx.put("message",  java.net.URLEncoder.encode("School添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("School添加失败!"));
            return "error";
        }
    }

    /*查询School信息*/
    public String QuerySchool() {
        if(currentPage == 0) currentPage = 1;
        List<School> schoolList = schoolDAO.QuerySchoolInfo(currentPage);
        /*计算总的页数和总的记录数*/
        schoolDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = schoolDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = schoolDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schoolList",  schoolList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*前台查询School信息*/
    public String FrontQuerySchool() {
        if(currentPage == 0) currentPage = 1;
        List<School> schoolList = schoolDAO.QuerySchoolInfo(currentPage);
        /*计算总的页数和总的记录数*/
        schoolDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = schoolDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = schoolDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schoolList",  schoolList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*
     * 跳转到modify页面
     * 查询要修改的School信息*/
    public String ModifySchoolQuery() {
        ActionContext ctx = ActionContext.getContext();
        
        /*根据主键schoolNumber获取School对象*/
        School school = schoolDAO.GetSchoolByNumber(schoolNumber);
        dumpMsg("ModifySchoolQuery :: " + school.toString());
        ctx.put("school",  school);
        return "modify_view";
    }

    /*查询要修改的School信息*/
    public String FrontShowSchoolQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键schoolNumber获取School对象*/
        School school = schoolDAO.GetSchoolByNumber(schoolNumber);

        ctx.put("school",  school);
        return "front_show_view";
    }

    /*更新修改School信息*/
    public String ModifySchool() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	dumpMsg("ModifySchool :: " + school.toString());
            schoolDAO.UpdateSchool(school);
            ctx.put("message",  java.net.URLEncoder.encode("School信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("School信息更新失败!"));
            return "error";
       }
   }

    /*删除School信息*/
    public String DeleteSchool() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            schoolDAO.DeleteSchool(schoolNumber);
            ctx.put("message",  java.net.URLEncoder.encode("School删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("School删除失败! 可能存在与此记录关联的数据。"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [SchoolAction] " + msg);
    }

}
