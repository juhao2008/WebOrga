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
import com.orga.domain.Assignment;
import com.orga.domain.CourseSchedule;

public class AssignmentDAO {

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

    /*��ӳɼ���Ϣ*/
    public void AddAssignment(Assignment assignment) throws Exception {
//    	dumpMsg("AddAssignment::" + assignment.toString());
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(assignment);
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
     * �ʺϷ�ҳչʾ
     * @param assignmentName
     * @param courseScheduleNumber
     * @param classNumber
     * @param currentPage
     * @return
     */
    public ArrayList<Assignment> QueryAssignments(String assignmentName, int courseScheduleNumber, int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Assignment assignment where 1=1";
            if(CommUtil.isNotNull(assignmentName)) {
            	hql += " and assignment.assignmentName = '" + assignmentName + "'";
            }
            if(courseScheduleNumber > 0) {
            	hql += " and assignment.courseSchedule.id='" + courseScheduleNumber + "'";
            }
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List assignmentList = q.list();
            
            return (ArrayList<Assignment>) assignmentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * �ʺ������б�
     * @param assignmentName
     * @param courseScheduleId
     * @param classNumber
     * @return
     */
    public ArrayList<Assignment> QueryAssignments(String assignmentName, int courseScheduleId) {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Assignment assignment where 1=1";
            if(CommUtil.isNotNull(assignmentName)) {
            	hql += " and assignment.assignmentName = '" + assignmentName + "'";
            }
            if(courseScheduleId > 0) {
            	hql += " and assignment.courseSchedule.id=" + courseScheduleId;
            }
            Query q = s.createQuery(hql);
            List assignmentList = q.list();
            return (ArrayList<Assignment>) assignmentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * �����ܵ�ҳ���ͼ�¼��
     * @param assignmentName
     * @param courseScheduleId
     * @param classNumber
     */
    public void CalculateTotalPageAndRecordNumber(String assignmentName, int courseScheduleId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Assignment assignment where 1=1";
            if(CommUtil.isNotNull(assignmentName)) {
            	hql += " and assignment.assignmentName = '" + assignmentName + "'";
            }
            if(courseScheduleId > 0) {
            	hql += " and assignment.courseSchedule.id=" + courseScheduleId;
            }
//            hql += " and assignment.assignmentDate > '2015-01-15'";
            Query q = s.createQuery(hql);
            List assignmentList = q.list();
            recordNumber = assignmentList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * ĳѧ�����ڰ༶�����пγ̵���ҵ(�ۺ�)
     * ���ڣ�ѧ��������ҵ�б�
     * @param classNumber
     * @return
     * @throws Exception
     */
    public List<Map> QueryClassAssignments(String classNumber, String studentNumber) throws Exception{
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String sql = "select assignmentId, assignmentName, assignmentDate,assignmentContent,assignmentAttachment," +
					"(select courseName from t_courseinfo where courseNumber = (select courseInfo from t_courseschedule where id=courseSchedule)) as courseName," +
					"(select teacherName from t_teacher where teacherNumber = (select teacherInfo from t_courseschedule where id=courseSchedule)) as teacherName," +
					"(select courseIconIndex from t_courseinfo where courseNumber = (select courseInfo from t_courseschedule where id=courseSchedule)) as courseIconIndex," +
					"(select statusId from t_studentassignment b where b.assignment=a.assignmentId and student = '"+ studentNumber + "') as statusId," +
					"(select assignmentStatus from t_studentassignment b where b.assignment=a.assignmentId and student = '"+ studentNumber + "') as assignmentStatus" +
					" from t_assignment a where 1=1";
			if(!StringUtils.isEmpty(classNumber)) {
				sql += " and courseSchedule in (select id from t_courseschedule where classInfo = '" + classNumber + "')";
			}
			dumpMsg(sql);
			long before = System.currentTimeMillis();
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("assignmentId", Hibernate.INTEGER)
					.addScalar("assignmentName", Hibernate.STRING)
					.addScalar("teacherName", Hibernate.STRING)
					.addScalar("assignmentDate", Hibernate.STRING)
					.addScalar("assignmentContent", Hibernate.STRING)
					.addScalar("assignmentAttachment", Hibernate.STRING)
					.addScalar("courseName", Hibernate.STRING)
					.addScalar("courseIconIndex", Hibernate.INTEGER)
					.addScalar("statusId", Hibernate.INTEGER)
					.addScalar("assignmentStatus", Hibernate.INTEGER);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			dumpMsg("1 sql spent time::>" + (System.currentTimeMillis() - before));
			return list;
        } finally {
            HibernateUtil.closeSession();
        } 
    }
    
    /**
     * ĳһ�ſγ̵���ҵ�б���ࣩ
     * ���ڣ��ʺϰ����ν����б�
     * @param courseScheduleId
     * @return
     */
    public List<Map> QueryCourseAssignments(int courseScheduleId) {
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String sql ="select assignmentId, assignmentName, assignmentDate from t_assignment where 1=1";
			if(courseScheduleId > 0) {
				sql += " and courseSchedule="+courseScheduleId;
			}
			long before = System.currentTimeMillis();
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("assignmentId", Hibernate.INTEGER)
					.addScalar("assignmentName", Hibernate.STRING)
					.addScalar("assignmentDate", Hibernate.STRING);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			dumpMsg("2 sql spent time::>" + (System.currentTimeMillis() - before));
			return list;
        } finally {
            HibernateUtil.closeSession();
        } 
    }
    
    
    /*����������ȡ����*/
    public Assignment GetAssignmentById(int assignmentId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Assignment assignment = (Assignment)s.get(Assignment.class, assignmentId);
            return assignment;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * ��ȡ���пγ̵���ҵ�б�
     * @param list
     * @return
     */
    public ArrayList<Assignment> GetAllAssignments(ArrayList<CourseSchedule> list) {
    	ArrayList<Assignment> resultList = new ArrayList<Assignment>();
    	for(CourseSchedule schedule : list) {
    		int courseScheduleId = schedule.getId();
    		ArrayList<Assignment> subList = QueryAssignments(null, courseScheduleId);
    		resultList.addAll(subList);
    	}
    	return resultList;
    }

    /*����Assignment��Ϣ*/
    public void UpdateAssignment(Assignment assignment) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(assignment);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��Assignment��Ϣ*/
    public void DeleteAssignment (int assignmentId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object assignment = s.get(Assignment.class, assignmentId);
            s.delete(assignment);
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
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [AssignmentDAO] " + msg);
    }

}
