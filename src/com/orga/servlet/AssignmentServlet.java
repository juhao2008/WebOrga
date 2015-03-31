package com.orga.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.orga.dao.AssignmentDAO;
import com.orga.dao.CourseScheduleDAO;
import com.orga.dao.StudentAssignmentDAO;
import com.orga.domain.Assignment;
import com.orga.domain.CourseSchedule;
import com.orga.domain.StudentAssignment;
import com.orga.utils.CommUtil;

public class AssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = -5445322469249536066L;
	private AssignmentDAO assignmentDAO = new AssignmentDAO();
	private StudentAssignmentDAO assignmentStatusDAO = new StudentAssignmentDAO();
	
	public AssignmentServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getParameter("action");
		final String userName = (String)request.getSession().getAttribute("userName");
		final String classNum = request.getParameter("classNumber");
		
		System.out.println(CommUtil.getCurrentDateTimeStr() + "[AssignmentServlet] action=" + action + ",classNum=" + classNum);
		if("classAssignment".equals(action)) { //all assignments from all course schedules in one class
			try {
				final String classNumber = request.getParameter("classNumber") == null ? ""
						: new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
				List<Map> list = assignmentDAO.QueryClassAssignments(classNumber);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
			
		} if("csAssignment".equals(action)) {//all assignments from one course schedule
			try {
				final String strCSId = request.getParameter("courseScheduleId") == null ? ""
						: new String(request.getParameter("courseScheduleId").getBytes("iso-8859-1"), "UTF-8");
				final int courseScheduleId = Integer.parseInt(strCSId);
				final List<Map> list = assignmentDAO.QueryCourseAssignments(courseScheduleId);
				
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			
		} else if("getStatus".equals(action)) {
			try {
				final String strAssignId = request.getParameter("assignmentId") == null ? ""
						: new String(request.getParameter("assignmentId").getBytes("iso-8859-1"), "UTF-8");
				final int assignmentId = Integer.parseInt(strAssignId);
				final String studentNumber = request.getParameter("studentNumber") == null ? ""
						: new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
				
				final List<Map> list = assignmentStatusDAO.QueryAssignmentStatus(studentNumber, assignmentId);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			
	    } else if("setStatus".equals(action)) { 
			try {
				final String strId = request.getParameter("id") == null ? ""
						: new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
				final int id = Integer.parseInt(strId);
				final String strValue = request.getParameter("statusValue") == null ? ""
						: new String(request.getParameter("statusValue").getBytes("iso-8859-1"), "UTF-8");
				final int value = Integer.parseInt(strValue);
				
				StudentAssignment assignmentStatus = assignmentStatusDAO.GetStudentAssignmentById(id);
				assignmentStatus.setAssignmentStatus(value);
				if(value == StudentAssignment.STATUS_FINISH_UNSIGN) {
					assignmentStatus.setFinishDate(CommUtil.getCurrentDateTimeStr());
				} else if(value == StudentAssignment.STATUS_FINISH_SIGN) {
					assignmentStatus.setSignDate(CommUtil.getCurrentDateTimeStr());
				}
				assignmentStatusDAO.UpdateStudentAssignment(assignmentStatus);
				outSuccessResult(response, null);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getLocalizedMessage());
				ex.printStackTrace();
			}
	    	
	    } else if("add".equals(action)) {
			try {
				final int courseScheduleId = Integer.valueOf(request.getParameter("courseScheduleId"));
				final CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
				final CourseSchedule courseSchedule = courseScheduleDAO.GetCourseScheduleById(courseScheduleId);
				final String assignmentName = request.getParameter("assignmentName") == null ? ""
						: new String(request.getParameter("assignmentName").getBytes("iso-8859-1"), "UTF-8");
				final String assignmentContent = request.getParameter("assignmentContent") == null ? ""
						: new String(request.getParameter("assignmentContent").getBytes("iso-8859-1"), "UTF-8");
				
				Assignment assignment = new Assignment();
				assignment.setCourseSchedule(courseSchedule);
				assignment.setAssignmentName(assignmentName); 
				assignment.setAssignmentContent(assignmentContent);
				assignment.setAssignmentDate(CommUtil.getCurrentDateTimeStr());
				//
				assignmentDAO.AddAssignment(assignment);
				
				outSuccessResult(response, null);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
			
		} else if("edit".equals(action)) {
			try {
				int assignmentId = Integer.valueOf(request.getParameter("assignmentId"));
				Assignment assignment = assignmentDAO.GetAssignmentById(assignmentId);
				final String assignmentName = request.getParameter("assignmentName") == null ? ""
						: new String(request.getParameter("assignmentName").getBytes("iso-8859-1"), "UTF-8");
				assignment.setAssignmentName(assignmentName);
				final String assignmentContent = request.getParameter("assignmentContent") == null ? ""
						: new String(request.getParameter("assignmentContent").getBytes("iso-8859-1"), "UTF-8");
				assignment.setAssignmentContent(assignmentContent);
				
				assignment.setAssignmentDate(CommUtil.getCurrentDateTimeStr());
				assignmentDAO.UpdateAssignment(assignment);
				
				outSuccessResult(response, null);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
			
		} else if("delete".equals(action)) {
			try {
				int assignmentId = Integer.valueOf(request.getParameter("assignmentId"));
				assignmentDAO.DeleteAssignment(assignmentId);
				
				outSuccessResult(response, null);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	
	private void outSuccessResult(HttpServletResponse response, List<Map> list) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", true);
			
			if(list != null && list.size() > 0)
				result.element("list", JSONArray.fromObject(list));
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.close();
		} catch (Exception ex) {
			outErrorResult(response, ex.getMessage());
			ex.printStackTrace();
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
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	
	
	
	
}
