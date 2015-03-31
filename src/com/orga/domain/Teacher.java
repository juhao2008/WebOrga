package com.orga.domain;

public class Teacher {
    /*教师编号*/
    private String teacherNumber;
    public String getTeacherNumber() {
        return teacherNumber;
    }
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    /*教师姓名*/
    private String teacherName;
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /*登录密码*/
    private String teacherPassword;
    public String getTeacherPassword() {
        return teacherPassword;
    }
    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }
    
    /*教师所在学校代码*/
    private School teacherSchool;
    public School getTeacherSchool() {
		return teacherSchool;
	}
	public void setTeacherSchool(School teacherSchool) {
		this.teacherSchool = teacherSchool;
	}
	/* 隶属组织机构 */
	private SchGroup teacherSchGroup;
	public SchGroup getTeacherSchGroup() {
		return teacherSchGroup;
	}
	public void setTeacherSchGroup(SchGroup group) {
		this.teacherSchGroup = group;
	}
	
	private Role teacherRole;
	public Role getTeacherRole() {
		return teacherRole;
	}
	public void setTeacherRole(Role teacherRole) {
		this.teacherRole = teacherRole;
	}

	/*性别*/
    private String teacherSex;
    public String getTeacherSex() {
        return teacherSex;
    }
    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex;
    }

    /*出生日期*/
    private String teacherBirthday;
    public String getTeacherBirthday() {
        return teacherBirthday;
    }
    public void setTeacherBirthday(String teacherBirthday) {
        this.teacherBirthday = teacherBirthday;
    }

    /*入职日期*/
    private String teacherArriveDate;
    public String getTeacherArriveDate() {
        return teacherArriveDate;
    }
    public void setTeacherArriveDate(String teacherArriveDate) {
        this.teacherArriveDate = teacherArriveDate;
    }

    /*身份证号*/
    private String teacherCardNumber;
    public String getTeacherCardNumber() {
        return teacherCardNumber;
    }
    public void setTeacherCardNumber(String teacherCardNumber) {
        this.teacherCardNumber = teacherCardNumber;
    }
    /*职称*/
    private String teacherOfficialTitle;
    public String getTeacherOfficialTitle() {
		return teacherOfficialTitle;
	}
	public void setTeacherOfficialTitle(String teacherOfficialTitle) {
		this.teacherOfficialTitle = teacherOfficialTitle;
	}
	/*学历*/
    private String teacherEducation;
    public String getTeacherEducation() {
		return teacherEducation;
	}
	public void setTeacherEducation(String teacherEducation) {
		this.teacherEducation = teacherEducation;
	}
	/*毕业院校*/
    private String teacherCollege;
    public String getTeacherCollege() {
		return teacherCollege;
	}
	public void setTeacherCollege(String teacherCollege) {
		this.teacherCollege = teacherCollege;
	}

    /*办公电话*/
    private String teacherOfficePhone;
    public String getTeacherOfficePhone() {
		return teacherOfficePhone;
	}
	public void setTeacherOfficePhone(String teacherOfficePhone) {
		this.teacherOfficePhone = teacherOfficePhone;
	}

	/*教师照片*/
    private String teacherPhoto;
    public String getTeacherPhoto() {
        return teacherPhoto;
    }
    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    /*家庭地址*/
    private String teacherHomeAddress;
    public String getTeacherHomeAddress() {
		return teacherHomeAddress;
	}
	public void setTeacherHomeAddress(String teacherHomeAddress) {
		this.teacherHomeAddress = teacherHomeAddress;
	}
	
	/*附加信息*/
    private String teacherMemo;
    public String getTeacherMemo() {
        return teacherMemo;
    }
    public void setTeacherMemo(String teacherMemo) {
        this.teacherMemo = teacherMemo;
    }
    
    public String toString() {
    	return " [teacherNumber:" + teacherNumber + ", teacherName="+ teacherName + ",teacherSchool=" + teacherSchool + "teacherSchGroup=" + teacherSchGroup + "] ";
    }
    
    
    
    
    
}