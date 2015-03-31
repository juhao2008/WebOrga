package com.orga.domain;

public class Assignment {
	private int assignmentId;
	private String assignmentName;
	private CourseSchedule courseSchedule;
	private String assignmentDate;
	private String assignmentContent;
	private String assignmentAttachment;
	
	public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int id) {
		assignmentId = id;
	}
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	public CourseSchedule getCourseSchedule() {
		return courseSchedule;
	}
	public void setCourseSchedule(CourseSchedule courseSchedule) {
		this.courseSchedule = courseSchedule;
	}
	public String getAssignmentDate() {
		return assignmentDate;
	}
	public void setAssignmentDate(String assignmentDate) {
		this.assignmentDate = assignmentDate;
	}
	public String getAssignmentContent() {
		return assignmentContent;
	}
	public void setAssignmentContent(String assignmentContent) {
		this.assignmentContent = assignmentContent;
	}
	public String getAssignmentAttachment() {
		return assignmentAttachment;
	}
	public void setAssignmentAttachment(String assignmentAttachment) {
		this.assignmentAttachment = assignmentAttachment;
	}
	
	public String toString() {
		return " [" + assignmentId + assignmentName + assignmentDate +  courseSchedule + assignmentAttachment + "] ";
	}

}
