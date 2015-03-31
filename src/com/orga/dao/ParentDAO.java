package com.orga.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.orga.domain.Parent;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;

public class ParentDAO {
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
    
    /**
     * ��Ӽҳ���Ϣ
     * @param parent
     * @throws Exception
     */
    public void AddStudentParent(Parent parent) throws Exception {
    	Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(parent);
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
     * ��ѯĳ�༶ѧ���ҳ���Ϣ�б�,��ҳ��ʾ
     * @param studentNumber
     * @param parentName
     * @param currentPage
     * @return
     */
    public ArrayList<Parent> QueryStudentParent(String classNumber, int currentPage) {
    	Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Parent parent where 1=1";
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and parent.student.studentClass.classNumber like '%" + classNumber + "%'";
            }
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List parentList = q.list();
            return (ArrayList<Parent>) parentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
	/* �����ܵ�ҳ���ͼ�¼�� */
	public void CalculateTotalPageAndRecordNumber(String classNumber) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
            String hql = "From Parent parent where 1=1";
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and parent.student.studentClass.classNumber like '%" + classNumber + "%'";
            }
			Query q = s.createQuery(hql);
			List courseScheduleList = q.list();
			recordNumber = courseScheduleList.size();
			int mod = recordNumber % this.PAGE_SIZE;
			totalPage = recordNumber / this.PAGE_SIZE;
			if (mod != 0)
				totalPage++;
		} finally {
			HibernateUtil.closeSession();
		}
	}
    
    
    /**
     * ֻ����ĳ��ѧ���ҳ���Ϣ
     * @param studentNumber
     * @param parentName
     * @return
     */
    public ArrayList<Parent> QueryStudentParent(String studentNumber, String parentName) {
    	Session s = null; 
    	try {
            s = HibernateUtil.getSession();
            String hql = "From Parent parent where 1=1";
            if(CommUtil.isNotNull(studentNumber)) {
            	hql += " and parent.student.studentNumber like '%" + studentNumber + "%'";
            }
            if(CommUtil.isNotNull(parentName)) {
            	hql += " and parent.parentName like '%" + parentName + "%'";
            }
            Query q = s.createQuery(hql);
            List parentList = q.list();
            return (ArrayList<Parent>) parentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * ����Parent
     * @param parentNumber
     * @return
     */
    public Parent QueryStudentParent(String parentNumber) {
    	Session s = null;
        try {
            s = HibernateUtil.getSession();
            Parent parent = (Parent)s.get(Parent.class, parentNumber);
            return parent;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * UpdateParent
     * @param parent
     * @throws Exception
     */
    public void UpdateParent(Parent parent) throws Exception {
    	Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(parent);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public void DeleteParent(String parentNumber) {
    	Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object courseSchedule = s.get(Parent.class, parentNumber);
            s.delete(courseSchedule);
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
