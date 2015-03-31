package com.orga.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.orga.dao.LoginRecordDAO;
import com.orga.dao.StudentDAO;
import com.orga.dao.TeacherDAO;
import com.orga.domain.LoginRecord;
import com.orga.domain.Student;
import com.orga.domain.Teacher;
import com.orga.utils.CommConst;
import com.orga.utils.CommUtil;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4997805743766230668L;
	private StudentDAO studentDao = new StudentDAO();
	private TeacherDAO teacherDao = new TeacherDAO();
	private LoginRecordDAO loginRecordDAO = new LoginRecordDAO();

	public LoginServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("juha get the request.");
		String userName = new String(request.getParameter("userName").getBytes("iso-8859-1"),"utf-8");
		String userPwd = new String(request.getParameter("userPwd").getBytes("iso-8859-1"),"utf-8"); 
		String userRole = new String(request.getParameter("userRole").getBytes("iso-8859-1"),"utf-8");
		System.out.println(CommUtil.getCurrentDateTimeStr() + "[LoginServlet] userName=" + userName + ", userPwd=" + userPwd);
		//save user info
		try {
			if(CommConst.ROLE_R1.equals(userRole)) {//student
				if(!studentDao.CheckStudentUser(userName, userPwd)) {
					outErrorResult(response, studentDao.getErrMessage());
					return;
				}
				//username & password matched
				final Student student = studentDao.GetStudentByNumber(userName);
				//define result
				JSONObject result = new JSONObject();
				result.put("userRole", CommConst.ROLE_R1);
				result.put("userName", userName);
				result.put("userPwd", userPwd);
				result.put("student", student);
				//out print
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				out.print(result);
				
			} else if (CommConst.ROLE_R2.equals(userRole)
					|| CommConst.ROLE_R3.equals(userRole)
					|| CommConst.ROLE_R4.equals(userRole)) {// teacher
				if(!teacherDao.CheckTeacherUser(userName, userPwd)) {
					outErrorResult(response, teacherDao.getErrMessage());
					return;
				}
				updateLoginRecord(userName);
				Teacher teacher = teacherDao.GetTeacherByTeacherNumber(userName);
				//define result
				JSONObject result = new JSONObject();
				result.put("userRole", userRole);
				result.put("userName", userName);
				result.put("userPwd", userPwd);
				result.put("teacher", teacher);
				//out print
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				out.print(result);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			outErrorResult(response, ex.getMessage());
		}
	}
	
	private void outErrorResult(HttpServletResponse response, String errorMsg) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("reason", errorMsg);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void updateLoginRecord(String loginName) {
		LoginRecord loginRecord = loginRecordDAO.GetRecordByName(loginName);
		try {
			if(loginRecord == null) {
				loginRecord = new LoginRecord();
				loginRecord.setLoginName(loginName);
				
				loginRecord.setLoginIp(CommUtil.getRemortIP());
				loginRecord.setLoginTime(CommUtil.getCurrentDateTimeStr());
				
				loginRecord.setLoginWhere("PC browser");
				loginRecordDAO.AddLoginRecord(loginRecord);
			} else {
				loginRecord.setLastestLoginTime(loginRecord.getLoginTime());
				loginRecord.setLoginTime(CommUtil.getCurrentDateTimeStr());
				
				loginRecordDAO.UpdateLoginRecord(loginRecord);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
