package com.orga.servlet;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.orga.dao.ClassInfoDAO;
import com.orga.domain.ClassInfo;
import com.orga.utils.CommUtil;

public class ClassInfoServlet extends HttpServlet{
	private static final long serialVersionUID = -961147808802097378L;
	private ClassInfoDAO classInfoDAO = new ClassInfoDAO();
	
	public ClassInfoServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		final String action = request.getParameter("action");
		final String userName = (String)request.getSession().getAttribute("userName");
		final String classNumber = (String)request.getSession().getAttribute("classNumber");
		final String schoolNumber = (String)request.getSession().getAttribute("schoolNumber");
		
		System.out.println(CommUtil.getCurrentDateTimeStr() + "[ClassInfoServlet] action=" + action);
		
		if("classList".equals(action)) {
			try {
				final String teacherNumber = request.getParameter("teacherNumber");
				List<Map> list = classInfoDAO.QueryClassSumary(teacherNumber);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
		}
		
	}
	
	private void outSuccessResult(HttpServletResponse response, ClassInfo classInfo) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", true);
			result.element("classInfo", classInfo);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			
		} catch (Exception ex) {
			outErrorResult(response, ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void outSuccessResult(HttpServletResponse response, List<Map> list) {
		try {
			final JSONArray jsonArray = JSONArray.fromObject(list);
			JSONObject result = new JSONObject();
			result.put("success", true);
			if(list != null) {
				result.element("list", jsonArray);
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
