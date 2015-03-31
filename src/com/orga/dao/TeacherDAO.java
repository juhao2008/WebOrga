package com.orga.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.orga.utils.HibernateUtil;
import com.orga.domain.Teacher;

public class TeacherDAO {
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }

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
    
    /**
     * Check login user
     * @param teacherNumber
     * @param pwd
     * @return
     */
    public boolean CheckTeacherUser(String teacherNumber, String pwd) {
    	Session s = null;
        try {
            s = HibernateUtil.getSession();
            Teacher dbTeacher = (Teacher)s.get(Teacher.class, teacherNumber);
            if(dbTeacher == null) {
            	errMessage = "帐号不存在!";
            	return false;
            } else if(!pwd.equals(dbTeacher.getTeacherPassword())){
            	errMessage = "密码有误!";
            	return false;
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    /*添加图书信息*/
    public void AddTeacher(Teacher teacher) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(teacher);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询Teacher列表信息 适用于分页显示*/
    public ArrayList<Teacher> QueryTeacherInfo(String teacherNumber,String teacherName,String schoolNumber,String teacherArriveDate,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Teacher teacher where 1=1";
            if(!teacherNumber.equals("")) 
            	hql = hql + " and teacher.teacherNumber like '%" + teacherNumber + "%'";
            if(!teacherName.equals("")) 
            	hql = hql + " and teacher.teacherName like '%" + teacherName + "%'";
            if(schoolNumber != null && schoolNumber.trim().length() > 0)
            	hql = hql + " and teacher.teacherSchool.schoolNumber like '%" + schoolNumber + "%'";
            if(!teacherArriveDate.equals("")) 
            	hql = hql + " and teacher.teacherArriveDate like '%" + teacherArriveDate + "%'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List teacherList = q.list();
            return (ArrayList<Teacher>) teacherList;
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*函数功能：查询所有的Teacher记录，适用于下拉列表*/
    public ArrayList<Teacher> QueryTeacherInfo(String teacherName,String schoolNumber) {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Teacher teacher where 1=1";
            if(teacherName != null && teacherName.trim().length() > 0) {
            	hql += " and teacher.teacherName like '%" + teacherName + "%'";
            }
            if(schoolNumber != null && schoolNumber.trim().length() > 0) {
            	hql += " and teacher.teacherSchool.schoolNumber like '%" + schoolNumber + "%'";
            }
            Query q = s.createQuery(hql);
            List teacherList = q.list();
            return (ArrayList<Teacher>) teacherList;
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(String teacherNumber,String teacherName,String schoolNumber,String teacherArriveDate) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Teacher teacher where 1=1";
            if(!teacherNumber.equals("")) 
            	hql = hql + " and teacher.teacherNumber like '%" + teacherNumber + "%'";
            if(!teacherName.equals("")) 
            	hql = hql + " and teacher.teacherName like '%" + teacherName + "%'";
            if(schoolNumber != null && schoolNumber.trim().length() > 0) 
            	hql = hql + " and teacher.teacherSchool.schoolNumber like '%" + schoolNumber + "%'";
            if(!teacherArriveDate.equals("")) 
            	hql = hql + " and teacher.teacherArriveDate like '%" + teacherArriveDate + "%'";
            Query q = s.createQuery(hql);
            List teacherList = q.list();
            recordNumber = teacherList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*根据主键获取对象*/
    public Teacher GetTeacherByTeacherNumber(String teacherNumber) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Teacher teacher = (Teacher)s.get(Teacher.class, teacherNumber);
            return teacher;
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新Teacher信息*/
    public void UpdateTeacher(Teacher teacher) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(teacher);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除Teacher信息*/
    public void DeleteTeacher (String teacherNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object teacher = s.get(Teacher.class, teacherNumber);
            s.delete(teacher);
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
