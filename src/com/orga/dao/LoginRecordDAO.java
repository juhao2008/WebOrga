package com.orga.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.orga.domain.LoginRecord;
import com.orga.utils.HibernateUtil;

public class LoginRecordDAO {
	
	public void AddLoginRecord(LoginRecord record) {
		Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(record);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
	}

    public ArrayList<LoginRecord> QueryAllRecords() { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Loginrecord";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            List recordList = q.list();
            return (ArrayList<LoginRecord>) recordList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public LoginRecord GetRecordByName(String name) {
    	Session s = null;
        try {
            s = HibernateUtil.getSession();
            LoginRecord record = (LoginRecord)s.get(LoginRecord.class, name);
            return record;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public void UpdateLoginRecord(LoginRecord record) {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(record);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void DeleteLoginRecord (String name) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object record = s.get(LoginRecord.class, name);
            s.delete(record);
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
