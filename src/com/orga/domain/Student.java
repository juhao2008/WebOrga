package com.orga.domain;

public class Student {
    /* ��� */
    private String studentNumber;
    public String getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /* ���� */
    private String studentName;
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    /* ѧ�� */
    private String studentId;
    public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

    /* ��¼���� */
    private String studentPassword;
    public String getStudentPassword() {
        return studentPassword;
    }
    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    /* �Ա� */
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

	/*�����꼶*/
	private GradeInfo studentGrade;
	public GradeInfo getStudentGrade() {
		return studentGrade;
	}
	public void setStudentGrade(GradeInfo studentGrade) {
		this.studentGrade = studentGrade;
	}

	/*���ڰ༶*/
    private ClassInfo studentClass;
    public ClassInfo getStudentClass() {
        return studentClass;
    }
    public void setStudentClass(ClassInfo studentClassNumber) {
        this.studentClass = studentClassNumber;
    }

    /*��������*/
    private String studentBirthday;
    public String getStudentBirthday() {
        return studentBirthday;
    }
    public void setStudentBirthday(String studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    /*������ò*/
    private String studentState;
    public String getStudentState() {
        return studentState;
    }
    public void setStudentState(String studentState) {
        this.studentState = studentState;
    }

    /*ѧ����Ƭ*/
    private String studentPhoto;
    public String getStudentPhoto() {
        return studentPhoto;
    }
    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    /*��ϵ�绰*/
    private String studentTelephone;
    public String getStudentTelephone() {
        return studentTelephone;
    }
    public void setStudentTelephone(String studentTelephone) {
        this.studentTelephone = studentTelephone;
    }

    /*ѧ������*/
    private String studentEmail;
    public String getStudentEmail() {
        return studentEmail;
    }
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    /*��ϵqq*/
    private String studentQQ;
    public String getStudentQQ() {
        return studentQQ;
    }
    public void setStudentQQ(String studentQQ) {
        this.studentQQ = studentQQ;
    }

    /*��ͥ��ַ*/
    private String studentAddress;
    public String getStudentAddress() {
        return studentAddress;
    }
    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    /*������Ϣ*/
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
     * ��̬����������UI��ʾ
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