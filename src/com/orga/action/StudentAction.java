package com.orga.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.GradeInfoDAO;
import com.orga.dao.StudentDAO;
import com.orga.domain.GradeInfo;
import com.orga.domain.Student;
import com.orga.dao.ClassInfoDAO;
import com.orga.domain.ClassInfo;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class StudentAction extends ActionSupport {
	private static final long serialVersionUID = 1975693257166549523L;
	/* ͼƬ�ֶ�studentPhoto�������� */
	private File studentPhotoFile;
	private String studentPhotoFileFileName;
	private String studentPhotoFileContentType;

	public File getStudentPhotoFile() {
		return studentPhotoFile;
	}
	public void setStudentPhotoFile(File studentPhotoFile) {
		this.studentPhotoFile = studentPhotoFile;
	}
	public String getStudentPhotoFileFileName() {
		return studentPhotoFileFileName;
	}
	public void setStudentPhotoFileFileName(String studentPhotoFileFileName) {
		this.studentPhotoFileFileName = studentPhotoFileFileName;
	}
	public String getStudentPhotoFileContentType() {
		return studentPhotoFileContentType;
	}
	public void setStudentPhotoFileContentType(String studentPhotoFileContentType) {
		this.studentPhotoFileContentType = studentPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ѧ��*/
    private String studentNumber;
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    public String getStudentNumber() {
        return this.studentNumber;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String studentName;
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentName() {
        return this.studentName;
    }

    /*�������Ҫ��ѯ������: ���ڰ༶*/
    private ClassInfo studentClass;
    public ClassInfo getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(ClassInfo studentClass) {
		this.studentClass = studentClass;
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
    private StudentDAO studentDAO = new StudentDAO();
    private ClassInfoDAO classInfoDAO = new ClassInfoDAO();

    /*��������Student����*/
    private Student student;
    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return this.student;
    }
    /* excel ���������ļ�*/
    private File excelFile;
    public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
	
	private String classNumber;
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
    public String getClassNumber() {
        return this.classNumber;
    }
    
    /*��ת�����Student��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        GradeInfoDAO gradeInfoDAO = new GradeInfoDAO();
        ArrayList<GradeInfo> gradeInfoList = gradeInfoDAO.QueryAllGradeInfo();
        ctx.put("gradeInfoList", gradeInfoList);
        
        /*��ѯ���е�ClassInfo��Ϣ*/
        try {
        	String schoolNumber = (String) ctx.getSession().get("schoolNumber");
            ClassInfoDAO classInfoDAO = new ClassInfoDAO();
            List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfo(null, schoolNumber, null, null);
            ctx.put("classInfoList", classInfoList);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        return "add_view";
    }

    /*���Student��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddStudent() {
        ActionContext ctx = ActionContext.getContext();
        student.setStudentNumber(HibernateUtil.generateRecordId("STU"));
        /*��֤ѧ���Ƿ��Ѿ�����*/
        String studentNumber = student.getStudentNumber();
        Student db_student = null;
        try {
        	db_student = studentDAO.GetStudentByNumber(studentNumber);
        	if(null != db_student) {
                ctx.put("error",  java.net.URLEncoder.encode("��ѧ���Ѿ�����!"));
                return "error";
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        try {
        	GradeInfoDAO gradeInfoDAO = new GradeInfoDAO();
        	GradeInfo gradeInfo = gradeInfoDAO.GetGradeInfoByGradeNumber(student.getStudentGrade().getGradeNumber());
        	student.setStudentGrade(gradeInfo);
        	
            ClassInfoDAO classInfoDAO = new ClassInfoDAO();
            ClassInfo studentClass = classInfoDAO.GetClassInfoByClassNumber(student.getStudentClass().getClassNumber());
            student.setStudentClass(studentClass);
            
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String studentPhotoFileName = ""; 
       	 	if(studentPhotoFile != null) {
       	 		InputStream is = new FileInputStream(studentPhotoFile);
       			String fileContentType = this.getStudentPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				studentPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				studentPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!", "UTF-8"));
       				return "error";
       			}
       			File file = new File(path, studentPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       	 	}
            if(studentPhotoFile != null)
            	student.setStudentPhoto("upload/" + studentPhotoFileName);
            else
            	student.setStudentPhoto("upload/NoImage.jpg");
            studentDAO.AddStudent(student);
            
            ctx.put("message",  java.net.URLEncoder.encode("ѧ����ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Student���ʧ��!"));
            return "error";
        }
    }
    
    /**
     * excel �����ϴ�
     * @return
     */
    public String UploadExcelStudents() {
		DumpMsg("UploadExcelFile excelFile is Null ? " + (excelFile == null) + ", classNumber=" + classNumber);
		ActionContext ctx = ActionContext.getContext();
		if(excelFile == null) {
			ctx.put("error",  java.net.URLEncoder.encode("excel�ļ��Ҳ���!"));
			return "error";
		}
		InputStream is = null;
		try {
			is = new FileInputStream(excelFile);
			HSSFWorkbook book = new HSSFWorkbook(is);
			final List<Student> list = getAllStudents(book);
			for(Student stu : list) {
//				DumpMsg("student===> " + stu.getStudentName() + "/" + stu.getStudentPassword());
				studentDAO.AddStudent(stu);
			}
			book.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.put("error",  java.net.URLEncoder.encode("ѧ����Ϣ���ʧ��!"));
            return "error";
		} finally {
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ctx.put("message",  java.net.URLEncoder.encode("ѧ����Ϣ��ӳɹ�!"));
        return "add_success";
	}
	
	private List<Student> getAllStudents(HSSFWorkbook book) {
		List<Student> list = new ArrayList<Student>();
		Student student = null;
		try {
			HSSFSheet sheet = book.getSheetAt(0);
			HSSFRow row = null;
			for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
				row = sheet.getRow(j);
				student = new Student();
				student.setStudentNumber(HibernateUtil.generateRecordId("STU"));
				student.setStudentName(row.getCell(0) != null ? row.getCell(0).toString() : "");
				student.setStudentId(row.getCell(1) != null ? row.getCell(1).toString() : "");
				student.setStudentPassword(row.getCell(2) != null ? row.getCell(2).toString() : "");
				student.setStudentSex(row.getCell(3) != null ? row.getCell(3).toString() : "");
				student.setStudentIDCard(row.getCell(4) != null ? row.getCell(4).toString() : "");
				student.setStudentBirthday(row.getCell(5) != null ? row.getCell(5).toString() : "");
				student.setStudentTelephone(row.getCell(6) != null ? row.getCell(6).toString() : "");
				student.setStudentEmail(row.getCell(7) != null ? row.getCell(7).toString() : "");
				student.setStudentQQ(row.getCell(8) != null ? row.getCell(8).toString() : "");
				student.setStudentAddress(row.getCell(9) != null ? row.getCell(9).toString() : "");
				student.setStudentMemo(row.getCell(10) != null ? row.getCell(10).toString() : "");
				final ClassInfoDAO classInfoDAO = new ClassInfoDAO();
	            final ClassInfo studentClass = classInfoDAO.GetClassInfoByClassNumber(classNumber);
	            student.setStudentClass(studentClass);
	            student.setStudentGrade(studentClass.getClassGrade());
				list.add(student);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
    
    public String QueryClassStudent() {
    	if(currentPage == 0) currentPage = 1;
    	String classNumber = null;
    	if(studentClass != null)
    		classNumber = studentClass.getClassNumber();
    	ActionContext ctx = ActionContext.getContext();
//    	ServletRequest request=ServletActionContext.getRequest();
//    	String juid=request.getParameter("juid"); 
//    	DumpMsg(" QueryClassStudent  classNumber=" + classNumber + ", juid=" + juid);
    	try {
    		List<Student> studentList = studentDAO.QueryStudentInfo(null, null, classNumber, null, currentPage);
            /*�����ܵ�ҳ�����ܵļ�¼��*/
            studentDAO.CalculateTotalPageAndRecordNumber(null, null, classNumber, null);
            /*��ȡ���ܵ�ҳ����Ŀ*/
            totalPage = studentDAO.getTotalPage();
            /*��ǰ��ѯ�������ܼ�¼��*/
            recordNumber = studentDAO.getRecordNumber();
            
            ctx.put("totalPage", totalPage);
            ctx.put("recordNumber", recordNumber);
            ctx.put("currentPage", currentPage);
            ctx.put("classNumber", classNumber);
            ctx.put("studentList",  studentList);
            
            return "query_class_student";
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		ctx.put("error",  java.net.URLEncoder.encode("��ѯѧ����Ϣʧ��!"));
    		return "error";
    	}
    }
    
    /*��ѯStudent��Ϣ*/
    public String QueryStudent() {
        if(currentPage == 0) currentPage = 1;
        String classNumber = null;
    	if(studentClass != null)
    		classNumber = studentClass.getClassNumber();
    	ActionContext ctx = ActionContext.getContext();
    	try {
    		List<Student> studentList = studentDAO.QueryStudentInfo(studentNumber, studentName, classNumber, null, currentPage);
            /*�����ܵ�ҳ�����ܵļ�¼��*/
            studentDAO.CalculateTotalPageAndRecordNumber(studentNumber, studentName, classNumber, null);
            /*��ȡ���ܵ�ҳ����Ŀ*/
            totalPage = studentDAO.getTotalPage();
            /*��ǰ��ѯ�������ܼ�¼��*/
            recordNumber = studentDAO.getRecordNumber();
            ctx.put("studentList",  studentList);
            ctx.put("totalPage", totalPage);
            ctx.put("recordNumber", recordNumber);
            ctx.put("currentPage", currentPage);
            ctx.put("studentNumber", studentNumber);
            ctx.put("studentName", studentName);
            ctx.put("studentClass", studentClass);
            
            String schoolNumber = (String)ctx.getSession().get("schoolNumber");
            ClassInfoDAO classInfoDAO = new ClassInfoDAO();
            List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfo(null, schoolNumber, null, null);
            
            ctx.put("classInfoList", classInfoList);
            return "query_view";
            
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		ctx.put("error",  java.net.URLEncoder.encode("��ѯѧ����Ϣʧ��!"));
    		return "error";
    	}
    }


    /*��ѯҪ�޸ĵ�Student��Ϣ*/
    public String ModifyStudentQuery() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	/*��������studentNumber��ȡStudent����*/
            Student student = studentDAO.GetStudentByNumber(studentNumber);
            
            GradeInfoDAO gradeInfoDAO = new GradeInfoDAO();
            List<GradeInfo> gradeInfoList = gradeInfoDAO.QueryAllGradeInfo();
            ctx.put("gradeInfoList", gradeInfoList);
            
            String schoolNumber = (String)ctx.getSession().get("schoolNumber");
            ClassInfoDAO classInfoDAO = new ClassInfoDAO();
            List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfo(null, schoolNumber, null, null);
            
            ctx.put("classInfoList", classInfoList);
            ctx.put("student",  student);
            return "modify_view";
        } catch (Exception ex) {
        	ex.printStackTrace();
        	ctx.put("error",  java.net.URLEncoder.encode("�޸�ѧ����Ϣʧ��!"));
    		return "error";
        }
    }

    /*�����޸�Student��Ϣ*/
    public String ModifyStudent() {
    	ActionContext ctx = ActionContext.getContext();
        try {
            GradeInfoDAO gradeInfoDAO = new GradeInfoDAO();
            GradeInfo gradeInfo = gradeInfoDAO.GetGradeInfoByGradeNumber(student.getStudentGrade().getGradeNumber());
            student.setStudentGrade(gradeInfo);
            	
            ClassInfoDAO classInfoDAO = new ClassInfoDAO();
            ClassInfo studentClass = classInfoDAO.GetClassInfoByClassNumber(student.getStudentClass().getClassNumber());
            student.setStudentClass(studentClass);
            
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String studentPhotoFileName = ""; 
       	 	if(studentPhotoFile != null) {
       	 		InputStream is = new FileInputStream(studentPhotoFile);
       			String fileContentType = this.getStudentPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				studentPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				studentPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
       				return "error";
       			}
       			File file = new File(path, studentPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       			student.setStudentPhoto("upload/" + studentPhotoFileName);
       	 	}
            studentDAO.UpdateStudent(student);
            ctx.put("message",  java.net.URLEncoder.encode("Student��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Student��Ϣ����ʧ��!"));
            return "error";
       }
   }
    
    /*ɾ��Student��Ϣ*/
    public String DeleteStudent() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentDAO.DeleteStudent(studentNumber);
            ctx.put("message",  java.net.URLEncoder.encode("Studentɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Studentɾ��ʧ��!"));
            return "error";
        }
    }
    
    public void DumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [StudentAction] " + msg);
    }

}
