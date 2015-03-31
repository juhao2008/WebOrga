<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.orga.domain.Student" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    Student student = (Student)request.getAttribute("student");
    
    String userId=(String)session.getAttribute("userId");
    if(userId==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>��Уͨ</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
		<script src="<%=basePath%>calendar.js"></script>
		<script language="javascript" type="text/javascript">
		function checkForm() {
		    var studentName = document.getElementById("student.studentName").value;
		    if(studentName=="" || studentName == undefine) {
		        alert('������ѧ������!');
		        return false;
		    }
		    return true; 
		}
		
		</script>
	</head>
	<body>
		<div id="detail_main">
		<s:form action="Student/Student_ModifyStudent.action" namespace="/Student" method="post" id="studentForm" enctype="multipart/form-data">
			<input type=hidden name='student.studentPassword' value="<%=student.getStudentPassword()%>" />
		<!--������-->
			<div class="text_info clearfix">
				<span>ѧ����ţ�</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentNumber" value="<%=student.getStudentNumber() %>" readonly class="readonly" />
			</div>
			<div class="text_info clearfix">
				<span>������</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentName" id="student.studentName" value="<%=student.getStudentName() %>" /> <span class="required">*</span>
				<div class="validate_msg_long error_msg">20�������ڵĺ��֡���ĸ�����ֵ����</div>
			</div>
			<div class="text_info clearfix">
				<span>�Ա�</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentSex" value="<%=student.getStudentSex() %>" readonly class="readonly" />
			</div>
			<div class="text_info clearfix">
				<span>�������ڣ�</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentBirthday" value="<%=student.getStudentBirthday() %>" readonly class="readonly" />
			</div>
			<div class="text_info clearfix">
				<span>���֤��</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentIDCard" value="<%=student.getStudentIDCard() %>" readonly class="readonly" />
			</div>
			<div class="text_info clearfix">
				<span>������ò��</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentState" value="<%=student.getStudentState() %>" />
			</div>
			<div class="text_info clearfix"><span>�꼶��</span></div>
			<div class="input_info">
				<select name="student.studentGrade.gradeNumber" readonly class="readonly">
					<option value='<%=student.getStudentGrade().getGradeNumber() %>' selected><%=student.getStudentGrade().getGradeName() %></option>
				</select>
			</div>
			<div class="text_info clearfix"><span>�༶��</span></div>
			<div class="input_info">
				<select name="student.studentClass.classNumber" readonly class="readonly">
					<option value='<%=student.getStudentClass().getClassNumber() %>' selected><%=student.getStudentClass().getClassName() %></option>
				</select>
			</div>
			<div class="text_info clearfix">
				<span>ѧ����Ƭ��</span>
			</div>
			<div class="input_info_high_high">
				<img src="<%=basePath %><%=student.getStudentPhoto() %>" border="0px" class="width200 height150" />
				<input type="hidden" name="student.studentPhoto" value="<%=student.getStudentPhoto() %>" />
    			<input id="studentPhotoFile" name="studentPhotoFile" type="file" size="50" />
			</div>
			<div class="text_info clearfix">
				<span>��ϵ�绰��</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentTelephone" value="<%=student.getStudentTelephone()%>" />
			</div>
			<div class="text_info clearfix">
				<span>�����ʼ���</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentEmail" value="<%=student.getStudentEmail() %>" />
			</div>
			<div class="text_info clearfix">
				<span>��ϵQQ��</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentQQ" value="<%=student.getStudentQQ() %>" />
			</div>
			<div class="text_info clearfix">
				<span>��ͥ��ַ��</span>
			</div>
			<div class="input_info">
				<input type="text" name="student.studentAddress" value="<%=student.getStudentAddress() %>" />
			</div>
			<div class="text_info clearfix">
				<span>������Ϣ��</span>
			</div>
			<div class="input_info_high">
                <textarea class="width300 height70" name="student.studentMemo"><%=student.getStudentMemo() %></textarea>
            </div>
			<!--������ť-->
			<div class="button_info clearfix">
				<input type='submit' name='button' value='����' class="btn_save" />
				<input type="button" name='cancel' value="ȡ��" onclick='history.back(-1);' class="btn_save" />
			</div>
		</s:form>
		</div>
    </body>
</html>