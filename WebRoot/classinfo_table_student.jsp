<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.ClassInfo"%>
<%@ page import="com.orga.domain.Student"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int currentPage = (Integer)request.getAttribute("currentPage"); //��ǰҳ
int totalPage = (Integer)request.getAttribute("totalPage");  //һ������ҳ
int recordNumber = (Integer)request.getAttribute("recordNumber");  //һ�����ټ�¼
String classNumber = (String)request.getAttribute("classNumber");
List<Student> studentList = (List<Student>)request.getAttribute("studentList");
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
	<div id="report_data">
		<table id="datalist">
			<form action="<%=basePath%>Student/Student_QueryClassStudent.action" name="studentQueryForm" method="post">
			<input type=hidden name='studentClass.classNumber' value="<%=classNumber%>" />
				<tr>
					<th>���</th>
					<th class="width100">ѧ�����</th>
					<th>ѧ������</th>
					<th>�Ա�</th>
					<th>��ϵ�绰</th>
					<th class="width200">��ͥ��ַ</th>
					<th class="width200">ѧ����ע</th>
					<th title="�����ο����޸�����ѧ����Ϣ!">����</th>
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
					<td><input type="button" value="�޸�" class="btn_modify_wide" onclick="parent.document.getElementById('table_frame').src='<%=basePath%>Student/Student_ModifyStudentQuery.action?studentNumber=<%=student.getStudentNumber() %>';"/>
					<input type="button" value="�ҳ���Ϣ" class="btn_modify_wide" onclick="parent.document.getElementById('table_frame').src='<%=basePath%>Parent/Parent_AddAndModifyView.action?studentNumber=<%=student.getStudentNumber() %>';"/>
					</td>
				</tr>
				<% } } %>
			</form>
		</table>
		<!--��ҳ-->
	</div>
	<div id="pages">
		<span>����<%=recordNumber %>����¼����ǰ�� <%=currentPage %>/<%=totalPage %>ҳ&nbsp;&nbsp;</span>
		<span><a onclick="GoToPage(1,<%=totalPage %>);" class="link">��ҳ</a></span>
		<span><a onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);">��һҳ</a></span>
		<span><a onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);">��һҳ</a></span>
		<span><a onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);">βҳ</a></span>
		<span>ת����&nbsp;<input name="pageValue" type="text" class="goto" />&nbsp;ҳ</span>
		<span><button class="goto" onclick="ChangePage(<%=totalPage %>);">GO</button></span>
	</div>
</body>
</html>