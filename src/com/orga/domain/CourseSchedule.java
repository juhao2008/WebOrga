package com.orga.domain;

public class CourseSchedule {
	private int id;
	private ClassInfo classInfo;
	private CourseInfo courseInfo;
	private TermInfo termInfo;
	private Teacher teacherInfo;
	private String representative;
	//课程计划
	private String scheduleMemo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ClassInfo getClassInfo() {
		return classInfo;
	}
	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
	public CourseInfo getCourseInfo() {
		return courseInfo;
	}
	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}
	public TermInfo getTermInfo() {
		return termInfo;
	}
	public void setTermInfo(TermInfo termInfo) {
		this.termInfo = termInfo;
	}
	public Teacher getTeacherInfo() {
		return teacherInfo;
	}
	public void setTeacherInfo(Teacher teacherInfo) {
		this.teacherInfo = teacherInfo;
	}
	public String getScheduleMemo() {
		return scheduleMemo;
	}
	public void setScheduleMemo(String scheduleMemo) {
		this.scheduleMemo = scheduleMemo;
	}
	
	//课时按排
	private String scheduleHour;
	public String getScheduleHour() {
		return scheduleHour;
	}
	public void setScheduleHour(String scheduleHour) {
		this.scheduleHour = scheduleHour;
	}
	
	private int assignmentCount = 0;
	
	public int getAssignmentCount() {
		return assignmentCount;
	}
	public void setAssignmentCount(int assignmentCount) {
		this.assignmentCount = assignmentCount;
	}
	private int testCount = 0;
	public int getTestCount() {
		return testCount;
	}
	public void setTestCount(int testCount) {
		this.testCount = testCount;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	
	
}
