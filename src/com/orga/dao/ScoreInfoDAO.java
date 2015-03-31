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
import com.orga.domain.ScoreInfo;

public class ScoreInfoDAO {

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

    /*添加成绩信息*/
    public void AddScoreInfo(ScoreInfo scoreInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(scoreInfo);
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
     * 查询ScoreInfo信息，分页显示
     * @param courseScheduleId
     * @param currentPage
     * @return
     */
    public ArrayList<ScoreInfo> QueryScoreInfo(int courseScheduleId, String studentNumber, int currentPage) throws Exception{ 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ScoreInfo scoreInfo where 1=1";
            if(CommUtil.isNotNull(studentNumber)) {
            	hql += " and scoreInfo.student.studentNumber like '%" + studentNumber + "%'";
            }
            if(courseScheduleId > 0) {
            	hql += " and scoreInfo.courseSchedule.id='" + courseScheduleId + "'";
            }
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List scoreInfoList = q.list();
            return (ArrayList<ScoreInfo>) scoreInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * 查询ScoreInfo信息，不分页
     * @param courseScheduleId
     * @return
     */
    public ArrayList<ScoreInfo> QueryScoreInfo(int courseScheduleId, String studentNumber) throws Exception{
    	Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ScoreInfo scoreInfo where 1=1";
            if(CommUtil.isNotNull(studentNumber)) {
            	hql += " and scoreInfo.student.studentNumber like '%" + studentNumber + "%'";
            }
            if(courseScheduleId > 0) {
            	hql += " and scoreInfo.courseSchedule.id='" + courseScheduleId + "'";
            }
            Query q = s.createQuery(hql);
            List scoreInfoList = q.list();
            return (ArrayList<ScoreInfo>) scoreInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(int courseScheduleId, String studentNumber) throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From ScoreInfo scoreInfo where 1=1";
            if(CommUtil.isNotNull(studentNumber)) {
            	hql += " and scoreInfo.student.studentNumber like '%" + studentNumber + "%'";
            }
            if(courseScheduleId > 0) {
            	hql += " and scoreInfo.courseSchedule.id='" + courseScheduleId + "'";
            }
            Query q = s.createQuery(hql);
            List scoreInfoList = q.list();
            recordNumber = scoreInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * 查询某班所有学生的考试分数列表
     * @param classNumber
     * @return
     * @throws Exception
     */
    public ArrayList<ScoreInfo> QueryClassStudentScore(String classNumber) throws Exception{
    	Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ScoreInfo scoreInfo where 1=1";
            
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and scoreInfo.student.studentClass.classNumber like '%" + classNumber + "%'";
            }
            Query q = s.createQuery(hql);
            List scoreInfoList = q.list();
            return (ArrayList<ScoreInfo>) scoreInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public List<Map> QueryStudentScoreList(String studentNumber) throws Exception {
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String sql = "select scoreId, scoreValue,studentEvaluate,count(student) as studentHeader," +
					" (select avg(scoreValue) from t_scoreinfo ta where ta.testInfo=tb.testInfo) as avgScore," +
					" (select courseName from t_courseinfo where courseNumber like (select courseInfo from t_courseschedule where id=(select courseSchedule from t_testinfo where testId=testInfo))) as courseName," +
					" (select testTitle from t_testinfo where testId=testInfo) as testTitle," +
					" (select testDate from t_testinfo where testId=testInfo) as testDate," +
					" (select testPropose from t_testinfo where testId=testInfo) as testPropose," +
					" (select testEvaluate from t_testinfo where testId=testInfo) as testEvaluate," +
					" (select recordScore from t_testinfo where testId=testInfo) as recordScore" +
					" from t_scoreinfo tb where 1=1";
			if(!StringUtils.isEmpty(studentNumber)) {
				sql += " and student like '%" + studentNumber + "%'";
			}
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("scoreId", Hibernate.INTEGER)
					.addScalar("scoreValue", Hibernate.FLOAT)
					.addScalar("studentEvaluate", Hibernate.STRING)
					.addScalar("studentHeader", Hibernate.INTEGER)
					.addScalar("avgScore", Hibernate.FLOAT)
					.addScalar("courseName", Hibernate.STRING)
					.addScalar("testTitle", Hibernate.STRING)
					.addScalar("testDate", Hibernate.STRING)
					.addScalar("testPropose", Hibernate.STRING)
					.addScalar("testEvaluate", Hibernate.STRING)
					.addScalar("recordScore", Hibernate.INTEGER);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
        } finally {
            HibernateUtil.closeSession();
        } 
    }
    
    public List<Map> QueryScoreInfoDetail(int testId) throws Exception {
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String sql = "select studentEvaluate, count(student) as studentHeader," + //avg(scoreValue) as avgValue,
					" (select testPropose from t_testinfo where testId=testInfo) as testPropose," +
					" (select testEvaluate from t_testinfo where testId=testInfo) as testEvaluate," +
					" (select recordScore from t_testinfo where testId=testInfo) as recordScore" +
					" from t_scoreinfo where 1=1";
			if(testId > 0) {
				sql += " and testInfo=" + testId;
			}
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("studentEvaluate", Hibernate.STRING)
					.addScalar("studentHeader", Hibernate.INTEGER)
					.addScalar("testPropose", Hibernate.STRING)
					.addScalar("testEvaluate", Hibernate.STRING)
					.addScalar("recordScore", Hibernate.INTEGER);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*根据主键获取对象*/
    public ScoreInfo GetScoreInfoByScoreId(int scoreId) throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            ScoreInfo scoreInfo = (ScoreInfo)s.get(ScoreInfo.class, scoreId);
            return scoreInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新ScoreInfo信息*/
    public void UpdateScoreInfo(ScoreInfo scoreInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(scoreInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除ScoreInfo信息*/
    public void DeleteScoreInfo (int scoreId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object scoreInfo = s.get(ScoreInfo.class, scoreId);
            s.delete(scoreInfo);
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
