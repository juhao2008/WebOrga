package com.orga.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.orga.dao.CourseScheduleDAO;
import com.orga.domain.CourseSchedule;
import com.orga.utils.CommUtil;

public class CourseScheduleServlet extends HttpServlet{
	private static final long serialVersionUID = 4615587207993548549L;
	private CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
	
	public CourseScheduleServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		final String action = request.getParameter("action");
		final String userName = (String)request.getSession().getAttribute("userName");
		//final String classNumber = (String)request.getSession().getAttribute("classNumber");
		//final String schoolNumber = (String)request.getSession().getAttribute("schoolNumber");
		
		System.out.println(CommUtil.getCurrentDateTimeStr() + "[CourseScheduleServlet] action=" + action);
		
		if("queryT".equals(action)) {
			try {
				final String teacherNumber = request.getParameter("teacherNumber");
				List<Map> list = courseScheduleDAO.QueryTeacherCSs(teacherNumber);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			
		} else if("queryC".equals(action)) {
			try {
				final String classNumber = request.getParameter("classNumber");
				List<Map> list = courseScheduleDAO.QueryClassCSs(classNumber);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			
		} else if("queryDetail".equals(action)) {
			try {
				final String strCSId = request.getParameter("courseScheduleId");
				final int courseScheduleId = Integer.parseInt(strCSId);
				List<Map> list = courseScheduleDAO.QueryCSDetail(courseScheduleId);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getLocalizedMessage());
				ex.printStackTrace();
			}
		}
	}
	
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
	
	private void outSuccessResult(HttpServletResponse response, ArrayList<CourseSchedule> list) {
		try {
			final JSONArray jsonArray = JSONArray.fromObject(list);
			JSONObject result = new JSONObject();
			result.put("success", true);
			result.element("list", jsonArray);
			
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
