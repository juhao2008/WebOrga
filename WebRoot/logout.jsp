<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.orga.utils.CommConst" %>
USER_ID
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
	session.removeAttribute(CommConst.USER_ID);	//�Ƴ�������session�е�username����
	session.removeAttribute(CommConst.USER_NAME);
	session.removeAttribute(CommConst.USER_SCHOOL_NAME);
	session.removeAttribute(CommConst.USER_SCHOOL_NUMBER);
	session.removeAttribute(CommConst.USER_ROLE);
	session.removeAttribute(CommConst.USER_ROLE_NAME);
	session.invalidate();
	out.println("<script>top.location='" + basePath +"login/login_view.action';</script>");
%>