package com.orga.action;

import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.GradeInfoDAO;
import com.orga.domain.GradeInfo;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class GradeInfoAction extends ActionSupport {

    /*�������Ҫ��ѯ������: �꼶���/����*/
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
	
	/*��������GradeInfo����*/
	private GradeInfo gradeInfo;
	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}
	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
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
    private GradeInfoDAO gradeInfoDAO = new GradeInfoDAO();

    /*��ת�����GradeInfo��ͼ*/
    public String AddView() {
        return "add_view";
    }

    /*���GradeInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddGradeInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤���нM����Ƿ��Ѿ�����*/
        gradeInfo.setGradeNumber(HibernateUtil.generateRecordId("NJ"));
        String gradeNumber = gradeInfo.getGradeNumber();
        GradeInfo db_gradeInfo = gradeInfoDAO.GetGradeInfoByGradeNumber(gradeNumber);
        if(null != db_gradeInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("��GradeInfo����Ѿ�����!"));
            return "error";
        }
        try {
        	gradeInfoDAO.AddGradeInfo(gradeInfo);
            ctx.put("message",  java.net.URLEncoder.encode("GradeInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GradeInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯGradeInfo��Ϣ*/
    public String QueryGradeInfo() {
        if(currentPage == 0) currentPage = 1;
        if(gradeNumber == null) gradeNumber = "";
        if(gradeName == null) gradeName = "";
        List<GradeInfo> gradeInfoList = gradeInfoDAO.QueryGradeInfo(gradeNumber, gradeName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        gradeInfoDAO.CalculateTotalPageAndRecordNumber(gradeNumber, gradeName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = gradeInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*ǰ̨��ѯGradeInfo��Ϣ*/
    public String FrontQueryGradeInfo() {
        if(currentPage == 0) currentPage = 1;
        if(gradeNumber == null) gradeNumber = "";
        if(gradeName == null) gradeName = "";
        List<GradeInfo> gradeInfoList = gradeInfoDAO.QueryGradeInfo(gradeNumber, gradeName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        gradeInfoDAO.CalculateTotalPageAndRecordNumber(gradeNumber, gradeName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = gradeInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�GradeInfo��Ϣ*/
    public String ModifyGradeInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������gradeNumber��ȡGradeInfo����*/
        GradeInfo gradeInfo = gradeInfoDAO.GetGradeInfoByGradeNumber(gradeNumber);
        ctx.put("gradeInfo",  gradeInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�GradeInfo��Ϣ*/
    public String FrontShowSchGroupQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������gradeNumber��ȡGradeInfo����*/
        GradeInfo gradeInfo = gradeInfoDAO.GetGradeInfoByGradeNumber(gradeNumber);

        ctx.put("gradeInfo",  gradeInfo);
        return "front_show_view";
    }

    /*�����޸�GradeInfo��Ϣ*/
    public String ModifyGradeInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	gradeInfoDAO.UpdateGradeInfo(gradeInfo);
            ctx.put("message",  java.net.URLEncoder.encode("GradeInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GradeInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SchGroup��Ϣ*/
    public String DeleteGradeInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
        	gradeInfoDAO.DeleteGradeInfo(gradeNumber);
            ctx.put("message",  java.net.URLEncoder.encode("GradeInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GradeInfoɾ��ʧ��! ���ܴ�����˼�¼����������ݡ�"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [GradeInfoAction] " + msg);
    }

}
