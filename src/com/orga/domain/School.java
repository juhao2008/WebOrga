package com.orga.domain;

public class School {
    /*ѧУ���*/
    private String schoolNumber;
    
    /*ѧУ����*/
    private String schoolName;

    /*��������*/
    private String schoolBirthDate;

    /*У������*/
    private String schoolManager;

    /*��ϵ�绰*/
    private String schoolTelephone;
    
    /*��ϵ��ַ*/
    private String schoolAddress;

    /*������Ϣ*/
    private String schoolMemo;

    public String getSchoolNumber() {
		return schoolNumber;
	}

	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolBirthDate() {
		return schoolBirthDate;
	}

	public void setSchoolBirthDate(String schoolBirthDate) {
		this.schoolBirthDate = schoolBirthDate;
	}

	public String getSchoolManager() {
		return schoolManager;
	}

	public void setSchoolManager(String schoolManager) {
		this.schoolManager = schoolManager;
	}

	public String getSchoolTelephone() {
		return schoolTelephone;
	}

	public void setSchoolTelephone(String schoolTelephone) {
		this.schoolTelephone = schoolTelephone;
	}

	public String getSchoolMemo() {
		return schoolMemo;
	}

	public void setSchoolMemo(String schoolMemo) {
		this.schoolMemo = schoolMemo;
	}

    public String toString() {
    	return "[ " + schoolNumber + schoolName + schoolManager + schoolTelephone + "] ";
    }

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
}