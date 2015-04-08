package com.orga.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;
import com.orga.domain.StudentAssignment;

public class StudentAssignmentDAO {

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

    public int AddStudentAssignment(StudentAssignment stuAssignment) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(stuAssignment);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return stuAssignment.getStatusId();
    }

    /**
     * �ʺϷ�ҳչʾ
     * @param assignmentName
     * @param courseScheduleNumber
     * @param classNumber
     * @param currentPage
     * @return
     */
    public ArrayList<StudentAssignment> QueryStudentAssignments(String studentNumber, int assignmentId, int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From StudentAssignment studentAssignment where 1=1";
            if(CommUtil.isNotNull(studentNumber)) {
            	hql += " and studentAssignment.student='" + studentNumber + "'";
            }
            if(assignmentId > 0) {
            	hql += " and studentAssignment.assignment.assignmentId='" + assignmentId + "'";
            }
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List stuAssignmentList = q.list();
            
            return (ArrayList<StudentAssignment>) stuAssignmentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * �ʺ������б�
     * @param assignmentName
     * @param courseScheduleNumber
     * @param classNumber
     * @return
     */
    public ArrayList<StudentAssignment> QueryStudentAssignments(String studentNumber, int assignmentId) {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From StudentAssignment studentAssignment where 1=1";
            if(CommUtil.isNotNull(studentNumber)) {
            	hql += " and studentAssignment.student= '" + studentNumber + "'";
            }
            if(assignmentId > 0) {
            	hql += " and studentAssignment.assignment.assignmentId='" + assignmentId + "'";
            }
            Query q = s.createQuery(hql);
            List assignmentList = q.list();
            return (ArrayList<StudentAssignment>) assignmentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * �����ܵ�ҳ���ͼ�¼��
     * @param assignmentName
     * @param courseScheduleNumber
     * @param classNumber
     */
    public void CalculateTotalPageAndRecordNumber(String studentNumber, int assignmentId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From StudentAssignment studentAssignment where 1=1";
            if(CommUtil.isNotNull(studentNumber)) {
            	hql += " and studentAssignment.student='" + studentNumber + "'";
            }
            if(assignmentId > 0) {
            	hql += " and studentAssignment.assignment.assignmentId='" + assignmentId + "'";
            }
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
    
    public List<Map> QueryAssignmentStatus(String studentNumber, int assignId) throws Exception{
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String sql = "select statusId,assignmentStatus,finishDate,signDate,signUrl from t_studentassignment where 1=1";
			if(CommUtil.isNotNull(studentNumber)) {
				sql += " and student = '" + studentNumber + "'";
			}
			if(assignId > 0) {
				sql += " and assignment=" + assignId;
			}
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("statusId", Hibernate.INTEGER)
					.addScalar("assignmentStatus", Hibernate.INTEGER)
					.addScalar("finishDate", Hibernate.STRING)
					.addScalar("signDate", Hibernate.STRING)
					.addScalar("signUrl", Hibernate.STRING);
			System.out.println("QueryAssignmentStatus sql: " + sql);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
        } finally {
            HibernateUtil.closeSession();
        } 
    }
    

    /*����������ȡ����*/
    public StudentAssignment GetStudentAssignmentById(int id) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            StudentAssignment assignment = (StudentAssignment)s.get(StudentAssignment.class, id);
            return assignment;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    

    /*����StudentAssignment��Ϣ*/
    public void UpdateStudentAssignment(StudentAssignment stuAssignment) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(stuAssignment);
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
    public void DeleteStudentAssignment (int id) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object assignment = s.get(StudentAssignment.class, id);
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
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [StudentAssignmentDAO] " + msg);
    }

}
