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
            	errMessage = "�ʺŲ�����!";
            	return false;
            } else if(!pwd.equals(dbStudent.getStudentPassword())) {
            	errMessage = "��������!";
            	return false;
            }
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    /*���ͼ����Ϣ*/
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
     * ��ѯStudent��Ϣ���ʺϷ�ҳ��ʾ��
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
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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
     * �ʺ�������ʾ
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
     * ��ѯĳ�� �����˵�(ѧ�ţ����������п�Ŀδ��ɵ���ҵ�����ҳ�)
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
     * ��ѯĳ�� �����˵� (ѧ�ţ�������ĳ�ſγ̵�δ��ɵ���ҵ��)
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

    /*�������ܣ���ѯ���е�Student��¼*/
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
    

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
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

    /*����Student��Ϣ*/
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

    /*ɾ��Student��Ϣ*/
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
