package com.orga.domain;

public class ClassInfo {
    /*�༶���*/
    private String classNumber;
    public String getClassNumber() {
        return classNumber;
    }
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /*�༶����*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    
    /*�����꼶*/
    private GradeInfo classGrade;
    public GradeInfo getClassGrade() {
		return classGrade;
	}
	public void setClassGrade(GradeInfo classGrade) {
		this.classGrade = classGrade;
	}

    /*����ѧУ*/
    private School classSchool;
	public School getClassSchool() {
		return classSchool;
	}
	public void setClassSchool(School classSchool) {
		this.classSchool = classSchool;
	}

	/*��������*/
    private String classBirthDate;
    public String getClassBirthDate() {
        return classBirthDate;
    }
    public void setClassBirthDate(String classBirthDate) {
        this.classBirthDate = classBirthDate;
    }

    /*������*/
    private Teacher classTeacherCharge;
    public Teacher getClassTeacherCharge() {
        return classTeacherCharge;
    }
    public void setClassTeacherCharge(Teacher classTeacherCharge) {
        this.classTeacherCharge = classTeacherCharge;
    }
    /* �೤ */
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

    /*��ϵ�绰*/
    private String classTelephone;
    public String getClassTelephone() {
        return classTelephone;
    }
    public void setClassTelephone(String classTelephone) {
        this.classTelephone = classTelephone;
    }

    /*������Ϣ*/
    private String classMemo;
    public String getClassMemo() {
        return classMemo;
    }
    public void setClassMemo(String classMemo) {
        this.classMemo = classMemo;
    }
    
    /*״̬*/
    private int classState;
    public int getClassState() {
		return classState;
	}
	public void setClassState(int classState) {
		this.classState = classState;
	}
	
}