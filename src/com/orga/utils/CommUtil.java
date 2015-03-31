package com.orga.utils;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class CommUtil {
	
	public static boolean isNull(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	public static boolean isNotNull(String str) {
		if(str != null && str.trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	public static java.util.Date getCurrentDateTime() {
		Locale locale = Locale.CHINA;
		Calendar calendar = Calendar.getInstance(locale);
		java.util.Date bjTime = calendar.getTime();
		return bjTime;
	}
	
	public static String getCurrentDateTimeStr() {
		return getCurrentDateTime().toLocaleString();
	}
	
	public static String getRemortIP() {
		String strIP = "127.0.0.0";
		HttpServletRequest request = (HttpServletRequest) ServletActionContext
				.getRequest();
		if (request.getHeader("x-forwarded-for") == null) {
			strIP = request.getRemoteAddr();
		}
		strIP = request.getHeader("x-forwarded-for");
		return strIP;
	}
	
	public static void dumpMsg(String msg) {
		System.out.println(getCurrentDateTimeStr() + msg);
	}

}
