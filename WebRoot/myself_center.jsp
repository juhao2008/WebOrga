<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.orga.domain.Teacher" %>
<%@ page import="com.orga.utils.CommUtil" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Teacher teacher = (Teacher)request.getAttribute("teacher");
    String userId=(String)session.getAttribute("userId");
    if(CommUtil.isNull(userId)){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>家校通_个人中心</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
        </head>
    <body>
    	<div id="detail_main">
            	<div class="text_info clearfix"><span>登录帐号：</span></div>
                <div class="input_info"><%=teacher.getTeacherNumber() %></div>
            	<!--必填项-->
                <div class="text_info clearfix"><span>姓名：</span></div>
                <div class="input_info"><%=teacher.getTeacherName() %></div>
                
                <div class="text_info clearfix"><span>角色：</span></div>
                <div class="input_info"><%=teacher.getTeacherRole().getRoleName() %></div>
                
                <div class="text_info clearfix"><span>所在学校：</span></div>
                <div class="input_info"><%=teacher.getTeacherSchool().getSchoolName() %></div>
                
                <div class="text_info clearfix"><span>所在部门：</span></div>
                <div class="input_info"><%=teacher.getTeacherSchGroup().getSchGroupName() %></div>
                
                <div class="text_info clearfix"><span>登录时间：</span></div>
                <div class="input_info"><%=teacher.getTeacherName() %></div>
                
                <div class="text_info clearfix"><span>上次登录日期：</span></div>
                <div class="input_info"></div>
    	</div> 
    </body>
</html>