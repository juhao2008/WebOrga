package com.orga.domain;

public class Student {
    /* 编号 */
    private String studentNumber;
    public String getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /* 姓名 */
    private String studentName;
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    /* 学号 */
    private String studentId;
    public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

    /* 登录密码 */
    private String studentPassword;
    public String getStudentPassword() {
        return studentPassword;
    }
    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    /* 性别 */
    private String studentSex;
    public String getStudentSex() {
        return studentSex;
    }
    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }
    
    private String studentIDCard;
	public String getStudentIDCard() {
		return studentIDCard;
	}
	public void setStudentIDCard(String studentIDCard) {
		this.studentIDCard = studentIDCard;
	}

	/*所在年级*/
	private GradeInfo studentGrade;
	public GradeInfo getStudentGrade() {
		return studentGrade;
	}
	public void setStudentGrade(GradeInfo studentGrade) {
		this.studentGrade = studentGrade;
	}

	/*所在班级*/
    private ClassInfo studentClass;
    public ClassInfo getStudentClass() {
        return studentClass;
    }
    public void setStudentClass(ClassInfo studentClassNumber) {
        this.studentClass = studentClassNumber;
    }

    /*出生日期*/
    private String studentBirthday;
    public String getStudentBirthday() {
        return studentBirthday;
    }
    public void setStudentBirthday(String studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    /*政治面貌*/
    private String studentState;
    public String getStudentState() {
        return studentState;
    }
    public void setStudentState(String studentState) {
        this.studentState = studentState;
    }

    /*学生照片*/
    private String studentPhoto;
    public String getStudentPhoto() {
        return studentPhoto;
    }
    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    /*联系电话*/
    private String studentTelephone;
    public String getStudentTelephone() {
        return studentTelephone;
    }
    public void setStudentTelephone(String studentTelephone) {
        this.studentTelephone = studentTelephone;
    }

    /*学生邮箱*/
    private String studentEmail;
    public String getStudentEmail() {
        return studentEmail;
    }
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    /*联系qq*/
    private String studentQQ;
    public String getStudentQQ() {
        return studentQQ;
    }
    public void setStudentQQ(String studentQQ) {
        this.studentQQ = studentQQ;
    }

    /*家庭地址*/
    private String studentAddress;
    public String getStudentAddress() {
        return studentAddress;
    }
    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    /*附加信息*/
    private String studentMemo;
    public String getStudentMemo() {
        return studentMemo;
    }
    public void setStudentMemo(String studentMemo) {
        this.studentMemo = studentMemo;
    }
    
    public String toString() {
    	return studentNumber + studentName + studentSex + studentIDCard+ studentMemo;
    }
    
    /**
     * 动态创建，用于UI显示
     */
    private int unfinish;
    
    private String parentName;
    public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public int getUnfinish() {
		return unfinish;
	}
	public void setUnfinish(int unfinish) {
		this.unfinish = unfinish;
	}
	
	

}