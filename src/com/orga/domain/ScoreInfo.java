package com.orga.domain;

public class ScoreInfo {
    /*��¼���*/
    private int scoreId;
    public int getScoreId() {
        return scoreId;
    }
    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }
	
	/*ѧ������*/
    private Student student;
    public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

    /*�ɼ���*/
    private TestInfo testInfo;
    public TestInfo getTestInfo() {
		return testInfo;
	}
	public void setTestInfo(TestInfo testInfo) {
		this.testInfo = testInfo;
	}

    /*�ɼ��÷�*/
    private float scoreValue;
    public float getScoreValue() {
        return scoreValue;
    }
    public void setScoreValue(float scoreValue) {
        this.scoreValue = scoreValue;
    }

    /*ѧ������*/
    private String studentEvaluate;
    public String getStudentEvaluate() {
        return studentEvaluate;
    }
    public void setStudentEvaluate(String studentEvaluate) {
        this.studentEvaluate = studentEvaluate;
    }

}