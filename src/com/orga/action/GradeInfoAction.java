package com.orga.action;

import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.GradeInfoDAO;
import com.orga.domain.GradeInfo;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class GradeInfoAction extends ActionSupport {

    /*界面层需要查询的属性: 年级编号/名称*/
	private String gradeNumber;
    public String getGradeNumber() {
		return gradeNumber;
	}
	public void setGradeNumber(String gradeNumber) {
		this.gradeNumber = gradeNumber;
	}

	private String gradeName;
    public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	/*待操作的GradeInfo对象*/
	private GradeInfo gradeInfo;
	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}
	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
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
    private GradeInfoDAO gradeInfoDAO = new GradeInfoDAO();

    /*跳转到添加GradeInfo视图*/
    public String AddView() {
        return "add_view";
    }

    /*添加GradeInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddGradeInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证教研組编号是否已经存在*/
        gradeInfo.setGradeNumber(HibernateUtil.generateRecordId("NJ"));
        String gradeNumber = gradeInfo.getGradeNumber();
        GradeInfo db_gradeInfo = gradeInfoDAO.GetGradeInfoByGradeNumber(gradeNumber);
        if(null != db_gradeInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该GradeInfo编号已经存在!"));
            return "error";
        }
        try {
        	gradeInfoDAO.AddGradeInfo(gradeInfo);
            ctx.put("message",  java.net.URLEncoder.encode("GradeInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GradeInfo添加失败!"));
            return "error";
        }
    }

    /*查询GradeInfo信息*/
    public String QueryGradeInfo() {
        if(currentPage == 0) currentPage = 1;
        if(gradeNumber == null) gradeNumber = "";
        if(gradeName == null) gradeName = "";
        List<GradeInfo> gradeInfoList = gradeInfoDAO.QueryGradeInfo(gradeNumber, gradeName, currentPage);
        /*计算总的页数和总的记录数*/
        gradeInfoDAO.CalculateTotalPageAndRecordNumber(gradeNumber, gradeName);
        /*获取到总的页码数目*/
        totalPage = gradeInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = gradeInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("gradeInfoList",  gradeInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("schGroupNumber", gradeNumber);
        ctx.put("schGroupName", gradeName);
        
        return "query_view";
    }

    /*前台查询GradeInfo信息*/
    public String FrontQueryGradeInfo() {
        if(currentPage == 0) currentPage = 1;
        if(gradeNumber == null) gradeNumber = "";
        if(gradeName == null) gradeName = "";
        List<GradeInfo> gradeInfoList = gradeInfoDAO.QueryGradeInfo(gradeNumber, gradeName, currentPage);
        /*计算总的页数和总的记录数*/
        gradeInfoDAO.CalculateTotalPageAndRecordNumber(gradeNumber, gradeName);
        /*获取到总的页码数目*/
        totalPage = gradeInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = gradeInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("gradeInfoList",  gradeInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("gradeNumber", gradeNumber);
        ctx.put("gradeName", gradeName);
        return "front_query_view";
    }

    /*查询要修改的GradeInfo信息*/
    public String ModifyGradeInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键gradeNumber获取GradeInfo对象*/
        GradeInfo gradeInfo = gradeInfoDAO.GetGradeInfoByGradeNumber(gradeNumber);
        ctx.put("gradeInfo",  gradeInfo);
        return "modify_view";
    }

    /*查询要修改的GradeInfo信息*/
    public String FrontShowSchGroupQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键gradeNumber获取GradeInfo对象*/
        GradeInfo gradeInfo = gradeInfoDAO.GetGradeInfoByGradeNumber(gradeNumber);

        ctx.put("gradeInfo",  gradeInfo);
        return "front_show_view";
    }

    /*更新修改GradeInfo信息*/
    public String ModifyGradeInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	gradeInfoDAO.UpdateGradeInfo(gradeInfo);
            ctx.put("message",  java.net.URLEncoder.encode("GradeInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GradeInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除SchGroup信息*/
    public String DeleteGradeInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
        	gradeInfoDAO.DeleteGradeInfo(gradeNumber);
            ctx.put("message",  java.net.URLEncoder.encode("GradeInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GradeInfo删除失败! 可能存在与此记录相关联的数据。"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [GradeInfoAction] " + msg);
    }

}
