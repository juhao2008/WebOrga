package com.orga.domain;

public class StudentAssignment {
	public final static int STATUS_UNFINISH = 0;
	public final static int STATUS_FINISH_UNSIGN = 1;
	public final static int STATUS_FINISH_SIGN = 2;
	private int id;
	private Student student;
	private Assignment assignment;
	private String finishDate;
	private String signDate;
	private String signUrl;
	/** 0:未完成  1:已完成未签字  2: 完成并已签字**/
	private int assignmentStatus;
	
	public String getSignUrl() {
		return signUrl;
	}


	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}


	public int getAssignmentStatus() {
		return assignmentStatus;
	}


	public void setAssignmentStatus(int assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public Assignment getAssignment() {
		return assignment;
	}


	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}


	public String getFinishDate() {
		return finishDate;
	}


	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}


	public String getSignDate() {
		return signDate;
	}


	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String toString() {
		return " [" + id + assignment + finishDate +  signDate + assignmentStatus + "] ";
	}


}
