<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.ClassInfo"%>
<%@ page import="com.orga.domain.Student"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int currentPage = (Integer)request.getAttribute("currentPage"); //当前页
int totalPage = (Integer)request.getAttribute("totalPage");  //一共多少页
int recordNumber = (Integer)request.getAttribute("recordNumber");  //一共多少记录
String classNumber = (String)request.getAttribute("classNumber");
List<Student> studentList = (List<Student>)request.getAttribute("studentList");
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
	<div id="report_data">
		<table id="datalist">
			<form action="<%=basePath%>Student/Student_QueryClassStudent.action" name="studentQueryForm" method="post">
			<input type=hidden name='studentClass.classNumber' value="<%=classNumber%>" />
				<tr>
					<th>序号</th>
					<th class="width100">学生编号</th>
					<th>学生名称</th>
					<th>性别</th>
					<th>联系电话</th>
					<th class="width200">家庭地址</th>
					<th class="width200">学生备注</th>
					<th title="班主任可以修改完善学生信息!">操作</th>
				</tr>
				<%if(studentList != null) {
					for(int i=0; i<studentList.size(); i++) {
						Student student = studentList.get(i);
				%>
				<tr>
					<td><%=i+1 %></td>
					<td><%=student.getStudentNumber() %></td>
					<td><%=student.getStudentName() %></td>
					<td><%=student.getStudentSex() %></td>
					<td><%=student.getStudentTelephone() %></td>
					<td><%=student.getStudentAddress() %></td>
					<td><%=student.getStudentMemo() %></td>
					<td><input type="button" value="修改" class="btn_modify_wide" onclick="parent.document.getElementById('table_frame').src='<%=basePath%>Student/Student_ModifyStudentQuery.action?studentNumber=<%=student.getStudentNumber() %>';"/>
					<input type="button" value="家长信息" class="btn_modify_wide" onclick="parent.document.getElementById('table_frame').src='<%=basePath%>Parent/Parent_AddAndModifyView.action?studentNumber=<%=student.getStudentNumber() %>';"/>
					</td>
				</tr>
				<% } } %>
			</form>
		</table>
		<!--分页-->
	</div>
	<div id="pages">
		<span>共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %>页&nbsp;&nbsp;</span>
		<span><a onclick="GoToPage(1,<%=totalPage %>);" class="link">首页</a></span>
		<span><a onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);">上一页</a></span>
		<span><a onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);">下一页</a></span>
		<span><a onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);">尾页</a></span>
		<span>转到第&nbsp;<input name="pageValue" type="text" class="goto" />&nbsp;页</span>
		<span><button class="goto" onclick="ChangePage(<%=totalPage %>);">GO</button></span>
	</div>
</body>
</html>