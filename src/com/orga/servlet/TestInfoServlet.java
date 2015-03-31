package com.orga.servlet;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.orga.dao.CourseScheduleDAO;
import com.orga.dao.TestInfoDAO;
import com.orga.domain.CourseSchedule;
import com.orga.domain.TestInfo;
import com.orga.utils.CommUtil;

public class TestInfoServlet extends HttpServlet{
	private static final long serialVersionUID = 6615100547817871143L;
	private TestInfoDAO testInfoDAO = new TestInfoDAO();
	
	public TestInfoServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		final String action = request.getParameter("action");
		final String userName = (String)request.getSession().getAttribute("userName");
		final String classNumber = (String)request.getSession().getAttribute("classNumber");
		final String schoolNumber = (String)request.getSession().getAttribute("schoolNumber");
		
		System.out.println(CommUtil.getCurrentDateTimeStr() + "[TestInfoServlet] action=" + action);
		
		if("courseTestList".equals(action)) {
			try {
				final int courseScheduleId = (Integer)request.getSession().getAttribute("courseScheduleId");
				final ArrayList<TestInfo> list = testInfoDAO.QueryTestInfo(null, courseScheduleId);
				outSuccessResult(response, list);
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
			
		} if("classTestInfo".equals(action)) { //unused
			try {
				final CourseScheduleDAO courseScheduleDAO = new CourseScheduleDAO();
				ArrayList<CourseSchedule> reqList = courseScheduleDAO.QueryCourseSchedule(0, classNumber, null, null, null);
				final ArrayList<TestInfo> list = testInfoDAO.GetAllTestInfo(reqList);
				outSuccessResult(response, list);
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
			
		} else if("edit".equals(action)) {
			
		} else if("delete".equals(action)) {
			
		}
	}
	
	private void outSuccessResult(HttpServletResponse response, ArrayList<TestInfo> list) {
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
