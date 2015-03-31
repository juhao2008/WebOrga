package com.orga.domain;

public class TestInfo {
	private int testId;
	private String testTitle;
	private CourseSchedule courseSchedule;
	/** 测验时间 **/
	private String testDate;
	/** 测验目的 **/
	private String testPropose;
	/** 测验评价 **/
	private String testEvaluate;
	/** 是否记入总成绩 **/
	private int recordScore;
	
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}
	public String getTestTitle() {
		return testTitle;
	}
	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}
	public CourseSchedule getCourseSchedule() {
		return courseSchedule;
	}
	public void setCourseSchedule(CourseSchedule courseSchedule) {
		this.courseSchedule = courseSchedule;
	}
	public String getTestEvaluate() {
		return testEvaluate;
	}
	public void setTestEvaluate(String testEvaluate) {
		this.testEvaluate = testEvaluate;
	}
	public int getRecordScore() {
		return recordScore;
	}
	public void setRecordScore(int recordScore) {
		this.recordScore = recordScore;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public String getTestPropose() {
		return testPropose;
	}
	public void setTestPropose(String testPropose) {
		this.testPropose = testPropose;
	}
}
