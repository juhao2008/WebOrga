package com.orga.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.domain.News;
import com.orga.domain.Student;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class ExcelAction extends ActionSupport{
	private File excelFile;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
	
	public String UploadExcelStudents() {
		DumpMsg("UploadExcelFile excelFile is Null ? " + (excelFile == null));
		ActionContext ctx = ActionContext.getContext();
		if(excelFile == null) {
			ctx.put("error",  java.net.URLEncoder.encode("excel文件找不到!"));
			return "error";
		}
		InputStream is = null;
		try {
			is = new FileInputStream(excelFile);
			HSSFWorkbook book = new HSSFWorkbook(is);
			final List<Student> list = getAllStudents(book);
			for(Student stu : list) {
				System.out.println("student===> " + stu.getStudentName() + "/" + stu.getStudentIDCard());
			}
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	private List<Student> getAllStudents(HSSFWorkbook book) {
		List<Student> list = new ArrayList<Student>();
		Student student = null;
		try {
			HSSFSheet sheet = book.getSheetAt(0);
			HSSFRow row = null;
			for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
				row = sheet.getRow(j);
				student = new Student();
				student.setStudentNumber(HibernateUtil.generateRecordId("STU"));
				student.setStudentName(row.getCell(0).toString());
				
				list.add(student);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	private List<News> getAllNews(HSSFWorkbook book) {
		List<News> list = new ArrayList<News>();
		News news = null;
		try {
			HSSFSheet sheet = book.getSheetAt(0);
			HSSFRow row = null;
			for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
				row = sheet.getRow(j);
				news = new News();
				String title = row.getCell(0).toString();
				String content = row.getCell(1).toString();
				
				news.setNewsTitle(title);
				news.setNewsContent(content);
				news.setNewsDate(CommUtil.getCurrentDateTime().toString());
				
				list.add(news);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return list;
	}
	
	private void DumpMsg(String msg) {
		System.out.println(CommUtil.getCurrentDateTimeStr() + " [ExcelAction] " + msg);
	}
	
}
