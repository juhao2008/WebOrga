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
import com.orga.domain.CourseSchedule;

public class CourseScheduleDAO {

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
     * 添加课程信息
     * @param courseSchedule
     * @throws Exception
     */
    public void AddCourseSchedule(CourseSchedule courseSchedule) throws Exception {
    	dumpMsg("AddCourseSchedule ::" + courseSchedule);
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(courseSchedule);
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
     * 查询CourseSchedule信息(分页显示)
     * @param classNumber
     * @param courseNumber
     * @param termNumber
     * @param teacherNumber
     * @param currentPage
     * @return
     */
    public ArrayList<CourseSchedule> QueryCourseSchedule(String schoolNumber, String classNumber, String courseNumber,
    		String termNumber, String teacherNumber, int currentPage) throws Exception{ 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseSchedule courseSchedule where 1=1";
            if(CommUtil.isNotNull(schoolNumber)) {
            	hql += " and courseSchedule.classInfo.classSchool.schoolNumber like '%" + schoolNumber + "%'";
            }
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and courseSchedule.classInfo.classNumber like '%" + classNumber + "%'";
            }
            if(CommUtil.isNotNull(courseNumber)) {
            	hql += " and courseSchedule.courseInfo.courseNumber like '%" + courseNumber + "%'";
            }
            if(CommUtil.isNotNull(termNumber)) {
            	hql += " and courseSchedule.termInfo.termNumber like '%" + termNumber + "%'";
            }
            if(CommUtil.isNotNull(teacherNumber)) {
            	hql += " and courseSchedule.teacherInfo.teacherNumber like '%" + teacherNumber + "%'";
            }
            System.out.println(hql);
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List courseScheduleList = q.list();
            return (ArrayList<CourseSchedule>) courseScheduleList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     * 查询所有的CourseSchedule记录(不分页)
     * @param id
     * @param classNumber
     * @param courseNumber
     * @param termNumber
     * @param teacherNumber
     * @return
     */
	public ArrayList<CourseSchedule> QueryCourseSchedule(int id,
			String classNumber, String courseNumber, String termNumber,
			String teacherNumber) throws Exception{
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseSchedule courseSchedule where 1=1";
            if(id > 0) {
            	hql += " and courseSchedule.id='" + id + "'";
            }
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and courseSchedule.classInfo.classNumber like '%" + classNumber + "%'";
            }
            if(CommUtil.isNotNull(courseNumber)) {
            	hql += " and courseSchedule.courseInfo.courseNumber like '%" + courseNumber + "%'";
            }
            if(CommUtil.isNotNull(termNumber)) {
            	hql += " and courseSchedule.termInfo.termNumber like '%" + termNumber + "%'";
            }
            if(CommUtil.isNotNull(teacherNumber)) {
            	hql += " and courseSchedule.teacherInfo.teacherNumber like '%" + teacherNumber + "%'";
            }
            this.dumpMsg(hql);
            Query q = s.createQuery(hql);
            List courseScheduleList = q.list();
            return (ArrayList<CourseSchedule>) courseScheduleList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
	public void CalculateTotalPageAndRecordNumber(String classNumber,
			String courseNumber, String termNumber, String teacherNumber) throws Exception{
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseSchedule courseSchedule where 1=1";
            if(CommUtil.isNotNull(classNumber)) {
            	hql += " and courseSchedule.classInfo.classNumber like '%" + classNumber + "%'";
            }
            if(CommUtil.isNotNull(courseNumber)) {
            	hql += " and courseSchedule.courseInfo.courseNumber like '%" + courseNumber + "%'";
            }
            if(CommUtil.isNotNull(termNumber)) {
            	hql += " and courseSchedule.termInfo.termNumber like '%" + termNumber + "%'";
            }
            if(CommUtil.isNotNull(teacherNumber)) {
            	hql += " and courseSchedule.teacherInfo.teacherNumber like '%" + teacherNumber + "%'";
            }
            Query q = s.createQuery(hql);
            List courseScheduleList = q.list();
            recordNumber = courseScheduleList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

	/**
	 * 获取某教师所带课程列表
	 * @param teacherNumber
	 * @return
	 * @throws Exception
	 */
	public List<Map> QueryTeacherCSs(String teacherNumber) throws Exception{
		Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String hql = "select id, representative, (select courseName from t_courseinfo where courseNumber=courseInfo) as courseName," +
					" (select courseIcon from t_courseInfo where courseNumber=courseInfo)" +
					" (select gradeName from t_gradeinfo where gradeNumber like (select classGrade from t_classinfo where classNumber like classInfo)) as gradeName," +
					" (select className from t_classinfo where classNumber like classInfo) as className, (select termName from t_terminfo where termInfo like termNumber) as termName," +
					" (select classMonitor from t_classinfo where classNumber like classInfo) as classMonitor" +
					" (select count(studentNumber) from t_student where studentClass like classInfo) as classHeader" +
					" (select count(assignmentId) from t_assignment where courseSchedule=id) as assignmentCount," +
					" (select count(testId) from t_testinfo where courseSchedule=id) as testCount from t_courseschedule where 1=1";
			if(CommUtil.isNotNull(teacherNumber)) {
            	hql += " and teacherInfo like '%" + teacherNumber + "%'";
            }
			SQLQuery query = s.createSQLQuery(hql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("representative", Hibernate.STRING)
					.addScalar("courseName", Hibernate.STRING)
					.addScalar("courseIcon", Hibernate.STRING)
					.addScalar("gradeName", Hibernate.STRING)
					.addScalar("className", Hibernate.STRING)
					.addScalar("termName", Hibernate.STRING)
					.addScalar("classHeader", Hibernate.STRING)
					.addScalar("assignmentCount", Hibernate.INTEGER)
					.addScalar("testCount", Hibernate.INTEGER);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            return list;
        } finally {
            HibernateUtil.closeSession();
        }
	}
	
	/**
	 * 获取某班的课程列表
	 * @param classNumber
	 * @return
	 * @throws Exception
	 */
	public List<Map> QueryClassCSs(String classNumber) throws Exception{
		Session s = null; 
        try {
			s = HibernateUtil.getSession();
			String sql = "select id, representative, (select courseName from t_courseinfo where courseNumber=courseInfo) as courseName," +
					" (select teacherName from t_teacher where teacherNumber like teacherInfo) as teacherName," +
					" (select teacherPhoto from t_teacher where teacherNumber like teacherInfo) as teacherPhoto," +
					" (select count(studentNumber) from t_student where studentClass like classInfo) as classHeader" +
					" (select count(assignmentId) from t_assignment where courseSchedule=id) as assignmentCount," +
					" (select count(testId) from t_testinfo where courseSchedule=id) as testCount from t_courseschedule where 1=1";
			if(CommUtil.isNotNull(classNumber)) {
				sql += " and classInfo like '%" + classNumber + "%'";
            }
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("representative", Hibernate.STRING)
					.addScalar("courseName", Hibernate.STRING)
					.addScalar("teacherName", Hibernate.STRING)
					.addScalar("teacherPhoto", Hibernate.STRING)
					.addScalar("classHeader", Hibernate.STRING)
					.addScalar("assignmentCount", Hibernate.INTEGER)
					.addScalar("testCount", Hibernate.INTEGER);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
        } finally {
            HibernateUtil.closeSession();
        }
	}
	
	/**
	 * 获取某课程按排具体内容
	 * @param courseScheduleId
	 * @return
	 * @throws Exception
	 */
	public List<Map> QueryCSDetail(int courseScheduleId) throws Exception{
		Session s = null;
		try {
			String sql = "select id, (select courseMemo from t_courseinfo where courseNumber like courseInfo) as courseMemo, scheduleHour, scheduleMemo from t_courseschedule where 1=1";
			if(courseScheduleId > 0) {
				sql += " and t_courseschedule=" + courseScheduleId;
			}
			SQLQuery query = s.createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("courseMemo", Hibernate.STRING)
					.addScalar("scheduleHour", Hibernate.STRING)
					.addScalar("scheduleMemo", Hibernate.STRING);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		} finally {
            HibernateUtil.closeSession();
        }
	}
	
    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    public CourseSchedule GetCourseScheduleById(int id) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            CourseSchedule courseSchedule = (CourseSchedule)s.get(CourseSchedule.class, id);
            return courseSchedule;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
    /**
     * 更新CourseSchedule信息
     * @param courseSchedule
     * @throws Exception
     */
    public void UpdateCourseSchedule(CourseSchedule courseSchedule) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(courseSchedule);
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
     * 删除CourseSchedule信息
     * @param strNumber
     * @throws Exception
     */
    public void DeleteCourseSchedule (int id) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object courseSchedule = s.get(CourseSchedule.class, id);
            s.delete(courseSchedule);
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
    	System.out.println(CommUtil.getCurrentDateTimeStr() + " [CourseScheduleDAO] " + msg);
    }

}
