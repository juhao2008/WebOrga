package com.orga.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;
import com.orga.domain.CourseSchedule;
import com.orga.domain.TestInfo;

public class TestInfoDAO {

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
    public void AddTestInfo(TestInfo testInfo) throws Exception {
    	dumpMsg("AddTestInfo ::" + testInfo);
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(testInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * ��ѯTestInfo��Ϣ (��ҳ��ʾ)
     * @param testTitle
     * @param courseScheduleId
     * @param currentPage
     * @return
     */
    public ArrayList<TestInfo> QueryTestInfo(String testTitle, int courseScheduleId,int currentPage)  throws Exception{ 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From TestInfo testInfo where 1=1";
            if(CommUtil.isNotNull(testTitle)) 
            	hql += " and testInfo.testTitle like '%" + testTitle + "%'";
            if(courseScheduleId > 0) 
            	hql += " and testInfo.courseSchedule.id= '" + courseScheduleId + "'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List testInfoList = q.list();
            return (ArrayList<TestInfo>) testInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * ��ѯTestInfo��Ϣ��¼(����ҳ���ʺ�����)
     * @param testTitle
     * @param courseScheduleId
     * @return
     */
    public ArrayList<TestInfo> QueryTestInfo(String testTitle, int courseScheduleId) throws Exception{
    	Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From TestInfo testInfo where 1=1";
            if(CommUtil.isNotNull(testTitle)) 
            	hql += " and testInfo.testTitle like '%" + testTitle + "%'";
            if(courseScheduleId > 0)
            	hql += " and testInfo.courseSchedule.id= '" + courseScheduleId + "'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            List testInfoList = q.list();
            return (ArrayList<TestInfo>) testInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    

    /*�������ܣ���ѯ���е�TestInfo��¼*/
    public ArrayList<TestInfo> QueryAllTestInfo()  throws Exception{
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From TestInfo";
            Query q = s.createQuery(hql);
            List testInfoList = q.list();
            return (ArrayList<TestInfo>) testInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String testTitle, int courseScheduleId)  throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From TestInfo testInfo where 1=1";
            if(CommUtil.isNotNull(testTitle))
            	hql += " and testInfo.testTitle like '%" + testTitle + "%'";
            if(courseScheduleId > 0) 
            	hql += " and testInfo.courseSchedule.id='" + courseScheduleId + "'";
            Query q = s.createQuery(hql);
            List testInfoList = q.list();
            recordNumber = testInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public TestInfo GetTestInfoById(int testId)  throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            TestInfo testInfo = (TestInfo)s.get(TestInfo.class, testId);
            return testInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * ��ȡ���пγ̵Ŀ�����Ϣ
     * @param list
     * @return
     */
    public ArrayList<TestInfo> GetAllTestInfo(ArrayList<CourseSchedule> list)  throws Exception{
    	ArrayList<TestInfo> resultList = new ArrayList<TestInfo>();
    	for(CourseSchedule schedule : list) {
    		int courseScheduleId = schedule.getId();
    		ArrayList<TestInfo> sub = QueryTestInfo(null, courseScheduleId);
    		resultList.addAll(sub);
    	}
    	return resultList;
    }
    
    /**
     * ��ѯĳ�γ̵Ŀ����б���ࣩ
     * @param courseScheduleId
     * @return
     */
    public List<Map> QueryCourseTestList(int courseScheduleId) {
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String sql = "select testId, testTitle, testDate from t_testinfo where 1=1";
			if(courseScheduleId > 0) {
				sql += " and courseSchedule=" + courseScheduleId;
			}
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("testId", Hibernate.INTEGER)
					.addScalar("testTitle", Hibernate.STRING)
					.addScalar("testDate", Hibernate.STRING);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
        } finally {
            HibernateUtil.closeSession();
        } 
    }

    /*����TestInfo��Ϣ*/
    public void UpdateTestInfo(TestInfo testInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(testInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��TestInfo��Ϣ*/
    public void DeleteTestInfo (int testId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object testInfo = s.get(TestInfo.class, testId);
            s.delete(testInfo);
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
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [TestInfoDAO] " + msg);
    }

}
