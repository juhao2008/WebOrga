package com.orga.domain;

public class CourseInfo {
    /*�γ̱��*/
    private String courseNumber;
    public String getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    /*�γ�����*/
    private String courseName;
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    /*������Ϣ*/
    private String courseMemo;
    public String getCourseMemo() {
        return courseMemo;
    }
    public void setCourseMemo(String courseMemo) {
        this.courseMemo = courseMemo;
    }
    
	public String toString() {
		return " [" + courseNumber + courseName + courseMemo +"] ";
	}
	
	private int courseIconIndex;
	public int getCourseIconIndex() {
		return courseIconIndex;
	}
	public void setCourseIconIndex(int courseIconIndex) {
		this.courseIconIndex = courseIconIndex;
	}
	
	
	

}