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

    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加课程信息*/
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
     * 查询TestInfo信息 (分页显示)
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
            /*计算当前显示页码的开始记录*/
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
     * 查询TestInfo信息记录(不分页，适合下拉)
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
            /*计算当前显示页码的开始记录*/
            List testInfoList = q.list();
            return (ArrayList<TestInfo>) testInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    

    /*函数功能：查询所有的TestInfo记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
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
     * 获取所有课程的考试信息
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
     * 查询某课程的考试列表（简洁）
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

    /*更新TestInfo信息*/
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

    /*删除TestInfo信息*/
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
