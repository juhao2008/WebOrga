package com.orga.domain;

public class Teacher {
    /*��ʦ���*/
    private String teacherNumber;
    public String getTeacherNumber() {
        return teacherNumber;
    }
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    /*��ʦ����*/
    private String teacherName;
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /*��¼����*/
    private String teacherPassword;
    public String getTeacherPassword() {
        return teacherPassword;
    }
    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }
    
    /*��ʦ����ѧУ����*/
    private School teacherSchool;
    public School getTeacherSchool() {
		return teacherSchool;
	}
	public void setTeacherSchool(School teacherSchool) {
		this.teacherSchool = teacherSchool;
	}
	/* ������֯���� */
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

	/*�Ա�*/
    private String teacherSex;
    public String getTeacherSex() {
        return teacherSex;
    }
    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex;
    }

    /*��������*/
    private String teacherBirthday;
    public String getTeacherBirthday() {
        return teacherBirthday;
    }
    public void setTeacherBirthday(String teacherBirthday) {
        this.teacherBirthday = teacherBirthday;
    }

    /*��ְ����*/
    private String teacherArriveDate;
    public String getTeacherArriveDate() {
        return teacherArriveDate;
    }
    public void setTeacherArriveDate(String teacherArriveDate) {
        this.teacherArriveDate = teacherArriveDate;
    }

    /*���֤��*/
    private String teacherCardNumber;
    public String getTeacherCardNumber() {
        return teacherCardNumber;
    }
    public void setTeacherCardNumber(String teacherCardNumber) {
        this.teacherCardNumber = teacherCardNumber;
    }
    /*ְ��*/
    private String teacherOfficialTitle;
    public String getTeacherOfficialTitle() {
		return teacherOfficialTitle;
	}
	public void setTeacherOfficialTitle(String teacherOfficialTitle) {
		this.teacherOfficialTitle = teacherOfficialTitle;
	}
	/*ѧ��*/
    private String teacherEducation;
    public String getTeacherEducation() {
		return teacherEducation;
	}
	public void setTeacherEducation(String teacherEducation) {
		this.teacherEducation = teacherEducation;
	}
	/*��ҵԺУ*/
    private String teacherCollege;
    public String getTeacherCollege() {
		return teacherCollege;
	}
	public void setTeacherCollege(String teacherCollege) {
		this.teacherCollege = teacherCollege;
	}

    /*�칫�绰*/
    private String teacherOfficePhone;
    public String getTeacherOfficePhone() {
		return teacherOfficePhone;
	}
	public void setTeacherOfficePhone(String teacherOfficePhone) {
		this.teacherOfficePhone = teacherOfficePhone;
	}

	/*��ʦ��Ƭ*/
    private String teacherPhoto;
    public String getTeacherPhoto() {
        return teacherPhoto;
    }
    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    /*��ͥ��ַ*/
    private String teacherHomeAddress;
    public String getTeacherHomeAddress() {
		return teacherHomeAddress;
	}
	public void setTeacherHomeAddress(String teacherHomeAddress) {
		this.teacherHomeAddress = teacherHomeAddress;
	}
	
	/*������Ϣ*/
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