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
import com.orga.domain.GradeInfo;

public class GradeInfoDAO {

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

    /*����꼶��Ϣ*/
    public void AddGradeInfo(GradeInfo gradeInfo) throws Exception {
    	dumpMsg("AddGradeInfo ::" + gradeInfo);
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(gradeInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*��ѯ�꼶��Ϣ*/
    public ArrayList<GradeInfo> QueryGradeInfo(String gradeNumber,String gradeName,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From GradeInfo gradeInfo where 1=1";
            if(!gradeNumber.equals("")) 
            	hql = hql + " and gradeInfo.gradeNumber like '%" + gradeNumber + "%'";
            if(!gradeName.equals("")) 
            	hql = hql + " and gradeInfo.gradeName like '%" + gradeName + "%'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List gradeInfoList = q.list();
            return (ArrayList<GradeInfo>) gradeInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�������ܣ���ѯ���е��꼶��¼*/
    public ArrayList<GradeInfo> QueryAllGradeInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From GradeInfo";
            Query q = s.createQuery(hql);
            List list = q.list();
            return (ArrayList<GradeInfo>) list;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String gradeNumber,String gradeName) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From GradeInfo gradeInfo where 1=1";
            if(!gradeNumber.equals("")) hql = hql + " and gradeInfo.gradeNumber like '%" + gradeNumber + "%'";
            if(!gradeName.equals("")) hql = hql + " and gradeInfo.gradeName like '%" + gradeName + "%'";
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
    public GradeInfo GetGradeInfoByGradeNumber(String gradeNumber) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            GradeInfo gradeInfo = (GradeInfo)s.get(GradeInfo.class, gradeNumber);
            return gradeInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����꼶��Ϣ*/
    public void UpdateGradeInfo(GradeInfo gradeInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(gradeInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ���꼶��Ϣ*/
    public void DeleteGradeInfo (String gradeNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object gradeInfo = s.get(GradeInfo.class, gradeNumber);
            s.delete(gradeInfo);
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
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [GradeInfo] " + msg);
    }

}
