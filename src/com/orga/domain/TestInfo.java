package com.orga.domain;

public class TestInfo {
	private int testId;
	private String testTitle;
	private CourseSchedule courseSchedule;
	/** ����ʱ�� **/
	private String testDate;
	/** ����Ŀ�� **/
	private String testPropose;
	/** �������� **/
	private String testEvaluate;
	/** �Ƿ�����ܳɼ� **/
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
