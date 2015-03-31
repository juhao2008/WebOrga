package com.orga.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.orga.utils.CommUtil;
import com.orga.utils.HibernateUtil;
import com.orga.domain.Student;

public class StudentDAO {
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
     * @param name
     * @param pwd
     * @return
     */
    public boolean CheckStudentUser(String name, String pwd) throws Exception{
    	Session s = null;
        try {
            s = HibernateUtil.getSession();
            Student dbStudent = (Student)s.get(Student.class, name);
            if(dbStudent == null) {
            	errMessage = "帐号不存在!";
            	return false;
            } else if(!pwd.equals(dbStudent.getStudentPassword())) {
            	errMessage = "密码有误!";
            	return false;
            }
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    /*添加图书信息*/
    public void AddStudent(Student student) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(student);
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
     * 查询Student信息（适合分页显示）
     * @param studentNumber
     * @param studentName
     * @param classNumber
     * @param studentBirthday
     * @param currentPage
     * @return
     */
    public ArrayList<Student> QueryStudentInfo(String studentNumber,String studentName,
    		String classNumber,String studentBirthday,int currentPage) throws Exception{ 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Student student where 1=1";
            if(CommUtil.isNotNull(studentNumber)) 
            	hql += " and student.studentNumber like '%" + studentNumber + "%'";
            if(CommUtil.isNotNull(studentName)) 
            	hql += " and student.studentName like '%" + studentName + "%'";
            if(CommUtil.isNotNull(classNumber)) 
            	hql += " and student.studentClass.classNumber like '%" + classNumber + "%'";
            if(CommUtil.isNotNull(studentBirthday)) 
            	hql += " and student.studentBirthday like '%" + studentBirthday + "%'";
            DumpMsg(hql);
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List studentList = q.list();
            return (ArrayList<Student>) studentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * 适合下拉显示
     * @param studentNumber
     * @param studentName
     * @param classNumber
     * @return
     */
    public ArrayList<Student> QueryStudentList(String studentNumber, String studentName, String classNumber) throws Exception{
    	Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Student student where 1=1";
            if(CommUtil.isNotNull(studentNumber)) {
            	hql += " and student.studentNumber like '%" + studentNumber + "%'";
            }
            if(CommUtil.isNotNull(studentName)) {
            	hql += " and student.studentName like '%" + studentName + "%'";
            }
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and student.studentClass.classNumber like '%" + classNumber + "%'";
            }
            Query q = s.createQuery(hql);
            List studentList = q.list();
            return (ArrayList<Student>) studentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * 查询某班 所有人的(学号，姓名，所有科目未完成的作业数，家长)
     * @param classNumber
     * @return
     */
    public List<Map> QueryClassStudents(String classNumber) {
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String hql = "select studentNumber, studentName, (select count(id) from t_studentassignment where student like studentNumber and assignmentStatus = 0) as unfinish, (select parentName from t_parent where student like studentNumber) as parentName from t_student where 1=1";
			if(CommUtil.isNotNull(classNumber)) {
            	hql += " and studentClass like '%" + classNumber + "%'";
            }
			SQLQuery query = s.createSQLQuery(hql)
					.addScalar("studentNumber", Hibernate.STRING)
					.addScalar("studentName", Hibernate.STRING)
					.addScalar("unfinish", Hibernate.INTEGER)
					.addScalar("parentName", Hibernate.STRING);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//            for(int i=0; i<list.size(); i++) {
//            	Map map = (Map)list.get(i); 
//            	System.out.println("== " + map.get("studentNumber") + map.get("studentName") + map.get("unfinish") + map.get("parentName"));
//            }
            return list;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * 查询某班 所有人的 (学号，姓名，某门课程的未完成的作业数)
     * @param classNumber
     * @param courseId
     * @return
     */
	public List<Map> QueryClassCourseStudents(String classNumber, int courseId) {
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String hql = "select studentNumber, studentName, (select count(id) from t_studentassignment where student like studentNumber and (select id from t_courseschedule where id = 2) and assignmentStatus = 0) as unfinish from t_student where 1=1";
			if(CommUtil.isNotNull(classNumber)) {
            	hql += " and studentClass like '%" + classNumber + "%'";
            }
			SQLQuery query = s.createSQLQuery(hql)
					.addScalar("studentNumber", Hibernate.STRING)
					.addScalar("studentName", Hibernate.STRING)
					.addScalar("unfinish", Hibernate.INTEGER);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            return list;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
    public int getClassHeaderCount(String classNumber) throws Exception{
    	Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Student student where 1=1";
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and student.studentClass.classNumber like '%" + classNumber + "%'";
            }
            Query q = s.createQuery(hql);
            List studentList = q.list();
            return studentList.size();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的Student记录*/
    public ArrayList<Student> QueryAllStudentInfo() throws Exception{
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Student";
            Query q = s.createQuery(hql);
            List studentList = q.list();
            return (ArrayList<Student>) studentList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(String studentNumber,String studentName, 
    		String classNumber,String studentBirthday) throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Student student where 1=1";
            if(CommUtil.isNotNull(studentNumber)) 
            	hql += " and student.studentNumber like '%" + studentNumber + "%'";
            if(CommUtil.isNotNull(studentName)) 
            	hql += " and student.studentName like '%" + studentName + "%'";
            if(CommUtil.isNotNull(classNumber)) 
            	hql += " and student.studentClass.classNumber like '%" + classNumber + "%'";
            if(CommUtil.isNotNull(studentBirthday)) 
            	hql += " and student.studentBirthday like '%" + studentBirthday + "%'";
            Query q = s.createQuery(hql);
            List studentList = q.list();
            recordNumber = studentList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public Student GetStudentByNumber(String studentNumber) throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Student student = (Student)s.get(Student.class, studentNumber);
            return student;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新Student信息*/
    public void UpdateStudent(Student student) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(student);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除Student信息*/
    public void DeleteStudent (String studentNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object student = s.get(Student.class, studentNumber);
            s.delete(student);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public void DumpMsg(String msg) {
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [StudentDAO] " + msg);
    }

}
