package com.orga.domain;

import java.util.List;


public class CourseScheduleStudent {
	public CourseScheduleStudent(CourseSchedule c, List<Student> l) {
		courseSchedule = c;
		studentList = l;
	}
	private CourseSchedule courseSchedule;
	public CourseSchedule getCourseSchedule() {
		return courseSchedule;
	}
	public void setCourseSchedule(CourseSchedule courseSchedule) {
		this.courseSchedule = courseSchedule;
	}
	public List<Student> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}
	private List<Student> studentList;

}
