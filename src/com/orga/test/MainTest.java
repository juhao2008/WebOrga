package com.orga.test;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.orga.dao.RoleDAO;
import com.orga.domain.ClassInfo;
import com.orga.domain.GradeInfo;
import com.orga.domain.Role;


public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		testJson();
		
//		RoleDAO roleDao = new RoleDAO();
//		ArrayList<Role> roleList = roleDao.QueryAllRoles();
//		if(roleList != null){
//			System.out.println(">>" + roleList.size());
//		} else {
//			System.out.println("roleList is null.");
//		}
	}
	
	private void testHttpCall() {
		String BASE_URL = "http://127.0.0.1/WebRoot/NewsServlet?action=query&newsTitle=tesa";
		
	}
	
	private static void testJson(){
		final String testResult = "{'success':true,'list':[{'studentNumber':'STU1426174249972','studentName':'郝俊','parentName':'郝中俊'},{'studentNumber':'STU1426174249977','studentName':'张一山','parentName':'张大仙'},{'studentNumber':'STU201305030317','studentName':'双鱼林','parentName':'双双'}]}";
		System.out.println("start ... " + testResult);
		try {
			Gson gson = new Gson();
			Object obj = gson.fromJson(testResult, Result.class);
			if (obj != null ) {
				Result data = (Result) obj;
//				System.out.println(data);
//				Stu[] list = data.getList();
				ArrayList<Stu> list = data.getList();
				for(Stu s : list) {
					System.out.println(s.getStudentName() + "-" + s.getParentName());
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public class Result {
		private boolean success;
		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public ArrayList<Stu> getList() {
			return list;
		}

		public void setList(ArrayList<Stu> list) {
			this.list = list;
		}

		private ArrayList<Stu> list;
		
		public String toString() {
			String str = "";
			for(Stu s : list) {
				str += s.toString();
			}
			return success + str;
		}
	}

	public class Stu {
		private String studentNumber;
		private String studentName;
		private String parentName;
		public String getStudentNumber() {
			return studentNumber;
		}
		public void setStudentNumber(String studentNumber) {
			this.studentNumber = studentNumber;
		}
		public String getStudentName() {
			return studentName;
		}
		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}
		public String getParentName() {
			return parentName;
		}
		public void setParentName(String parentName) {
			this.parentName = parentName;
		}
		public String toString() {
			return studentNumber + studentName + parentName;
		}
		/* 性别 */
	    private String studentSex;
	    public String getStudentSex() {
	        return studentSex;
	    }
	    public void setStudentSex(String studentSex) {
	        this.studentSex = studentSex;
	    }
	    
	    private String studentIDCard;
		public String getStudentIDCard() {
			return studentIDCard;
		}
		public void setStudentIDCard(String studentIDCard) {
			this.studentIDCard = studentIDCard;
		}

		/*所在年级*/
		private GradeInfo studentGrade;
		public GradeInfo getStudentGrade() {
			return studentGrade;
		}
		public void setStudentGrade(GradeInfo studentGrade) {
			this.studentGrade = studentGrade;
		}

		/*所在班级*/
	    private ClassInfo studentClass;
	    public ClassInfo getStudentClass() {
	        return studentClass;
	    }
	    public void setStudentClass(ClassInfo studentClassNumber) {
	        this.studentClass = studentClassNumber;
	    }

	    /*出生日期*/
	    private String studentBirthday;
	    public String getStudentBirthday() {
	        return studentBirthday;
	    }
	    public void setStudentBirthday(String studentBirthday) {
	        this.studentBirthday = studentBirthday;
	    }
	}
}
