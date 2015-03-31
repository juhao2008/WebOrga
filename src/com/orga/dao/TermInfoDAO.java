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

    /*添加学期信息*/
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
     * 查询学期信息，适合分页显示
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
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List gradeInfoList = q.list();
            return (ArrayList<TermInfo>) gradeInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的学期记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
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

    /*更新学期信息*/
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

    /*删除学期信息*/
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
