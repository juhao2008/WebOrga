<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.TestInfo" %>
<%@ page import="com.orga.domain.CourseSchedule" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<CourseSchedule> courseScheduleList = (List<CourseSchedule>)request.getAttribute("courseScheduleList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>家校通</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" /> 
        </head>
	<body class="transparent">
	<!--主要区域开始-->
       <div class="tabs">
    	    <ul onclick="ChangeTab(event,this);">
    	    	<li><a href="#" class="tab_on" title="查看所有课程成绩项" onclick="document.getElementById('table_frame').src='<%=basePath%>TestInfo/TestInfo_QueryTestInfoOverall.action';">所有课程成绩</a></li>
    	        <% if(courseScheduleList != null) {
    	        		for(int i=0; i<courseScheduleList.size(); i++) {
    	        			CourseSchedule schedule = courseScheduleList.get(i);
    	        %>
        	    <li><a href="#" class="tab_out" title="查看课程具体详细" onclick="document.getElementById('table_frame').src='<%=basePath%>TestInfo/TestInfo_QueryTestCourseSchedule.action?id=<%=schedule.getId()%>';"><%=schedule.getCourseInfo().getCourseName() %></a></li>
                <% } }%>
            </ul>
        </div>
        <iframe src="<%=basePath %>TestInfo/TestInfo_QueryTestInfoOverall.action?iframe=abc" id="table_frame" scrolling="no" marginwidth=0 
        	marginheight=0 frameborder=0 vspace=0 hspace=0></iframe>
	</body>
</html>