package com.orga.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;


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
			
		} else if("upload".equals(action)) {
			String loadpath=this.getServletConfig().getServletContext().getRealPath("/")+"upload";
			String temp = this.getServletConfig().getServletContext().getRealPath("/")+"upload/temp"; // ��ʱĿ¼
			System.out.println("temp=" + temp);
			System.out.println("loadpath=" + loadpath);
			DiskFileUpload fu = new DiskFileUpload();
			fu.setSizeMax(6 * 1024 * 1024); // ���������û��ϴ��ļ���С,��λ:�ֽ�
			fu.setSizeThreshold(2048); // �������ֻ�������ڴ��д洢������,��λ:�ֽ�
			fu.setRepositoryPath(temp); // ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
	 
			// ��ʼ��ȡ�ϴ���Ϣ
			int index = 0;
			List fileItems = null;

			try {
				fileItems = fu.parseRequest(request);
				System.out.println("fileItems=" + fileItems);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String filename = "";

			Iterator iter = fileItems.iterator(); // ���δ���ÿ���ϴ����ļ�
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();// �������������ļ�������б���Ϣ
				if (!item.isFormField()) {
				    filename = item.getName();// ��ȡ�ϴ��ļ���,����·��
				    System.out.println("@@@@@@@@@@@@@@@@@@@@@ "+filename); 
					filename = filename.substring(filename.lastIndexOf("\\") + 1);// ��ȫ·������ȡ�ļ���
					long size = item.getSize();
					if ((filename == null || filename.equals("")) && size == 0)
						continue;
					int point = filename.indexOf(".");
//					name = (new Date()).getTime()
//							+ name.substring(point, name.length());
					index++; 
					File file = new File(loadpath);
					if(!file.exists()){
						System.out.println("==========================================");
						file.mkdirs();
					}
					Date date = new Date();
					String timeString =  "" + date.getYear() + date.getMonth() + date.getDay() + date.getHours() + date.getMinutes() + date.getSeconds();
					filename = timeString + filename;
					File fNew = new File(loadpath, filename);
					try {
						item.write(fNew);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				} else// ȡ�������ļ�������б���Ϣ
				{
					String fieldvalue = item.getString();
					// �����������ӦдΪ��(תΪUTF-8����)
					// String fieldvalue = new
					// String(item.getString().getBytes(),"UTF-8");
				}
			}
			
			String newUrl = "upload/" + filename;
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(newUrl);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%  "+ filename);
			
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
