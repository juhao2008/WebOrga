package com.orga.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;
import com.orga.domain.CourseInfo;
import com.orga.domain.CourseSchedule;

public class CourseInfoDAO {

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

    /*��ӿγ���Ϣ*/
    public void AddCourseInfo(CourseInfo courseInfo) throws Exception {
    	dumpMsg("AddCourseInfo ::" + courseInfo);
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(courseInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*��ѯCourseInfo��Ϣ*/
    public ArrayList<CourseInfo> QueryCourseInfoInfo(String courseNumber, String courseName,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseInfo courseInfo where 1=1";
            if(CommUtil.isNotNull(courseNumber)) 
            	hql += " and courseInfo.courseNumber like '%" + courseNumber + "%'";
            if(CommUtil.isNotNull(courseName)) 
            	hql += " and courseInfo.courseName like '%" + courseName + "%'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List courseInfoList = q.list();
            return (ArrayList<CourseInfo>) courseInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String courseNumber,String courseName) {
        Session s = null;
        try {
        	String hql = "From CourseInfo courseInfo where 1=1";
            if(CommUtil.isNotNull(courseNumber)) 
            	hql += " and courseInfo.courseNumber like '%" + courseNumber + "%'";
            if(CommUtil.isNotNull(courseName)) 
            	hql += " and courseInfo.courseName like '%" + courseName + "%'";
            Query q = s.createQuery(hql);
            List courseInfoList = q.list();
            recordNumber = courseInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*�������ܣ���ѯ���е�CourseInfo��¼*/
    public ArrayList<CourseInfo> QueryAllCourseInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseInfo";
            Query q = s.createQuery(hql);
            List courseInfoList = q.list();
            return (ArrayList<CourseInfo>) courseInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public CourseInfo GetCourseInfoByCourseNumber(String courseNumber) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            CourseInfo courseInfo = (CourseInfo)s.get(CourseInfo.class, courseNumber);
            return courseInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public void QueryAllCourseSchedule(String classNumber) {
//		List<CourseSchedule> list = new Arrary();
    }

    /*����CourseInfo��Ϣ*/
    public void UpdateCourseInfo(CourseInfo courseInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(courseInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��CourseInfo��Ϣ*/
    public void DeleteCourseInfo (String courseNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object courseInfo = s.get(CourseInfo.class, courseNumber);
            s.delete(courseInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    private void dumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [CourseInfoDAO] " + msg);
    }

}
