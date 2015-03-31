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
import com.orga.domain.Notify;

public class NotifyDAO {

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

    /*��ѯ֪ͨ��Ϣ*/
    public ArrayList<Notify> QueryNotifyInfo(String notifyTitle,String classNumber, String notifyDate,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Notify notify where 1=1";
            if(CommUtil.isNotNull(notifyTitle)) {
            	hql += " and notify.notifyTitle like '%" + notifyTitle + "%'";
            }
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and notify.notifyClass.classNumber like '%" + classNumber + "%'";
            }
            if(CommUtil.isNotNull(notifyDate)) {
            	hql += " and notify.notifyDate like '%" + notifyDate + "%'";
            }
            hql += " order by notify.notifyDate desc";
            
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List notifyList = q.list();
            return (ArrayList<Notify>) notifyList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String notifyTitle, String classNumber, String notifyDate) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Notify notify where 1=1";
            if(CommUtil.isNotNull(notifyTitle)){
            	hql += " and notify.notifyTitle like '%" + notifyTitle + "%'";
            }
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and notify.notifyClass.classNumber like '%" + classNumber + "%'";
            }
            if(CommUtil.isNotNull(notifyDate)) {
            	hql += " and notify.notifyDate like '%" + notifyDate + "%'";
            }
            Query q = s.createQuery(hql);
            List notifyList = q.list();
            recordNumber = notifyList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
	public ArrayList<Notify> QueryNotifyInfo(String notifyTitle, String classNumber) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			String hql = "From Notify notify where 1=1";
			
			if (CommUtil.isNotNull(notifyTitle)) {
				hql += " and notify.notifyTitle like '%" + notifyTitle + "%'";
			}
			if (CommUtil.isNotNull(classNumber)) {
				hql += " and notify.notifyClass.classNumber like '%" + classNumber + "%'";
			}
			Query q = s.createQuery(hql);
			List notifyList = q.list();
			return (ArrayList<Notify>) notifyList;
		} finally {
			HibernateUtil.closeSession();
		}
	}
	
	public List<Map> QueryStudentNotify(String classNumber) {
		Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String sql = "select notifyId,notifyAuthor,msgType,notifyTitle,notifyContent,notifyDate" +
			" from t_notify where 1=1";
			if(!StringUtils.isEmpty(classNumber)) {
				sql += " and notifyClass like '%" + classNumber + "%'";
			}
			
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("notifyId", Hibernate.INTEGER)
					.addScalar("notifyAuthor", Hibernate.STRING)
					.addScalar("msgType", Hibernate.INTEGER)
					.addScalar("notifyTitle", Hibernate.STRING)
					.addScalar("notifyContent", Hibernate.STRING)
					.addScalar("notifyDate", Hibernate.STRING);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
        } finally {
            HibernateUtil.closeSession();
        } 
	}
    
    /*�������ܣ���ѯ���е�Notify��¼*/
    public ArrayList<Notify> QueryAllNotify() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Notify";
            Query q = s.createQuery(hql);
            List notifyList = q.list();
            return (ArrayList<Notify>) notifyList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public Notify GetNotifyByNotifyId(int notifyId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Notify notify = (Notify)s.get(Notify.class, notifyId);
            return notify;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*���ͼ����Ϣ*/
    public void AddNotify(Notify notify) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(notify);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����Notify��Ϣ*/
    public void UpdateNotify(Notify notify) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(notify);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��Notify��Ϣ*/
    public void DeleteNotify (int notifyId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object notify = s.get(Notify.class, notifyId);
            s.delete(notify);
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
