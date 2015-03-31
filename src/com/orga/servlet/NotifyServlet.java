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

import com.orga.dao.NotifyDAO;
import com.orga.domain.Notify;
import com.orga.utils.CommUtil;

public class NotifyServlet extends HttpServlet{
	private static final long serialVersionUID = -961147808802097378L;
	private NotifyDAO notifyDAO = new NotifyDAO();
	
	public NotifyServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		final String action = request.getParameter("action");
		final String userName = (String)request.getSession().getAttribute("userName");
		
		System.out.println(CommUtil.getCurrentDateTimeStr() + "[CourseScheduleServlet] action=" + action);
		
		if("queryNotify".equals(action)) {
			try {
				final String classNumber = request.getParameter("classNumber") == null ? ""
						: new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
				List<Map> list = notifyDAO.QueryStudentNotify(classNumber);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
		} else if("addNotify".equals(action)) {
			
			try {
				Notify notify = new Notify();
				notifyDAO.AddNotify(notify);
				
				outSuccessResult(response, null);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
			
		} else if("edit".equals(action)) {
			
		} else if("delete".equals(action)) {
			
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
