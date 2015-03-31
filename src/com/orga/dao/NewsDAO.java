package com.orga.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;
import com.orga.domain.News;

public class NewsDAO {

    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*��ѯNews��Ϣ*/
    public ArrayList<News> QueryNewsInfo(String newsTitle,String newsDate, String schoolNumber, int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From News news where 1=1";
            if(CommUtil.isNotNull(newsTitle)) 
            	hql += " and news.newsTitle like '%" + newsTitle + "%'";
            if(CommUtil.isNotNull(schoolNumber)) {
            	hql += "and news.newsSchool.schoolNumber like '%" + schoolNumber + "%'";
            }
            if(CommUtil.isNotNull(newsDate)) 
            	hql += " and news.newsDate like '%" + newsDate + "%'";
            hql += " order by news.newsDate desc";
            
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List newsList = q.list();
            return (ArrayList<News>) newsList;
            
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String newsTitle,String newsDate, String schoolNumber) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From News news where 1=1";
            if(CommUtil.isNotNull(newsTitle))
            	hql += " and news.newsTitle like '%" + newsTitle + "%'";
            if(CommUtil.isNotNull(schoolNumber)) {
            	hql += "and news.newsSchool.schoolNumber like '%" + schoolNumber + "%'";
            }
            if(CommUtil.isNotNull(newsDate)) 
            	hql += " and news.newsDate like '%" + newsDate + "%'";
            Query q = s.createQuery(hql);
            List newsList = q.list();
            recordNumber = newsList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*�������ܣ���ѯ���е�News��¼*/
    public ArrayList<News> QueryAllNewsInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From News";
            Query q = s.createQuery(hql);
            List newsList = q.list();
            return (ArrayList<News>) newsList;
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public News GetNewsByNewsId(int newsId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            News news = (News)s.get(News.class, newsId);
            return news;
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*���������Ϣ*/
    public void AddNews(News news) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(news);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����News��Ϣ*/
    public void UpdateNews(News news) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(news);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��News��Ϣ*/
    public void DeleteNews (int newsId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object news = s.get(News.class, newsId);
            s.delete(news);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

}
