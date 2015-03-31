package com.orga.domain;

public class Notify {
    private int notifyId;
    private String notifyAuthor;
    private ClassInfo notifyClass;
    private String notifyTitle;
    private String notifyContent;
    private String notifyDate;
	public int getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}
	public String getNotifyTitle() {
		return notifyTitle;
	}
	public void setNotifyTitle(String notifyTitle) {
		this.notifyTitle = notifyTitle;
	}
	public String getNotifyContent() {
		return notifyContent;
	}
	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}
	public String getNotifyDate() {
		return notifyDate;
	}
	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}
    
	public String toString() {
		return notifyTitle + notifyDate + notifyContent;
	}
	public String getNotifyAuthor() {
		return notifyAuthor;
	}
	public void setNotifyAuthor(String notifyAuthor) {
		this.notifyAuthor = notifyAuthor;
	}
	public ClassInfo getNotifyClass() {
		return notifyClass;
	}
	public void setNotifyClass(ClassInfo notifyClass) {
		this.notifyClass = notifyClass;
	}
}