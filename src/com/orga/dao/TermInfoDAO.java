package com.orga.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mysql.jdbc.Statement;
import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;
import com.orga.domain.TermInfo;

public class TermInfoDAO {

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

    /*���ѧ����Ϣ*/
    public void AddTermInfo(TermInfo termInfo) throws Exception {
    	dumpMsg("AddTermInfo ::" + termInfo);
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(termInfo);
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
     * ��ѯѧ����Ϣ���ʺϷ�ҳ��ʾ
     * @param termNumber
     * @param termName
     * @param currentPage
     * @return
     */
    public ArrayList<TermInfo> QueryTermInfo(String termNumber,String termName,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From TermInfo termInfo where 1=1";
            if(CommUtil.isNotNull(termNumber)) {
            	hql += " and termInfo.termNumber like '%" + termNumber + "%'";
            }
            if(CommUtil.isNotNull(termName)) {
            	hql += " and termInfo.termName like '%" + termName + "%'";
            }
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List gradeInfoList = q.list();
            return (ArrayList<TermInfo>) gradeInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�������ܣ���ѯ���е�ѧ�ڼ�¼*/
    public ArrayList<TermInfo> QueryAllTermInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From TermInfo";
            Query q = s.createQuery(hql);
            List list = q.list();
            return (ArrayList<TermInfo>) list;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String termNumber,String termName) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From TermInfo termInfo where 1=1";
            if(CommUtil.isNotNull(termNumber)) {
            	hql += " and termInfo.termNumber like '%" + termNumber + "%'";
            }
            if(CommUtil.isNotNull(termName)) {
            	hql += " and termInfo.termName like '%" + termName + "%'";
            }
            Query q = s.createQuery(hql);
            List list = q.list();
            recordNumber = list.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public TermInfo GetTermInfoByTermNumber(String termNumber) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            TermInfo termInfo = (TermInfo)s.get(TermInfo.class, termNumber);
            return termInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����ѧ����Ϣ*/
    public void UpdateTermInfo(TermInfo termInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(termInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��ѧ����Ϣ*/
    public void DeleteTermInfo (String termNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object termInfo = s.get(TermInfo.class, termNumber);
            s.delete(termInfo);
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
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [TermInfo] " + msg);
    }

}
