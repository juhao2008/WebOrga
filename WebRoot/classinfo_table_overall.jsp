<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.ClassInfo"%>
<%@ page import="com.orga.utils.CommConst"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<ClassInfo> classInfoList = (List<ClassInfo>)request.getAttribute("classInfoList");

String userId=(String)session.getAttribute(CommConst.USER_ID);
if(userId==null){
    response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
}
System.out.println("classinfo_table_overall.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>家校通</title>
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
<script language="javascript" type="text/javascript">
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.forms[0].currentPage.value = currentPage;
    document.forms[0].submit();
}

function ChangePage(totalPage){
    var pageValue=document.forms[0].pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.forms[0].currentPage.value = pageValue;
    document.forms[0].submit();
}
</script>
</head>
<body>
	<!--数据区域：用表格展示数据-->
	<div id="report_box">
		<!--数据区域：用表格展示数据-->
		<div id="report_data">
			<table id="datalist">
				<tr>
					<th>序号</th>
					<th>班级编号</th>
					<th>班级名称</th>
					<th>年级名称</th>
					<th class="width150">学校名称</th>
					<th>班级人数</th>
					<th class="width300">班级备注</th>
					<th>操作</th>
				</tr>
				<%
					if (classInfoList != null) {
						for (int i = 0; i < classInfoList.size(); i++) {
							ClassInfo classInfo = classInfoList.get(i);
				%>
				<tr>
					<td><%=i + 1%></td>
					<td><%=classInfo.getClassNumber()%></td>
					<td><%=classInfo.getClassName()%></td>
					<td><%=classInfo.getClassGrade().getGradeName()%></td>
					<td><%=classInfo.getClassSchool().getSchoolName()%></td>
					<td><% %></td>
					<td><%=classInfo.getClassMemo()%></td>
					<td><input type="button" value="管理" class="btn_modify_wide" 
						onclick="parent.document.getElementById('table_frame').src='<%=basePath%>ClassInfo/ClassInfo_ModifyClassInfoQuery.action?classNumber=<%=classInfo.getClassNumber() %>';"/>
					</td>
				</tr>
				<%
						}
					}
				%>
			</table>
		</div>
	</div>
</body>
</html>