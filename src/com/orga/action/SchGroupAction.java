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
import com.orga.dao.SchGroupDAO;
import com.orga.domain.SchGroup;
import com.orga.dao.SchoolDAO;
import com.orga.domain.School;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class SchGroupAction extends ActionSupport {

    /*界面层需要查询的属性: 教研室编号*/
	private String schGroupNumber;
	
	public String getSchGroupNumber() {
		return schGroupNumber;
	}

	public void setSchGroupNumber(String schGroupNumber) {
		this.schGroupNumber = schGroupNumber;
	}

    /*界面层需要查询的属性: 教研室名称*/
    private String schGroupName;
    public String getSchGroupName() {
		return schGroupName;
	}

	public void setSchGroupName(String schGroupName) {
		this.schGroupName = schGroupName;
	}

    /*界面层需要查询的属性: 所在学校 */
    private School schGroupSchool;
    public School getSchGroupSchool() {
		return schGroupSchool;
	}

	public void setSchGroupSchool(School schGroupSchool) {
		this.schGroupSchool = schGroupSchool;
	}

    /*界面层需要查询的属性: 成立日期*/
    private String schGroupBirthDate;
    public String getSchGroupBirthDate() {
		return schGroupBirthDate;
	}

	public void setSchGroupBirthDate(String schGroupBirthDate) {
		this.schGroupBirthDate = schGroupBirthDate;
	}

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

    /*业务层对象*/
    SchGroupDAO schGroupDAO = new SchGroupDAO();

    /*待操作的SchGroup对象*/
    private SchGroup schGroup;
    public void setSchGroup(SchGroup schGroup) {
        this.schGroup = schGroup;
    }
    public SchGroup getSchGroup() {
        return this.schGroup;
    }

    /*跳转到添加SchGroup视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的SchGroup信息*/
        SchoolDAO schGroupDAO = new SchoolDAO();
        List<School> schoolList = schGroupDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        return "add_view";
    }

    /*添加SchGroup信息*/
    @SuppressWarnings("deprecation")
    public String AddSchGroup() {
        ActionContext ctx = ActionContext.getContext();
        /*验证教研組编号是否已经存在*/
        schGroup.setSchGroupNumber(HibernateUtil.generateRecordId("ORG"));
        String schGroupNumber = schGroup.getSchGroupNumber();
        SchGroup db_schGroup = schGroupDAO.GetSchGroupByNumber(schGroupNumber);
        if(null != db_schGroup) {
            ctx.put("error",  java.net.URLEncoder.encode("该教研室编号已经存在!"));
            return "error";
        }
        try {
            SchoolDAO schoolDAO = new SchoolDAO();
            School school = schoolDAO.GetSchoolByNumber(schGroup.getSchGroupSchool().getSchoolNumber());
            schGroup.setSchGroupSchool(school);
            
            schGroupDAO.AddSchGroup(schGroup);
            ctx.put("message",  java.net.URLEncoder.encode("SchGroup添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchGroup添加失败!"));
            return "error";
        }
    }

    /*查询SchGroup信息*/
    public String QuerySchGroup() {
        if(currentPage == 0) currentPage = 1;
        if(schGroupNumber == null) schGroupNumber = "";
        if(schGroupName == null) schGroupName = "";
        if(schGroupBirthDate == null) schGroupBirthDate = "";
        List<SchGroup> schGroupList = schGroupDAO.QuerySchGroupInfo(schGroupNumber, schGroupName, schGroupSchool, schGroupBirthDate, currentPage);
        /*计算总的页数和总的记录数*/
        schGroupDAO.CalculateTotalPageAndRecordNumber(schGroupNumber, schGroupName, schGroupSchool, schGroupBirthDate);
        /*获取到总的页码数目*/
        totalPage = schGroupDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = schGroupDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schGroupList",  schGroupList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("schGroupNumber", schGroupNumber);
        ctx.put("schGroupName", schGroupName);
        ctx.put("schGroupSchool", schGroupSchool);
        SchoolDAO schoolDAO = new SchoolDAO();
        
        List<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        ctx.put("schGroupBirthDate", schGroupBirthDate);
        return "query_view";
    }

    /*前台查询SchGroup信息*/
    public String FrontQuerySchGroup() {
        if(currentPage == 0) currentPage = 1;
        if(schGroupNumber == null) schGroupNumber = "";
        if(schGroupName == null) schGroupName = "";
        if(schGroupBirthDate == null) schGroupBirthDate = "";
        List<SchGroup> schGroupList = schGroupDAO.QuerySchGroupInfo(schGroupNumber, schGroupName, schGroupSchool, schGroupBirthDate, currentPage);
        /*计算总的页数和总的记录数*/
        schGroupDAO.CalculateTotalPageAndRecordNumber(schGroupNumber, schGroupName, schGroupSchool, schGroupBirthDate);
        /*获取到总的页码数目*/
        totalPage = schGroupDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = schGroupDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schGroupList",  schGroupList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("schGroupNumber", schGroupNumber);
        ctx.put("schGroupName", schGroupName);
        ctx.put("schGroupSchool", schGroupSchool);
        SchoolDAO schoolDAO = new SchoolDAO();
        List<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        ctx.put("schGroupBirthDate", schGroupBirthDate);
        return "front_query_view";
    }

    /*查询要修改的SchGroup信息*/
    public String ModifySchGroupQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键schGroupNumber获取SchGroup对象*/
        SchGroup schGroup = schGroupDAO.GetSchGroupByNumber(schGroupNumber);
        dumpMsg("[ModifySchGroupQuery] " + schGroup);
        SchoolDAO schoolDAO = new SchoolDAO();
        List<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        ctx.put("schGroup",  schGroup);
        return "modify_view";
    }

    /*查询要修改的SchGroup信息*/
    public String FrontShowSchGroupQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键schGroupNumber获取SchGroup对象*/
        SchGroup schGroup = schGroupDAO.GetSchGroupByNumber(schGroupNumber);

        SchoolDAO schoolDAO = new SchoolDAO();
        List<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        ctx.put("schGroup",  schGroup);
        return "front_show_view";
    }

    /*更新修改SchGroup信息*/
    public String ModifySchGroup() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	dumpMsg("[ModifySchGroup] 1:: " + schGroup);
            if(true) {
            SchoolDAO schoolDAO = new SchoolDAO();
            School school = schoolDAO.GetSchoolByNumber(schGroup.getSchGroupSchool().getSchoolNumber());
            schGroup.setSchGroupSchool(school);
            }
            dumpMsg("[ModifySchGroup] 2:: " + schGroup);
            schGroupDAO.UpdateSchGroup(schGroup);
            ctx.put("message",  java.net.URLEncoder.encode("SchGroup信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchGroup信息更新失败!"));
            return "error";
       }
   }

    /*删除SchGroup信息*/
    public String DeleteSchGroup() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            schGroupDAO.DeleteSchGroup(schGroupNumber);
            ctx.put("message",  java.net.URLEncoder.encode("SchGroup删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchGroup删除失败! 可能存在与此记录相关联的数据。"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [SchGroupAction] " + msg);
    }

}
