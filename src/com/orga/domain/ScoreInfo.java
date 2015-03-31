package com.orga.domain;

public class ScoreInfo {
    /*记录编号*/
    private int scoreId;
    public int getScoreId() {
        return scoreId;
    }
    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }
	
	/*学生对象*/
    private Student student;
    public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

    /*成绩项*/
    private TestInfo testInfo;
    public TestInfo getTestInfo() {
		return testInfo;
	}
	public void setTestInfo(TestInfo testInfo) {
		this.testInfo = testInfo;
	}

    /*成绩得分*/
    private float scoreValue;
    public float getScoreValue() {
        return scoreValue;
    }
    public void setScoreValue(float scoreValue) {
        this.scoreValue = scoreValue;
    }

    /*学生评价*/
    private String studentEvaluate;
    public String getStudentEvaluate() {
        return studentEvaluate;
    }
    public void setStudentEvaluate(String studentEvaluate) {
        this.studentEvaluate = studentEvaluate;
    }

}