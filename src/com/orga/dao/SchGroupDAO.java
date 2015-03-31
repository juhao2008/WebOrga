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
import com.orga.domain.School;
import com.orga.domain.SchGroup;

public class SchGroupDAO {

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

    /*添加机构信息*/
    public void AddSchGroup(SchGroup group) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(group);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询SchGroup信息*/
    public ArrayList<SchGroup> QuerySchGroupInfo(String strNumber,String strName,School school,String strBirthDate,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From SchGroup schGroup where 1=1";
            if(!strNumber.equals("")) 
            	hql = hql + " and schGroup.schGroupNumber like '%" + strNumber + "%'";
            if(!strName.equals("")) 
            	hql = hql + " and schGroup.schGroupName like '%" + strName + "%'";
            if(null != school && !school.getSchoolNumber().equals("")) 
            	hql += " and schGroup.schGroupSchool.schoolNumber='%" + school.getSchoolNumber() + "%'";
            if(!strBirthDate.equals("")) 
            	hql = hql + " and schGroup.schGroupBirthDate like '%" + strBirthDate + "%'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            return (ArrayList<SchGroup>) q.list();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的SchGroupInfo记录*/
    public ArrayList<SchGroup> QueryAllSchGroupInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From SchGroup";
            Query q = s.createQuery(hql);
            List groupList = q.list();
            return (ArrayList<SchGroup>) groupList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(String strNumber,String strName,School school,String strBirthDate) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From SchGroup schGroup where 1=1";
            if(!strNumber.equals("")) hql = hql + " and schGroup.schGroupNumber like '%" + strNumber + "%'";
            if(!strName.equals("")) hql = hql + " and schGroup.schGroupName like '%" + strName + "%'";
            if(null != school && !school.getSchoolNumber().equals("")) hql += " and schGroup.schGroupSchool.schoolNumber='" + school.getSchoolNumber() + "'";
            if(!strBirthDate.equals("")) hql = hql + " and schGroup.schGroupBirthDate like '%" + strBirthDate + "%'";
            Query q = s.createQuery(hql);
            List schGroupList = q.list();
            recordNumber = schGroupList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public SchGroup GetSchGroupByNumber(String strNumber) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            SchGroup group = (SchGroup)s.get(SchGroup.class, strNumber);
            return group;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新SchGroup信息*/
    public void UpdateSchGroup(SchGroup group) throws Exception {
    	dumpMsg(group.toString());
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(group);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除SchGroup信息*/
    public void DeleteSchGroup (String strGroup) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object object = s.get(SchGroup.class, strGroup);
            s.delete(object);
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
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [SchGroupDAO] " + msg);
    }

}
