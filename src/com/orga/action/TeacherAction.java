package com.orga.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.SchGroupDAO;
import com.orga.dao.RoleDAO;
import com.orga.dao.SchoolDAO;
import com.orga.dao.TeacherDAO;
import com.orga.domain.SchGroup;
import com.orga.domain.Role;
import com.orga.domain.School;
import com.orga.domain.Student;
import com.orga.domain.Teacher;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class TeacherAction extends ActionSupport {

	private static final long serialVersionUID = -6346945059419287091L;
	/*图片字段teacherPhoto参数接收*/
	 private File teacherPhotoFile;
	 private String teacherPhotoFileFileName;
	 private String teacherPhotoFileContentType;
	 public File getTeacherPhotoFile() {
		return teacherPhotoFile;
	}
	public void setTeacherPhotoFile(File teacherPhotoFile) {
		this.teacherPhotoFile = teacherPhotoFile;
	}
	public String getTeacherPhotoFileFileName() {
		return teacherPhotoFileFileName;
	}
	public void setTeacherPhotoFileFileName(String teacherPhotoFileFileName) {
		this.teacherPhotoFileFileName = teacherPhotoFileFileName;
	}
	public String getTeacherPhotoFileContentType() {
		return teacherPhotoFileContentType;
	}
	public void setTeacherPhotoFileContentType(String teacherPhotoFileContentType) {
		this.teacherPhotoFileContentType = teacherPhotoFileContentType;
	}
    /*界面层需要查询的属性: 教师编号*/
    private String teacherNumber;
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }
    public String getTeacherNumber() {
        return this.teacherNumber;
    }

    /*界面层需要查询的属性: 教师姓名*/
    private String teacherName;
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getTeacherName() {
        return this.teacherName;
    }

    /*界面层需要查询的属性: 所属学校*/
    private School teacherSchool;
    public School getTeacherSchool() {
		return teacherSchool;
	}
	public void setTeacherSchool(School teacherSchool) {
		this.teacherSchool = teacherSchool;
	}
	
	/*界面层需要查询的属性: 入职日期*/
    private String teacherArriveDate;
    public void setTeacherArriveDate(String teacherArriveDate) {
        this.teacherArriveDate = teacherArriveDate;
    }
    public String getTeacherArriveDate() {
        return this.teacherArriveDate;
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
    private TeacherDAO teacherDAO = new TeacherDAO();

    /*待操作的Teacher对象*/
    private Teacher teacher;
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Teacher getTeacher() {
        return this.teacher;
    }
    
    /* excel 批量导入文件*/
    private File excelFile;
    public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
	
	public String UploadExcelTeachers() {
		ActionContext ctx = ActionContext.getContext();
		if(excelFile == null) {
			ctx.put("error",  java.net.URLEncoder.encode("excel文件找不到!"));
			return "error";
		}
		try {
			FileInputStream is = new FileInputStream(excelFile);
			HSSFWorkbook book = new HSSFWorkbook(is);
			final List<Teacher> list = getAllTeachers(book);
			for(Teacher teacher : list) {
				teacherDAO.AddTeacher(teacher);
			}
			book.close();
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			ctx.put("error",  java.net.URLEncoder.encode("学生信息添加失败!"));
            return "error";
		}
		ctx.put("message",  java.net.URLEncoder.encode("教师信息添加成功!"));
        return "add_success";
	}
	
	private List<Teacher> getAllTeachers(HSSFWorkbook book) {
		List<Teacher> list = new ArrayList<Teacher>();
		try {
			
			
			
		} catch (Exception ex) {
			
		}
		return list;
	}

    /*跳转到添加Teacher视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        
        SchoolDAO schoolDAO = new SchoolDAO();
        ArrayList<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList", schoolList);
        
        SchGroupDAO schGroupDAO = new SchGroupDAO();
        ArrayList<SchGroup> schGroupList = schGroupDAO.QueryAllSchGroupInfo();
        ctx.put("schGroupList", schGroupList);
        
        RoleDAO roleDAO = new RoleDAO();
        ArrayList<Role> roleList = roleDAO.QueryAllRoles();
        ctx.put("roleList", roleList);
        
        return "add_view";
    }

    /*添加Teacher信息*/
    @SuppressWarnings("deprecation")
    public String AddTeacher() {
        ActionContext ctx = ActionContext.getContext();
        /*验证教师编号是否已经存在*/
        teacher.setTeacherNumber(HibernateUtil.generateRecordId("TH"));
        String teacherNumber = teacher.getTeacherNumber();
        Teacher db_teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);
        if(null != db_teacher) {
            ctx.put("error",  java.net.URLEncoder.encode("该教师编号已经存在!"));
            return "error";
        }
        RoleDAO roleDAO = new RoleDAO();
        Role teacherRole = roleDAO.GetRoleById(teacher.getTeacherRole().getRoleId());
        teacher.setTeacherRole(teacherRole);
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理图片上传*/
            String teacherPhotoFileName = ""; 
       	 	if(teacherPhotoFile != null) {
       	 		InputStream is = new FileInputStream(teacherPhotoFile);
       			String fileContentType = this.getTeacherPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("上传图片格式不正确!"));
       				return "error";
       			}
       			File file = new File(path, teacherPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       	 	}
            if(teacherPhotoFile != null)
            	teacher.setTeacherPhoto("upload/" + teacherPhotoFileName);
            else
            	teacher.setTeacherPhoto("upload/NoImage.jpg");
            teacherDAO.AddTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher添加失败!"));
            return "error";
        }
    }

    /*查询Teacher信息*/
    public String QueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(teacherNumber == null) teacherNumber = "";
        if(teacherName == null) teacherName = "";
        String schoolNumber = null;
        if(teacherSchool != null) {
        	schoolNumber = teacherSchool.getSchoolNumber();
        }
        if(teacherArriveDate == null) teacherArriveDate = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(teacherNumber, teacherName, schoolNumber, teacherArriveDate, currentPage);
        dumpMsg(teacherNumber + teacherName + schoolNumber + schoolNumber + teacherList.size());
        /*计算总的页数和总的记录数*/
        teacherDAO.CalculateTotalPageAndRecordNumber(teacherNumber, teacherName, schoolNumber, teacherArriveDate);
        /*获取到总的页码数目*/
        totalPage = teacherDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = teacherDAO.getRecordNumber();
        
        SchoolDAO schoolDAO = new SchoolDAO();
        ArrayList<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        
        SchGroupDAO schGroupDAO = new SchGroupDAO();
        ArrayList<SchGroup> schGroupList = schGroupDAO.QueryAllSchGroupInfo();
        
        RoleDAO roleDAO = new RoleDAO();
        ArrayList<Role> roleList = roleDAO.QueryAllRoles();
        
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teacherList",  teacherList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherNumber", teacherNumber);
        ctx.put("teacherName", teacherName);
        ctx.put("teacherSchool", teacherSchool);
        ctx.put("teacherArriveDate", teacherArriveDate);
        ctx.put("schoolList", schoolList);
        ctx.put("schGroupList", schGroupList);
        ctx.put("roleList", roleList);
        return "query_view";
    }

    /*前台查询Teacher信息*/
    public String FrontQueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(teacherNumber == null) teacherNumber = "";
        if(teacherName == null) teacherName = "";
        String schoolNumber = null;
        if(teacherSchool != null) {
        	schoolNumber = teacherSchool.getSchoolNumber();
        }
        if(teacherArriveDate == null) teacherArriveDate = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(teacherNumber, teacherName, schoolNumber, teacherArriveDate, currentPage);
        /*计算总的页数和总的记录数*/
        teacherDAO.CalculateTotalPageAndRecordNumber(teacherNumber, teacherName, schoolNumber, teacherArriveDate);
        /*获取到总的页码数目*/
        totalPage = teacherDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = teacherDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teacherList",  teacherList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherNumber", teacherNumber);
        ctx.put("teacherName", teacherName);
        ctx.put("teacherSchool", teacherSchool);
        ctx.put("teacherArriveDate", teacherArriveDate);
        return "front_query_view";
    }

    /*查询要修改的Teacher信息*/
    public String ModifyTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键teacherNumber获取Teacher对象*/
        Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);
        ctx.put("teacher",  teacher);
        
        SchoolDAO schoolDAO = new SchoolDAO();
        ArrayList<School> schoolList = schoolDAO.QueryAllSchoolInfo();
        ctx.put("schoolList",  schoolList);
        
        SchGroupDAO schGroupDAO = new SchGroupDAO();
        ArrayList<SchGroup> schGroupList = schGroupDAO.QueryAllSchGroupInfo();
        ctx.put("schGroupList",  schGroupList);
        
        RoleDAO roleDAO = new RoleDAO();
        ArrayList<Role> roleList = roleDAO.QueryAllRoles();
        ctx.put("roleList", roleList);
        
        return "modify_view";
    }

    /*查询要修改的Teacher信息*/
    public String FrontShowTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键teacherNumber获取Teacher对象*/
        Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);

        ctx.put("teacher",  teacher);
        return "front_show_view";
    }

    /*更新修改Teacher信息*/
    public String ModifyTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理图片上传*/
            String teacherPhotoFileName = ""; 
       	 	if(teacherPhotoFile != null) {
       	 		InputStream is = new FileInputStream(teacherPhotoFile);
       			String fileContentType = this.getTeacherPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("上传图片格式不正确!"));
       				return "error";
       			}
       			File file = new File(path, teacherPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       			teacher.setTeacherPhoto("upload/" + teacherPhotoFileName);
       	 	}
       	 	SchoolDAO schoolDAO = new SchoolDAO();
       	 	School school = schoolDAO.GetSchoolByNumber(teacher.getTeacherSchool().getSchoolNumber());
       	 	teacher.setTeacherSchool(school);
       	 	SchGroupDAO schGroupDAO = new SchGroupDAO();
       	 	SchGroup schGroup = schGroupDAO.GetSchGroupByNumber(teacher.getTeacherSchGroup().getSchGroupNumber());
       	 	teacher.setTeacherSchGroup(schGroup);
       	 	
       	 	RoleDAO roleDAO = new RoleDAO();
       	 	Role teacherRole = roleDAO.GetRoleById(teacher.getTeacherRole().getRoleId());
       	 	teacher.setTeacherRole(teacherRole);
       	 	
       	 	dumpMsg("[ModifyTeacher]" + teacher);
            teacherDAO.UpdateTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher信息更新失败!"));
            return "error";
       }
   }

    /*删除Teacher信息*/
    public String DeleteTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            teacherDAO.DeleteTeacher(teacherNumber);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher删除失败!"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [TeacherAction] " + msg);
    }

}
