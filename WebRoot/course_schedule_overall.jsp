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
        <title>��Уͨ</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" /> 
        </head>
	<body>
	<!--���������ñ��չʾ����-->
	<div id="report_box">
		<!--���������ñ��չʾ����-->
		<div id="report_data">
			<table id="datalist">
				<tr>
					<th>���</th>
					<th>�γ�����</th>
					<th>��ʱ����</th>
					<th class="width300">�γ̼ƻ�</th>
					<th>ѧ��</th>
					<th>�༶����</th>
					<th>��ҵ����</th>
					<th>�������</th>
					<th>����</th>
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
					<td><input type="button" value="�޸�" class="btn_modify" onclick="#"/></td>
				</tr>
				<% 		}
					}
				%>
			</table>
		</div>
	</div>
	</body>
</html>