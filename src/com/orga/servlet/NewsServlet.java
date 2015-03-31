package com.orga.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orga.dao.NewsDAO;
import com.orga.dao.SchoolDAO;
import com.orga.domain.News;
import com.orga.domain.School;
import com.orga.utils.CommUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NewsServlet extends HttpServlet {

	private static final long serialVersionUID = 8356790027341179590L;
	private NewsDAO newsDao = new NewsDAO();
	
	/*默认构造函数*/
	public NewsServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getParameter("action");
		final String schoolNumber = (String)request.getSession().getAttribute("schoolNumber");
		boolean success = false;
		if("query".equals(action)) {
			JSONObject result = new JSONObject();
			try {
				final String newsTitle = request.getParameter("newsTitle") == null ? ""
						: new String(request.getParameter("newsTitle").getBytes("iso-8859-1"), "UTF-8");
				int currentPage = 1;
				ArrayList<News> list = newsDao.QueryNewsInfo(newsTitle, null, schoolNumber, currentPage);
				final JSONArray jsonArray = JSONArray.fromObject(list);
				success = true;
				result.put("success", success);
				result.element("list", jsonArray);
				
			} catch (Exception ex) {
				success = false;
				ex.printStackTrace();
			}
			result.put("success", success);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			
		} else if("add".equals(action)) {
			News news = new News();
			final String newsTitle = new String(request.getParameter("newsTitle").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsTitle(newsTitle);
			final String newsContent = new String(request.getParameter("newsContent").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsContent(newsContent);
			news.setNewsDate(CommUtil.getCurrentDateTime().toString());
			
            SchoolDAO schoolDAO = new SchoolDAO();
            School school = schoolDAO.GetSchoolByNumber(schoolNumber);
            news.setNewsSchool(school);
			try {
				newsDao.AddNews(news);
				success = true;
			} catch (Exception e) {
				success = false;
				e.printStackTrace();
			}
			JSONObject result = new JSONObject();
			result.put("success", success);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if("update".equals(action)) {
			
			
		} else if("delete".equals(action)) {
			
		}
	}

}
