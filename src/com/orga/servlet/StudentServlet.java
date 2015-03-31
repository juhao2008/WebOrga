package com.orga.servlet;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.orga.dao.ParentDAO;
import com.orga.dao.StudentDAO;
import com.orga.domain.Parent;
import com.orga.domain.Student;
import com.orga.utils.CommUtil;

public class StudentServlet extends HttpServlet{
	private static final long serialVersionUID = -961147808802097378L;
	private StudentDAO studentDAO = new StudentDAO();
	private ParentDAO parentDAO = new ParentDAO();
	
	public StudentServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		final String action = request.getParameter("action");
		final String userName = (String)request.getSession().getAttribute("userName");
//		final String classNumber = (String)request.getSession().getAttribute("classNumber");
//		final String schoolNumber = (String)request.getSession().getAttribute("schoolNumber");
		
		System.out.println(CommUtil.getCurrentDateTimeStr() + "[StudentServlet] action=" + action);
		
		if("queryList".equals(action)) {//full student info
			try {
				final String classNumber = request.getParameter("classNumber");
				ArrayList<Student> list = studentDAO.QueryStudentList(null, null, classNumber);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
		} else if("queryClass".equals(action)) {//part student info
			try {
				final String classNumber = request.getParameter("classNumber");
				List<Map> list = studentDAO.QueryClassStudents(classNumber);
		    	outSuccessResult(response, list);
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
		
		} else if("queryCourse".equals(action)) {//part student info
			try {
				final String classNumber = request.getParameter("classNumber");
				final String strId = request.getParameter("CourseScheduleId");
				final int courseScheduleId = Integer.valueOf(strId);
				List<Map> list = studentDAO.QueryClassCourseStudents(classNumber, courseScheduleId);
		    	outSuccessResult(response, list);
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
		} else if("query".equals(action)) {
			try {
				final String studentNumber = request.getParameter("studentNumber");
//				Student student = studentDAO.GetStudentByNumber(studentNumber);
				//get the student from parentDAO, because parent contains student info
				ArrayList<Parent> list = parentDAO.QueryStudentParent(studentNumber, null);
				if(list != null && list.size() > 0) {
					outSuccessResult(response, list.get(0));
				}
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
		} else if("update".equals(action)) {
			try {
				final String studentNumber = request.getParameter("studentNumber");
				final String studentName = request.getParameter("studentName");
				
				final String parentName = request.getParameter("parentName");
				final String parentPhone = request.getParameter("parentPhone");
				final String parentAddress = request.getParameter("parentAddress");
				//update student
				Student student = studentDAO.GetStudentByNumber(studentNumber);
				student.setStudentName(studentName);
				studentDAO.UpdateStudent(student);
				
				//update parent
				ArrayList<Parent> list = parentDAO.QueryStudentParent(studentNumber, null);
				Parent parent = list.get(0);
				parent.setParentName(parentName);
				parent.setParentTelephone(parentPhone);
				parent.setParentAddress(parentAddress);
				parentDAO.UpdateParent(parent);
				//out result
				outSuccessResult(response, parent);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * one Parent data
	 * @param response
	 * @param stu
	 */
	private void outSuccessResult(HttpServletResponse response, Parent partent) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", true);
			if(partent != null) {
				result.put("partent", partent);
			}
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} catch (Exception ex) {
			outErrorResult(response, ex.getMessage());
			ex.printStackTrace();
		}
	}
	

	/**
	 * one Student data
	 * @param response
	 * @param stu
	 */
	private void outSuccessResult(HttpServletResponse response, Student stu) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", true);
			if(stu != null) {
				result.put("student", stu);
			}
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} catch (Exception ex) {
			outErrorResult(response, ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * part Student elements
	 * @param response
	 * @param list
	 */
	private void outSuccessResult(HttpServletResponse response, List<Map> list) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", true);
			if(list != null) {
				result.element("list", JSONArray.fromObject(list));
			}
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} catch (Exception ex) {
			outErrorResult(response, ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * full Student elements
	 * @param response
	 * @param list
	 */
	private void outSuccessResult(HttpServletResponse response, ArrayList<Student> list) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", true);
			if(list != null) {
				result.element("list", JSONArray.fromObject(list));
			}
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} catch (Exception ex) {
			outErrorResult(response, ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private  void ontSuccess(HttpServletResponse response) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", true);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} catch (Exception ex) {
			outErrorResult(response, ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param response
	 * @param errorMsg
	 */
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
	
}
