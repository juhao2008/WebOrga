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

    /*界面层需要查询的属性: 班级编号*/
    private String classNumber;
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
    public String getClassNumber() {
        return this.classNumber;
    }

    /*界面层需要查询的属性: 班级名称*/
    private String className;
    public void setClassName(String className) {
        this.className = className;
    }
    public String getClassName() {
        return this.className;
    }

    /*界面层需要查询的属性: 所属学校*/
    private School classSchool;
    public School getClassSchool() {
		return classSchool;
	}
	public void setClassSchool(School classSchool) {
		this.classSchool = classSchool;
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
    private ClassInfoDAO classInfoDAO = new ClassInfoDAO();

    /*待操作的ClassInfo对象*/
    private ClassInfo classInfo;
    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }
    /* excel 批量导入文件*/
    private File excelFile;
    public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

    /*跳转到添加ClassInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        SchoolDAO schoolDAO = new SchoolDAO();
        String schoolNumber = (String)ctx.getSession().get("schoolNumber");
        School classSchool = schoolDAO.GetSchoolByNumber(schoolNumber);
        ctx.put("classSchool", classSchool);      
        return "add_view";
    }

    /*添加ClassInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        
        classInfo.setClassNumber(HibernateUtil.generateRecordId("BAN"));
        /*验证班级编号是否已经存在*/
        String classNumber = classInfo.getClassNumber();
        ClassInfo db_classInfo = null;
        try {
        	db_classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        if(null != db_classInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该班级编号已经存在!"));
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
            ctx.put("message",  java.net.URLEncoder.encode("班级添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("班级添加失败!"));
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
            /*计算总的页数和总的记录数*/
            classInfoDAO.CalculateTotalPageAndRecordNumber(null, null, schoolNumber, null, teacherNumber);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        /*获取到总的页码数目*/
        totalPage = classInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = classInfoDAO.getRecordNumber();
        
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        
        String userRole = (String)ctx.getSession().get(CommConst.USER_ROLE);
        if(CommConst.ROLE_R2.equals(userRole)) {//任课教师
			return "classinfo_index";
		} else if(CommConst.ROLE_R3.equals(userRole)) {//班主任
			return "classinfo_index";
		} else if(CommConst.ROLE_R4.equals(userRole)) {//学校管理员
			return "classinfo_list_admin";
		}
//        ctx.put("error",  java.net.URLEncoder.encode(userRole + " 未匹配到此用户角色的返回页面。"));
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
            /*计算总的页数和总的记录数*/
            classInfoDAO.CalculateTotalPageAndRecordNumber(null, null, schoolNumber, null, teacherNumber);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        /*获取到总的页码数目*/
        totalPage = classInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = classInfoDAO.getRecordNumber();
        
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
    	return "classinfo_table";
    }

    /*查询ClassInfo信息*/
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
            /*计算总的页数和总的记录数*/
            classInfoDAO.CalculateTotalPageAndRecordNumber(classNumber, className, schoolNumber, null, null);
        	
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        /*获取到总的页码数目*/
        totalPage = classInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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
        if(CommConst.ROLE_R2.equals(userRole)) {//任课教师
			return "classinfo_student";
		} else if(CommConst.ROLE_R3.equals(userRole)) {//班主任
			return "classinfo_student";
		} else if(CommConst.ROLE_R4.equals(userRole)) {//学校管理员
			return "classinfo_list";
		}
//        ctx.put("error",  java.net.URLEncoder.encode(userRole + " 未匹配到此用户角色的返回页面。"));
        return "login_view";
    }
    
    /*查询要修改的ClassInfo信息*/
    public String ModifyClassInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键classNumber获取ClassInfo对象*/
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

    /*更新修改ClassInfo信息*/
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
            
            ctx.put("message",  java.net.URLEncoder.encode("班级信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("班级信息更新失败!"));
            return "error";
       }
   }

    /*删除ClassInfo信息*/
    public String DeleteClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            classInfoDAO.DeleteClassInfo(classNumber);
            ctx.put("message",  java.net.URLEncoder.encode("班级删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("班级删除失败!"));
            return "error";
        }
    }
    
    public void DumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [StudentAction] " + msg);
    }

}
