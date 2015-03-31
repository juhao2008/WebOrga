package com.orga.domain;

public class ClassInfo {
    /*班级编号*/
    private String classNumber;
    public String getClassNumber() {
        return classNumber;
    }
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /*班级名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    
    /*所属年级*/
    private GradeInfo classGrade;
    public GradeInfo getClassGrade() {
		return classGrade;
	}
	public void setClassGrade(GradeInfo classGrade) {
		this.classGrade = classGrade;
	}

    /*所属学校*/
    private School classSchool;
	public School getClassSchool() {
		return classSchool;
	}
	public void setClassSchool(School classSchool) {
		this.classSchool = classSchool;
	}

	/*成立日期*/
    private String classBirthDate;
    public String getClassBirthDate() {
        return classBirthDate;
    }
    public void setClassBirthDate(String classBirthDate) {
        this.classBirthDate = classBirthDate;
    }

    /*班主任*/
    private Teacher classTeacherCharge;
    public Teacher getClassTeacherCharge() {
        return classTeacherCharge;
    }
    public void setClassTeacherCharge(Teacher classTeacherCharge) {
        this.classTeacherCharge = classTeacherCharge;
    }
    /* 班长 */
    private String classMonitor;
    public String getClassMonitor() {
		return classMonitor;
	}
	public void setClassMonitor(String classMonitor) {
		this.classMonitor = classMonitor;
	}
	private String classPhoto;
	public String getClassPhoto() {
		return classPhoto;
	}
	public void setClassPhoto(String classPhoto) {
		this.classPhoto = classPhoto;
	}

    /*联系电话*/
    private String classTelephone;
    public String getClassTelephone() {
        return classTelephone;
    }
    public void setClassTelephone(String classTelephone) {
        this.classTelephone = classTelephone;
    }

    /*附加信息*/
    private String classMemo;
    public String getClassMemo() {
        return classMemo;
    }
    public void setClassMemo(String classMemo) {
        this.classMemo = classMemo;
    }
    
    /*状态*/
    private int classState;
    public int getClassState() {
		return classState;
	}
	public void setClassState(int classState) {
		this.classState = classState;
	}
	
}