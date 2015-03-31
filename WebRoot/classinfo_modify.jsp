<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.orga.domain.ClassInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    ClassInfo classInfo = (ClassInfo)request.getAttribute("classInfo");
    
    String userId=(String)session.getAttribute("userId");
    if(userId==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>��Уͨ_�༶��Ϣ����</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
		<script src="<%=basePath%>calendar.js"></script>
		<script language="javascript" type="text/javascript">
		function excelSubmit(type) {
			if(1 == type) {
				document.myForm.action='<%=basePath %>Student/Student_UploadExcelStudents.action?classNumber=<%=classInfo.getClassNumber() %>';
			} else if(2 == type) {
				document.myForm.action='<%=basePath %>ClassInfo/ClassInfo_ModifyClassInfo.action';
			}
			document.myForm.submit();
		} 
		</script>
	</head>
	<body>
		<div id="detail_main">
		<form action="<%=basePath %>ClassInfo/ClassInfo_ModifyClassInfo.action" name="myForm" method="post" enctype="multipart/form-data">
			<input type=hidden name='classInfo.classNumber' value="<%=classInfo.getClassNumber() %>" />
		<!--������-->
			<div class="text_info clearfix">
				<span>�༶���ƣ�</span>
			</div>
			<div class="input_info">
				<input type="text" name="classInfo.className" value="<%=classInfo.getClassName() %>" readonly class="readonly" />
			</div>
			<div class="text_info clearfix">
				<span>�꼶��</span>
			</div>
			<div class="input_info">
				<input type="text" name="classInfo.classGrade.gradeName" value="<%=classInfo.getClassGrade().getGradeName() %>" readonly class="readonly" />
			</div>
			<div class="text_info clearfix">
				<span>�༶����ʱ�䣺</span>
			</div>
			<div class="input_info">
				<input type="text" name="classInfo.classBirthDate" value="<%=classInfo.getClassBirthDate() %>" readonly class="readonly" />
			</div>
			<div class="text_info clearfix">
				<span>������ ��</span>
			</div>
			<div class="input_info">
				<input type="text" name="classInfo.classTeacherCharge.teacherName" value="<%=classInfo.getClassTeacherCharge().getTeacherName() %>" readonly class="readonly" />
			</div>
			<div class="text_info clearfix">
				<span>������Ϣ��</span>
			</div>
			<div class="input_info_high">
                <textarea class="width300 height70" name="classInfo.classMemo"><%=classInfo.getClassMemo() %></textarea>
            </div>
            
			<!--������ť-->
			<div class="button_info clearfix">
				<input type='submit' name='button' value='����'  onclick='excelSubmit(2);' class="btn_save" />
				<input type="button" name='cancel' value="ȡ��" onclick='history.back(-1);' class="btn_save" />
			</div>
			
			<div class="text_info clearfix">
			</div>
			<div class="text_info clearfix">
				<span>��������ѧ����Ϣ��</span>
			</div>
			<div class="input_info_high">
				<input id="excelFile" name="excelFile" type="file" size="50" />
				<a href="<%=basePath %>download/template_student.xls">ģ������<img src="<%=basePath %>images/icon_excel.png" alt="excel" /></a>
			</div>
			<div class="button_info clearfix">
				<input type='submit' value='�ύ' onclick='excelSubmit(1);' class="btn_save" />
			</div>
		</form>
		</div>
    </body>
</html>