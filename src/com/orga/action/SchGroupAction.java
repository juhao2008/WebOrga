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

    /*�������Ҫ��ѯ������: �����ұ��*/
	private String schGroupNumber;
	
	public String getSchGroupNumber() {
		return schGroupNumber;
	}

	public void setSchGroupNumber(String schGroupNumber) {
		this.schGroupNumber = schGroupNumber;
	}

    /*�������Ҫ��ѯ������: ����������*/
    private String schGroupName;
    public String getSchGroupName() {
		return schGroupName;
	}

	public void setSchGroupName(String schGroupName) {
		this.schGroupName = schGroupName;
	}

    /*�������Ҫ��ѯ������: ����ѧУ */
    private School schGroupSchool;
    public School getSchGroupSchool() {
		return schGroupSchool;
	}

	public void setSchGroupSchool(School schGroupSchool) {
		this.schGroupSchool = schGroupSchool;
	}

    /*�������Ҫ��ѯ������: ��������*/
    private String schGroupBirthDate;
    public String getSchGroupBirthDate() {
		return schGroupBirthDate;
	}

	public void setSchGroupBirthDate(String schGroupBirthDate) {
		this.schGroupBirthDate = schGroupBirthDate;
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
    SchGroupDAO schGroupDAO = new SchGroupDAO();

    /*��������SchGroup����*/
    private SchGroup schGroup;
    public void setSchGroup(SchGroup schGroup) {
        this.schGroup = schGroup;
    }
    public SchGroup getSchGroup() {
        return this.schGroup;
    }

    /*��ת�����SchGroup��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�SchGroup��Ϣ*/
        SchoolDAO schGroupDAO = new SchoolDAO();
        List<School> schoolList = schGroupDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        return "add_view";
    }

    /*���SchGroup��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSchGroup() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤���нM����Ƿ��Ѿ�����*/
        schGroup.setSchGroupNumber(HibernateUtil.generateRecordId("ORG"));
        String schGroupNumber = schGroup.getSchGroupNumber();
        SchGroup db_schGroup = schGroupDAO.GetSchGroupByNumber(schGroupNumber);
        if(null != db_schGroup) {
            ctx.put("error",  java.net.URLEncoder.encode("�ý����ұ���Ѿ�����!"));
            return "error";
        }
        try {
            SchoolDAO schoolDAO = new SchoolDAO();
            School school = schoolDAO.GetSchoolByNumber(schGroup.getSchGroupSchool().getSchoolNumber());
            schGroup.setSchGroupSchool(school);
            
            schGroupDAO.AddSchGroup(schGroup);
            ctx.put("message",  java.net.URLEncoder.encode("SchGroup��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchGroup���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSchGroup��Ϣ*/
    public String QuerySchGroup() {
        if(currentPage == 0) currentPage = 1;
        if(schGroupNumber == null) schGroupNumber = "";
        if(schGroupName == null) schGroupName = "";
        if(schGroupBirthDate == null) schGroupBirthDate = "";
        List<SchGroup> schGroupList = schGroupDAO.QuerySchGroupInfo(schGroupNumber, schGroupName, schGroupSchool, schGroupBirthDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        schGroupDAO.CalculateTotalPageAndRecordNumber(schGroupNumber, schGroupName, schGroupSchool, schGroupBirthDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = schGroupDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*ǰ̨��ѯSchGroup��Ϣ*/
    public String FrontQuerySchGroup() {
        if(currentPage == 0) currentPage = 1;
        if(schGroupNumber == null) schGroupNumber = "";
        if(schGroupName == null) schGroupName = "";
        if(schGroupBirthDate == null) schGroupBirthDate = "";
        List<SchGroup> schGroupList = schGroupDAO.QuerySchGroupInfo(schGroupNumber, schGroupName, schGroupSchool, schGroupBirthDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        schGroupDAO.CalculateTotalPageAndRecordNumber(schGroupNumber, schGroupName, schGroupSchool, schGroupBirthDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = schGroupDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�SchGroup��Ϣ*/
    public String ModifySchGroupQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������schGroupNumber��ȡSchGroup����*/
        SchGroup schGroup = schGroupDAO.GetSchGroupByNumber(schGroupNumber);
        dumpMsg("[ModifySchGroupQuery] " + schGroup);
        SchoolDAO schoolDAO = new SchoolDAO();
        List<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        ctx.put("schGroup",  schGroup);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SchGroup��Ϣ*/
    public String FrontShowSchGroupQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������schGroupNumber��ȡSchGroup����*/
        SchGroup schGroup = schGroupDAO.GetSchGroupByNumber(schGroupNumber);

        SchoolDAO schoolDAO = new SchoolDAO();
        List<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        ctx.put("schGroup",  schGroup);
        return "front_show_view";
    }

    /*�����޸�SchGroup��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("SchGroup��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchGroup��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SchGroup��Ϣ*/
    public String DeleteSchGroup() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            schGroupDAO.DeleteSchGroup(schGroupNumber);
            ctx.put("message",  java.net.URLEncoder.encode("SchGroupɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchGroupɾ��ʧ��! ���ܴ�����˼�¼����������ݡ�"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [SchGroupAction] " + msg);
    }

}
