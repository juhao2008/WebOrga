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

    /*��ӻ�����Ϣ*/
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

    /*��ѯSchGroup��Ϣ*/
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
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            return (ArrayList<SchGroup>) q.list();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�������ܣ���ѯ���е�SchGroupInfo��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
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

    /*����SchGroup��Ϣ*/
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

    /*ɾ��SchGroup��Ϣ*/
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
