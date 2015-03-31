package com.orga.action;

import java.io.File;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.ClassInfoDAO;
import com.orga.dao.SchoolDAO;
import com.orga.dao.StudentDAO;
import com.orga.domain.ClassInfo;
import com.orga.domain.School;
import com.orga.utils.CommConst;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class ClassInfoAction extends ActionSupport {

    /*�������Ҫ��ѯ������: �༶���*/
    private String classNumber;
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
    public String getClassNumber() {
        return this.classNumber;
    }

    /*�������Ҫ��ѯ������: �༶����*/
    private String className;
    public void setClassName(String className) {
        this.className = className;
    }
    public String getClassName() {
        return this.className;
    }

    /*�������Ҫ��ѯ������: ����ѧУ*/
    private School classSchool;
    public School getClassSchool() {
		return classSchool;
	}
	public void setClassSchool(School classSchool) {
		this.classSchool = classSchool;
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
    private ClassInfoDAO classInfoDAO = new ClassInfoDAO();

    /*��������ClassInfo����*/
    private ClassInfo classInfo;
    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }
    /* excel ���������ļ�*/
    private File excelFile;
    public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

    /*��ת�����ClassInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        SchoolDAO schoolDAO = new SchoolDAO();
        String schoolNumber = (String)ctx.getSession().get("schoolNumber");
        School classSchool = schoolDAO.GetSchoolByNumber(schoolNumber);
        ctx.put("classSchool", classSchool);      
        return "add_view";
    }

    /*���ClassInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        
        classInfo.setClassNumber(HibernateUtil.generateRecordId("BAN"));
        /*��֤�༶����Ƿ��Ѿ�����*/
        String classNumber = classInfo.getClassNumber();
        ClassInfo db_classInfo = null;
        try {
        	db_classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        if(null != db_classInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�ð༶����Ѿ�����!"));
            return "error";
        }
        try {
        	String schoolNumber = (String)ctx.getSession().get("schoolNumber");
            if(schoolNumber != null && !schoolNumber.equals("")) {
            	SchoolDAO schoolDAO = new SchoolDAO();
            	School classSchool = schoolDAO.GetSchoolByNumber(schoolNumber);
            	classInfo.setClassSchool(classSchool);
            }
            classInfoDAO.AddClassInfo(classInfo);
            ctx.put("message",  java.net.URLEncoder.encode("�༶��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("�༶���ʧ��!"));
            return "error";
        }
    }
    
    /**
     * QueryClassInfoBySession
     * @return
     */
    public String QueryClassInfoRecords() {
    	ActionContext ctx = ActionContext.getContext();
    	if(ctx == null || ctx.getSession() == null) {
    		System.out.println("aaaa");
    		return "login_view";
    	}
    	String teacherNumber = (String)ctx.getSession().get(CommConst.USER_ID);
    	String schoolNumber = (String)ctx.getSession().get(CommConst.USER_SCHOOL_NUMBER);
    	
        if(currentPage == 0) currentPage = 1;
        List<ClassInfo> classInfoList = null;
        try {
        	classInfoList = classInfoDAO.QueryClassInfo(null, null, schoolNumber, null, teacherNumber, currentPage);
            /*�����ܵ�ҳ�����ܵļ�¼��*/
            classInfoDAO.CalculateTotalPageAndRecordNumber(null, null, schoolNumber, null, teacherNumber);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = classInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = classInfoDAO.getRecordNumber();
        
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        
        String userRole = (String)ctx.getSession().get(CommConst.USER_ROLE);
        if(CommConst.ROLE_R2.equals(userRole)) {//�ον�ʦ
			return "classinfo_index";
		} else if(CommConst.ROLE_R3.equals(userRole)) {//������
			return "classinfo_index";
		} else if(CommConst.ROLE_R4.equals(userRole)) {//ѧУ����Ա
			return "classinfo_list_admin";
		}
//        ctx.put("error",  java.net.URLEncoder.encode(userRole + " δƥ�䵽���û���ɫ�ķ���ҳ�档"));
        return "login_view";
    }
    
    public String QueryClassInfoTable() {
    	ActionContext ctx = ActionContext.getContext();
    	if(ctx == null || ctx.getSession() == null) {
    		return "login_view";
    	}
    	String teacherNumber = (String)ctx.getSession().get(CommConst.USER_ID);
    	String schoolNumber = (String)ctx.getSession().get(CommConst.USER_SCHOOL_NUMBER);
    	
        if(currentPage == 0) currentPage = 1;
        List<ClassInfo> classInfoList = null;
        try {
        	classInfoList = classInfoDAO.QueryClassInfo(null, null, schoolNumber, null, teacherNumber, currentPage);
            /*�����ܵ�ҳ�����ܵļ�¼��*/
            classInfoDAO.CalculateTotalPageAndRecordNumber(null, null, schoolNumber, null, teacherNumber);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = classInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = classInfoDAO.getRecordNumber();
        
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
    	return "classinfo_table";
    }

    /*��ѯClassInfo��Ϣ*/
    public String QueryClassInfo() {
        if(currentPage == 0) currentPage = 1;
        if(classNumber == null) classNumber = "";
        if(className == null) className = "";
        String schoolNumber = null;
        if(classSchool != null) {
        	schoolNumber = classSchool.getSchoolNumber();
        }
        List<ClassInfo> classInfoList = null;
        try {
        	classInfoList = classInfoDAO.QueryClassInfo(classNumber, className, schoolNumber, null, null, currentPage);
            /*�����ܵ�ҳ�����ܵļ�¼��*/
            classInfoDAO.CalculateTotalPageAndRecordNumber(classNumber, className, schoolNumber, null, null);
        	
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = classInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = classInfoDAO.getRecordNumber();
        
        ActionContext ctx = ActionContext.getContext();
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("classNumber", classNumber);
        ctx.put("className", className);
        ctx.put("classSchool", classSchool);
        SchoolDAO schoolDAO = new SchoolDAO();
        List<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        
        String userRole = (String)ctx.getSession().get(CommConst.USER_ROLE);
        if(CommConst.ROLE_R2.equals(userRole)) {//�ον�ʦ
			return "classinfo_student";
		} else if(CommConst.ROLE_R3.equals(userRole)) {//������
			return "classinfo_student";
		} else if(CommConst.ROLE_R4.equals(userRole)) {//ѧУ����Ա
			return "classinfo_list";
		}
//        ctx.put("error",  java.net.URLEncoder.encode(userRole + " δƥ�䵽���û���ɫ�ķ���ҳ�档"));
        return "login_view";
    }
    
    /*��ѯҪ�޸ĵ�ClassInfo��Ϣ*/
    public String ModifyClassInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classNumber��ȡClassInfo����*/
        try {
        	ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);
            ctx.put("classInfo",  classInfo);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        String schoolNumber = (String)ctx.getSession().get("schoolNumber");
        SchoolDAO schoolDAO = new SchoolDAO();
        School classSchool = schoolDAO.GetSchoolByNumber(schoolNumber);
        ctx.put("classSchool", classSchool);

        return "modify_view";
    }

    /*�����޸�ClassInfo��Ϣ*/
    public String ModifyClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
//        	String schoolNumber = (String)ctx.getSession().get("schoolNumber");
//			if (schoolNumber != null && !schoolNumber.equals("")) {
//				SchoolDAO schoolDAO = new SchoolDAO();
//				School school = schoolDAO.GetSchoolByNumber(schoolNumber);
//				classInfo.setClassSchool(school);
//			}
			ClassInfo oldClassInfo = classInfoDAO.GetClassInfoByClassNumber(classInfo.getClassNumber());
			oldClassInfo.setClassMemo(classInfo.getClassMemo());
            classInfoDAO.UpdateClassInfo(oldClassInfo);
            
            ctx.put("message",  java.net.URLEncoder.encode("�༶��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("�༶��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ClassInfo��Ϣ*/
    public String DeleteClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            classInfoDAO.DeleteClassInfo(classNumber);
            ctx.put("message",  java.net.URLEncoder.encode("�༶ɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("�༶ɾ��ʧ��!"));
            return "error";
        }
    }
    
    public void DumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [StudentAction] " + msg);
    }

}
