package com.orga.domain;

public class School {
    /*学校编号*/
    private String schoolNumber;
    
    /*学校名称*/
    private String schoolName;

    /*成立日期*/
    private String schoolBirthDate;

    /*校长姓名*/
    private String schoolManager;

    /*联系电话*/
    private String schoolTelephone;
    
    /*联系地址*/
    private String schoolAddress;

    /*附加信息*/
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