package com.orga.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.SchoolDAO;
import com.orga.dao.NewsDAO;
import com.orga.domain.School;
import com.orga.domain.News;
import com.orga.utils.CommConst;
import com.orga.utils.CommUtil;

public class NewsAction extends ActionSupport {

	private static final long serialVersionUID = -3280162314065843179L;
	/* ͼƬ�ֶ�newsPhoto�������� */
	private File newsPhotoFile;
	private String newsPhotoFileFileName;
	private String newsPhotoFileContentType;

	public File getNewsPhotoFile() {
		return newsPhotoFile;
	}
	public void setNewsPhotoFile(File newsPhotoFile) {
		this.newsPhotoFile = newsPhotoFile;
	}
	public String getNewsPhotoFileFileName() {
		return newsPhotoFileFileName;
	}
	public void setNewsPhotoFileFileName(String newsPhotoFileFileName) {
		this.newsPhotoFileFileName = newsPhotoFileFileName;
	}
	public String getNewsPhotoFileContentType() {
		return newsPhotoFileContentType;
	}
	public void setNewsPhotoFileContentType(String newsPhotoFileContentType) {
		this.newsPhotoFileContentType = newsPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ���ű���*/
    private String newsTitle;
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
    public String getNewsTitle() {
        return this.newsTitle;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String newsDate;
    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
    public String getNewsDate() {
        return this.newsDate;
    }
    
    /*�������Ҫ��ѯ������: ��������-ѧУ*/
    private School newsSchool;
    public School getNewsSchool() {
		return newsSchool;
	}
	public void setNewsSchool(School newsSchool) {
		this.newsSchool = newsSchool;
	}
	/*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }
    
    private int newsId;
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
    public int getNewsId() {
        return newsId;
    }

    /*ҵ������*/
    private NewsDAO newsDAO = new NewsDAO();

    /*��������News����*/
    private News news;
    public void setNews(News news) {
        this.news = news;
    }
    public News getNews() {
        return this.news;
    }

    /*��ת�����News��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
//        ClassInfoDAO classInfoDAO = new ClassInfoDAO();
//        
//        ArrayList<ClassInfo> classInfoList = classInfoDAO.getClassInfoBySchoolNumber(ctx.getSession().get("schoolNumber").toString());
//        ctx.put("classInfoList", classInfoList);
        return "add_view";
    }

    /*���News��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNews() {
        ActionContext ctx = ActionContext.getContext();
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String newsPhotoFileName = ""; 
       	 	if(newsPhotoFile != null) {
       	 		InputStream is = new FileInputStream(newsPhotoFile);
       			String fileContentType = this.getNewsPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				newsPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				newsPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
       				return "error";
       			}
       			File file = new File(path, newsPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       	 	}
            if(newsPhotoFile != null)
            	news.setNewsPhoto("upload/" + newsPhotoFileName);
            else
            	news.setNewsPhoto("upload/NoImage.jpg");
            
            String schoolNumber = ctx.getSession().get("schoolNumber").toString();
            SchoolDAO schoolDAO = new SchoolDAO();
            School school = schoolDAO.GetSchoolByNumber(schoolNumber);
            news.setNewsSchool(school);
            
            newsDAO.AddNews(news);
            ctx.put("message",  java.net.URLEncoder.encode("News��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("News���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNews��Ϣ*/
    public String QueryNews() {
    	DumpMsg(" QueryNews newsTitle=" +newsTitle + ", newsDate=" + newsDate);
    	ActionContext ctx = ActionContext.getContext();
    	if(ctx == null || ctx.getSession() == null) {
    		return "login_view";
    	}
        if(currentPage == 0) currentPage = 1;
        if(newsTitle == null) newsTitle = "";
        if(newsDate == null) newsDate = "";
        String schoolNumber = null;
        if(newsSchool != null ) {
        	schoolNumber = newsSchool.getSchoolNumber();
        } else {
        	schoolNumber = (String)ctx.getSession().get(CommConst.USER_SCHOOL_NUMBER);
        }
        
        List<News> newsList = newsDAO.QueryNewsInfo(newsTitle, newsDate, schoolNumber, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        newsDAO.CalculateTotalPageAndRecordNumber(newsTitle, newsDate, schoolNumber);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = newsDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = newsDAO.getRecordNumber();
        
        ctx.put("newsList",  newsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("newsTitle", newsTitle);
        ctx.put("newsDate", newsDate);
        return "query_view";
    }

    /*��ѯҪ�޸ĵ�News��Ϣ*/
    public String ModifyNewsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������newsId��ȡNews����*/
        News news = newsDAO.GetNewsByNewsId(newsId);

        ctx.put("news",  news);
        return "modify_view";
    }

    /*�����޸�News��Ϣ*/
    public String ModifyNews() {
        ActionContext ctx = ActionContext.getContext();
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String newsPhotoFileName = ""; 
       	 	if(newsPhotoFile != null) {
       	 		InputStream is = new FileInputStream(newsPhotoFile);
       			String fileContentType = this.getNewsPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				newsPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				newsPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
       				return "error";
       			}
       			File file = new File(path, newsPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       			news.setNewsPhoto("upload/" + newsPhotoFileName);
       	 	}
       	 	String schoolNumber = ctx.getSession().get("schoolNumber").toString();
       	 	SchoolDAO schoolDAO = new SchoolDAO();
       	 	School school = schoolDAO.GetSchoolByNumber(schoolNumber);
       	 	news.setNewsSchool(school);
       	 	
            newsDAO.UpdateNews(news);
            ctx.put("message",  java.net.URLEncoder.encode("News��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("News��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��News��Ϣ*/
    public String DeleteNews() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            newsDAO.DeleteNews(newsId);
            ctx.put("message",  java.net.URLEncoder.encode("Newsɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Newsɾ��ʧ��!"));
            return "error";
        }
    }
    
    private void DumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [NewsAction] " + msg);
    }

}
