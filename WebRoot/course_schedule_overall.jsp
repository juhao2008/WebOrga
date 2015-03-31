<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
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
	<body>
	<!--数据区域：用表格展示数据-->
	<div id="report_box">
		<!--数据区域：用表格展示数据-->
		<div id="report_data">
			<table id="datalist">
				<tr>
					<th>序号</th>
					<th>课程名称</th>
					<th>课时安排</th>
					<th class="width300">课程计划</th>
					<th>学期</th>
					<th>班级名称</th>
					<th>作业次数</th>
					<th>测验次数</th>
					<th>操作</th>
				</tr>
				<%
					if (courseScheduleList != null) {
						for (int i = 0; i < courseScheduleList.size(); i++) {
							CourseSchedule schedule = courseScheduleList.get(i);
				%>
				<tr>
					<td><%=i + 1%></td>
					<td><%=schedule.getCourseInfo().getCourseName()%></td>
					<td><%=schedule.getScheduleHour() %></td>
					<td><%=schedule.getScheduleMemo()%></td>
					<td><%=schedule.getTermInfo().getTermName()%></td>
					<td><%=schedule.getClassInfo().getClassName()%></td>
					<td><%=schedule.getAssignmentCount() %></td>
					<td><%=schedule.getTestCount() %></td>
					<td><input type="button" value="修改" class="btn_modify" onclick="#"/></td>
				</tr>
				<% 		}
					}
				%>
			</table>
		</div>
	</div>
	</body>
</html>