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

import com.orga.domain.ClassInfo;

public class ClassInfoDAO {

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
     * ��Ӱ༶��Ϣ
     * @param classInfo
     * @throws Exception
     */
    public void AddClassInfo(ClassInfo classInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(classInfo);
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
     * ���ط��������ļ�¼����
     * @param schoolNumber
     * @param gradeNunmber
     * @param teacherNumber
     * @return
     */
    public int CountClassInfo(String schoolNumber, String gradeNumber, String teacherNumber) throws Exception{
    	Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "COUNT(*) From ClassInfo classInfo where 1=1";
            if(CommUtil.isNotNull(schoolNumber)) {
            	hql += " and classInfo.classSchool.schoolNumber like '%" + schoolNumber + "%'";
            }
            if(CommUtil.isNotNull(gradeNumber)) {
            	hql += " and classInfo.classGrade.gradeNumber like '%" + gradeNumber + "%'";
            }
            if(CommUtil.isNotNull(teacherNumber)) {
            	hql += " and classInfo.classTeacherCharge.teacherNumber like '%" + teacherNumber + "%'";
            }
            Query q = s.createQuery(hql);
            List classInfoList = q.list();
            if(classInfoList != null)
            	return classInfoList.size();
            else return 0;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * ��ѯClassInfo��Ϣ, �����ڷ�ҳ��ʾ
     * @param classNumber
     * @param className
     * @param schoolNumber
     * @param gradeNumber
     * @param teacherNumber
     * @param currentPage
     * @return
     */
    public ArrayList<ClassInfo> QueryClassInfo(String classNumber, String className, String schoolNumber, 
    		String gradeNumber, String teacherNumber, int currentPage) throws Exception{ 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ClassInfo classInfo where 1=1";
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and classInfo.classNumber like '%" + classNumber + "%'";
            }
            if(CommUtil.isNotNull(className)) {
            	hql += " and classInfo.className like '%" + className + "%'";
            }
            if(CommUtil.isNotNull(schoolNumber)) {
            	hql += " and classInfo.classSchool.schoolNumber like '%" + schoolNumber + "%'";
            }
            if(CommUtil.isNotNull(gradeNumber)) {
            	hql += " and classInfo.classGrade.gradeNumber like '%" + gradeNumber + "%'";
            }
            if(!CommUtil.isNotNull(teacherNumber)) {
            	hql = hql + " and classInfo.classTeacherCharge.teacherNumber like '%" + teacherNumber + "%'";
            }
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List classInfoList = q.list();
            return (ArrayList<ClassInfo>) classInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * �������ܣ���ѯ���е�ClassInfo��¼�� �����������б�
     * @param className
     * @param schoolNumber
     * @param gradeNumber
     * @param teacherNumber
     * @return
     */
    public ArrayList<ClassInfo> QueryClassInfo(String className, String schoolNumber, 
    		String gradeNumber, String teacherNumber) throws Exception{
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ClassInfo classInfo where 1=1";
            if(!CommUtil.isNotNull(className)) {
            	hql += " and classInfo.className like '%" + className + "%'";
            }
            if(!CommUtil.isNotNull(schoolNumber)) {
            	hql += " and classInfo.classSchool.schoolNumber like '%" + schoolNumber + "%'";
            }
            if(!CommUtil.isNotNull(gradeNumber)) {
            	hql += " and classInfo.classGrade.gradeNumber like '%" + gradeNumber + "%'";
            }
            if(!CommUtil.isNotNull(teacherNumber)) {
            	hql += " and classInfo.classTeacherCharge.teacherNumber like '%" + teacherNumber + "%'";
            }
            Query q = s.createQuery(hql);
            List classInfoList = q.list();
            return (ArrayList<ClassInfo>) classInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * �����ܵ�ҳ���ͼ�¼��
     * @param classNumber
     * @param className
     * @param schoolNumber
     * @param gradeNumber
     * @param teacherNumber
     */
    public void CalculateTotalPageAndRecordNumber(String classNumber, String className, String schoolNumber, 
    		String gradeNumber, String teacherNumber) throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From ClassInfo classInfo where 1=1";
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and classInfo.classNumber like '%" + classNumber + "%'";
            }
            if(CommUtil.isNotNull(className)) {
            	hql += " and classInfo.className like '%" + className + "%'";
            }
            if(CommUtil.isNotNull(schoolNumber)) {
            	hql += " and classInfo.classSchool.schoolNumber like '%" + schoolNumber + "%'";
            }
            if(CommUtil.isNotNull(gradeNumber)) {
            	hql = hql + " and classInfo.classGrade.gradeNumber like '%" + gradeNumber + "%'";
            }
            if(CommUtil.isNotNull(teacherNumber)) {
            	hql = hql + " and classInfo.classTeacherCharge.teacherNumber like '%" + teacherNumber + "%'";
            }
            Query q = s.createQuery(hql);
            List classInfoList = q.list();
            recordNumber = classInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * ��ȡĳ��������Ϣ
     * @param teacherNumber
     * @return
     */
    public List<Map> QueryClassSumary(String teacherNumber) {
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String hql = "classNumber,className,(select gradeName from t_gradeinfo where gradeNumber like classGrade) as gradeName, classMonitor,classPhoto," +
					" (select count(studentNumber) from t_student where studentClass like classNumber) as classHeader," +
					" (select count(id) from t_courseschedule where classInfo like classNumber) as courseCount" +
					" (select count(assignmentId) from t_assignment where courseSchedule=id) as assignmentCount," +
					" (select count(testId) from t_testinfo where courseSchedule=id) as testCount from t_classinfo where 1=1";
			if(CommUtil.isNotNull(teacherNumber)) {
            	hql += " and classTeacherCharge like '%" + teacherNumber + "%'";
            }
			SQLQuery query = s.createSQLQuery(hql)
					.addScalar("classNumber", Hibernate.STRING)
					.addScalar("className", Hibernate.STRING)
					.addScalar("gradeName", Hibernate.STRING)
					.addScalar("classMonitor", Hibernate.STRING)
					.addScalar("classPhoto", Hibernate.STRING)
					.addScalar("classHeader", Hibernate.INTEGER)
					.addScalar("courseCount", Hibernate.INTEGER)
					.addScalar("assignmentCount", Hibernate.INTEGER)
					.addScalar("testCount", Hibernate.INTEGER);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            return list;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
    public List<Map> QueryClassHeader(String classNumber) {
    	Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String hql = "classNumber,className," +
					" (select count(studentNumber) from t_student where studentClass like classNumber) as classHeader" +
					" from t_classinfo where 1=1";
			if(CommUtil.isNotNull(classNumber)) {
            	hql += " and classNumber like '%" + classNumber + "%'";
            }
			SQLQuery query = s.createSQLQuery(hql)
					.addScalar("classNumber", Hibernate.STRING)
					.addScalar("className", Hibernate.STRING)
					.addScalar("classHeader", Hibernate.INTEGER);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            return list;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * ����������ȡ����
     * @param classNumber
     * @return
     */
    public ClassInfo GetClassInfoByClassNumber(String classNumber) throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            ClassInfo classInfo = (ClassInfo)s.get(ClassInfo.class, classNumber);
            return classInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * ����ClassInfo��Ϣ
     * @param classInfo
     * @throws Exception
     */
    public void UpdateClassInfo(ClassInfo classInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(classInfo);
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
     * ɾ��ClassInfo��Ϣ
     * @param classNumber
     * @throws Exception
     */
    public void DeleteClassInfo (String classNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object classInfo = s.get(ClassInfo.class, classNumber);
            s.delete(classInfo);
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
