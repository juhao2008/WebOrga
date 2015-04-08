package com.orga.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UpPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpPhotoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		final String action = request.getParameter("action");
		System.out.println("juhao debug UpPhotoServlet action=" + action);
		
		String loadpath=this.getServletConfig().getServletContext().getRealPath("/")+"upload";
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		upload.setSizeMax(6 * 1024 * 1024); // 设置允许用户上传文件大小,单位:字节
//		fu.setSizeThreshold(2048); // 设置最多只允许在内存中存储的数据,单位:字节
 
		// 开始读取上传信息
		int index = 0;
		List<FileItem> fileItems = null;

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart) {
			try {
				fileItems = upload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("not Multipart data.");
		}
		String filename = "";

		Iterator iter = fileItems.iterator(); // 依次处理每个上传的文件
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();// 忽略其他不是文件域的所有表单信息
			if (!item.isFormField()) {
			    filename = item.getName();// 获取上传文件名,包括路径
			    System.out.println("@@@@@@@@@@@@@@@@@@@@@ "+filename); 
			    
				filename = filename.substring(filename.lastIndexOf("\\") + 1);// 从全路径中提取文件名
				long size = item.getSize();
				if ((filename == null || filename.equals("")) && size == 0)
					continue;
				index++; 
				File file = new File(loadpath);
				if(!file.exists()){
					file.mkdirs();
				}
				File fNew = new File(loadpath, filename);
				try {
					item.write(fNew);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			} else// 取出不是文件域的所有表单信息
			{
				String fieldvalue = item.getString();
				// 如果包含中文应写为：(转为UTF-8编码)
				// String fieldvalue = new
				// String(item.getString().getBytes(),"UTF-8");
			}
		}
		
		String newUrl = "upload/" + filename;
		PrintWriter out = response.getWriter();
		out.print(newUrl);
	}

}
