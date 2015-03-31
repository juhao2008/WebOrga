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
	/*ͼƬ�ֶ�teacherPhoto��������*/
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
    /*�������Ҫ��ѯ������: ��ʦ���*/
    private String teacherNumber;
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }
    public String getTeacherNumber() {
        return this.teacherNumber;
    }

    /*�������Ҫ��ѯ������: ��ʦ����*/
    private String teacherName;
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getTeacherName() {
        return this.teacherName;
    }

    /*�������Ҫ��ѯ������: ����ѧУ*/
    private School teacherSchool;
    public School getTeacherSchool() {
		return teacherSchool;
	}
	public void setTeacherSchool(School teacherSchool) {
		this.teacherSchool = teacherSchool;
	}
	
	/*�������Ҫ��ѯ������: ��ְ����*/
    private String teacherArriveDate;
    public void setTeacherArriveDate(String teacherArriveDate) {
        this.teacherArriveDate = teacherArriveDate;
    }
    public String getTeacherArriveDate() {
        return this.teacherArriveDate;
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
    private TeacherDAO teacherDAO = new TeacherDAO();

    /*��������Teacher����*/
    private Teacher teacher;
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Teacher getTeacher() {
        return this.teacher;
    }
    
    /* excel ���������ļ�*/
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
			ctx.put("error",  java.net.URLEncoder.encode("excel�ļ��Ҳ���!"));
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
			ctx.put("error",  java.net.URLEncoder.encode("ѧ����Ϣ���ʧ��!"));
            return "error";
		}
		ctx.put("message",  java.net.URLEncoder.encode("��ʦ��Ϣ��ӳɹ�!"));
        return "add_success";
	}
	
	private List<Teacher> getAllTeachers(HSSFWorkbook book) {
		List<Teacher> list = new ArrayList<Teacher>();
		try {
			
			
			
		} catch (Exception ex) {
			
		}
		return list;
	}

    /*��ת�����Teacher��ͼ*/
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

    /*���Teacher��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTeacher() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��ʦ����Ƿ��Ѿ�����*/
        teacher.setTeacherNumber(HibernateUtil.generateRecordId("TH"));
        String teacherNumber = teacher.getTeacherNumber();
        Teacher db_teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);
        if(null != db_teacher) {
            ctx.put("error",  java.net.URLEncoder.encode("�ý�ʦ����Ѿ�����!"));
            return "error";
        }
        RoleDAO roleDAO = new RoleDAO();
        Role teacherRole = roleDAO.GetRoleById(teacher.getTeacherRole().getRoleId());
        teacher.setTeacherRole(teacherRole);
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String teacherPhotoFileName = ""; 
       	 	if(teacherPhotoFile != null) {
       	 		InputStream is = new FileInputStream(teacherPhotoFile);
       			String fileContentType = this.getTeacherPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
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
            ctx.put("message",  java.net.URLEncoder.encode("Teacher��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTeacher��Ϣ*/
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
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teacherDAO.CalculateTotalPageAndRecordNumber(teacherNumber, teacherName, schoolNumber, teacherArriveDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teacherDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*ǰ̨��ѯTeacher��Ϣ*/
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
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teacherDAO.CalculateTotalPageAndRecordNumber(teacherNumber, teacherName, schoolNumber, teacherArriveDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teacherDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Teacher��Ϣ*/
    public String ModifyTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������teacherNumber��ȡTeacher����*/
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

    /*��ѯҪ�޸ĵ�Teacher��Ϣ*/
    public String FrontShowTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������teacherNumber��ȡTeacher����*/
        Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);

        ctx.put("teacher",  teacher);
        return "front_show_view";
    }

    /*�����޸�Teacher��Ϣ*/
    public String ModifyTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String teacherPhotoFileName = ""; 
       	 	if(teacherPhotoFile != null) {
       	 		InputStream is = new FileInputStream(teacherPhotoFile);
       			String fileContentType = this.getTeacherPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
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
            ctx.put("message",  java.net.URLEncoder.encode("Teacher��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Teacher��Ϣ*/
    public String DeleteTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            teacherDAO.DeleteTeacher(teacherNumber);
            ctx.put("message",  java.net.URLEncoder.encode("Teacherɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacherɾ��ʧ��!"));
            return "error";
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [TeacherAction] " + msg);
    }

}
