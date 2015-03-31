package com.orga.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;
import com.orga.domain.School;

public class SchoolDAO {

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

    /*添加学校信息*/
    public void AddSchool(School school) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(school);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询学校信息*/
    public ArrayList<School> QuerySchoolInfo(int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From School school where 1=1";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List schoolList = q.list();
            return (ArrayList<School>) schoolList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的School记录*/
    public ArrayList<School> QueryAllSchoolInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From School";
            Query q = s.createQuery(hql);
            List schoolList = q.list();
            return (ArrayList<School>) schoolList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber() {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From School school where 1=1";
            Query q = s.createQuery(hql);
            List schoolList = q.list();
            recordNumber = schoolList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public School GetSchoolByNumber(String schoolNumber) {
    	System.out.println("[juhao] GetSchoolByNumber, schoolNumber" + schoolNumber);
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            School scholl = (School)s.get(School.class, schoolNumber);
            return scholl;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新学校信息*/
    public void UpdateSchool(School school) throws Exception {
    	dumpMsg("UpdateSchool  :" + school.toString());
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(school);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除学校信息*/
    public void DeleteSchool (String schoolNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object school = s.get(School.class, schoolNumber);
            s.delete(school);
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
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [SchoolDAO] " + msg);
    }

}
