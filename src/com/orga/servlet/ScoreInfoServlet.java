package com.orga.servlet;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.orga.dao.ScoreInfoDAO;
import com.orga.utils.CommUtil;

public class ScoreInfoServlet extends HttpServlet{
	private static final long serialVersionUID = 6615100547817871143L;
	private ScoreInfoDAO scoreInfoDAO = new ScoreInfoDAO();
	
	public ScoreInfoServlet() {
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
		
		if("studentScoreInfo".equals(action)) {
			try {
				final String studentNumber = request.getParameter("studentNumber");
				List<Map> list = scoreInfoDAO.QueryStudentScoreList(studentNumber);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
			
		} else if("ScoreInfo".equals(action)) {
			try {
				final String strTestId = request.getParameter("testId");
				final int testId = Integer.parseInt(strTestId);
				List<Map> list = scoreInfoDAO.QueryScoreInfoDetail(testId);
				outSuccessResult(response, list);
				
			} catch (Exception ex) {
				outErrorResult(response, ex.getMessage());
				ex.printStackTrace();
			}
			
		} else if("delete".equals(action)) {
			
		}
	}
	
	private void outSuccessResult(HttpServletResponse response, List<Map> list) {
		try {
			JSONObject result = new JSONObject();
			result.put("success", true);
			if (list != null) {
				result.element("list", JSONArray.fromObject(list));
			}
			
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
