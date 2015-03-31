<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.orga.utils.CommConst" %>
USER_ID
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
	session.removeAttribute(CommConst.USER_ID);	//移除保存在session中的username属性
	session.removeAttribute(CommConst.USER_NAME);
	session.removeAttribute(CommConst.USER_SCHOOL_NAME);
	session.removeAttribute(CommConst.USER_SCHOOL_NUMBER);
	session.removeAttribute(CommConst.USER_ROLE);
	session.removeAttribute(CommConst.USER_ROLE_NAME);
	session.invalidate();
	out.println("<script>top.location='" + basePath +"login/login_view.action';</script>");
%>