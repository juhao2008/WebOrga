package com.orga.domain;

public class SchGroup {
	/* 组织机构编号 */
	private String schGroupNumber;
	/* 组织机构编号 */
	private String schGroupName;
	/* 隶属学校 */
	private School schGroupSchool;
	/* 组织机构成立时间 */
	private String schGroupBirthDate;
	/* 组织机构管理者 */
	private String schGroupManager;
	private String schGroupTelephone;
	/* 组织机构简介 */
	private String schGroupResume;

	public String getSchGroupNumber() {
		return schGroupNumber;
	}

	public void setSchGroupNumber(String schGroupNumber) {
		this.schGroupNumber = schGroupNumber;
	}

	public String getSchGroupName() {
		return schGroupName;
	}

	public void setSchGroupName(String schGroupName) {
		this.schGroupName = schGroupName;
	}

	public School getSchGroupSchool() {
		return schGroupSchool;
	}

	public void setSchGroupSchool(School schGroupSchool) {
		this.schGroupSchool = schGroupSchool;
	}

	public String getSchGroupBirthDate() {
		return schGroupBirthDate;
	}

	public void setSchGroupBirthDate(String schGroupBirthDate) {
		this.schGroupBirthDate = schGroupBirthDate;
	}

	public String getSchGroupManager() {
		return schGroupManager;
	}

	public void setSchGroupManager(String schGroupManager) {
		this.schGroupManager = schGroupManager;
	}

	public String getSchGroupTelephone() {
		return schGroupTelephone;
	}

	public void setSchGroupTelephone(String schGroupTelephone) {
		this.schGroupTelephone = schGroupTelephone;
	}

	public String getSchGroupResume() {
		return schGroupResume;
	}

	public void setSchGroupResume(String schGroupResume) {
		this.schGroupResume = schGroupResume;
	}

}
