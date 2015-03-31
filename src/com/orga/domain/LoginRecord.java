package com.orga.domain;


public class LoginRecord {
	private String loginIp;
	private String loginName;
	private String loginTime;
	private String lastestLoginTime;
	private String loginWhere;

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginWhere() {
		return loginWhere;
	}

	public void setLoginWhere(String loginWhere) {
		this.loginWhere = loginWhere;
	}

	public String getLastestLoginTime() {
		return lastestLoginTime;
	}

	public void setLastestLoginTime(String lastestLoginTime) {
		this.lastestLoginTime = lastestLoginTime;
	}

}
