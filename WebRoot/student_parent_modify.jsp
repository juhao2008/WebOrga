<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.orga.domain.Parent" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    ArrayList<Parent> parentList = (ArrayList)request.getAttribute("parentList");
    Parent stuParent = null;
    if(parentList != null && parentList.size() > 0){
    	stuParent = parentList.get(0);
    }
    
    String userId=(String)session.getAttribute("userId");
    if(userId==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>家校通</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
		<script src="<%=basePath%>calendar.js"></script>
	</head>
	<body>
		<div id="detail_main">
		<s:form action='Parent/Parent_ModifyParent.action' method="post" id="parentModifyForm" onsubmit="return checkForm();"  enctype="multipart/form-data">
			<input type=hidden name='parent.parentNumber' value="<%=stuParent.getParentNumber() %>" />
			<input type=hidden name='parent.student.studentNumber' value="<%=stuParent.getStudent().getStudentNumber()%>" />
			<div class="text_info clearfix">
				<span>学生姓名：</span>
			</div>
			<div class="input_info">
				<input type="text" name="parent.student.studentName" value='<%=stuParent.getStudent().getStudentName() %>' readonly class="readonly"/>
			</div>
			<div class="text_info clearfix">
				<span>家长姓名：</span>
			</div>
			<div class="input_info">
				<input type="text" name="parent.parentName" id="parent.parentName" value='<%=stuParent==null?"":stuParent.getParentName() %>' /> <span class="required">*</span>
				<div class="validate_msg_long error_msg">20长度以内的汉字、字母和数字的组合</div>
			</div>
			<div class="text_info clearfix">
				<span>与学生关系：</span>
			</div>
			<div class="input_info">
				<input type="text" name="parent.relation" value='<%=stuParent==null?"":stuParent.getRelation() %>' />
			</div>
			<div class="text_info clearfix">
				<span>家长职业：</span>
			</div>
			<div class="input_info">
				<input type="text" name="parent.parentProfession" value='<%=stuParent==null?"":stuParent.getParentProfession() %>' />
			</div>
			<div class="text_info clearfix">
				<span>家长单位：</span>
			</div>
			<div class="input_info">
				<input type="text" name="parent.parentWork" value='<%=stuParent==null?"":stuParent.getParentWork() %>' />
			</div>
			<div class="text_info clearfix">
				<span>联系电话：</span>
			</div>
			<div class="input_info">
				<input type="text" name="parent.parentTelephone" value='<%=stuParent==null?"":stuParent.getParentTelephone() %>' />
			</div>
			<div class="text_info clearfix">
				<span>联系地址：</span>
			</div>
			<div class="input_info">
				<input type="text" name="parent.parentAddress" value='<%=stuParent==null?"":stuParent.getParentAddress() %>' />
			</div>
			<div class="text_info clearfix">
				<span>班主任备注：</span>
			</div>
			<div class="input_info_high">
				<textarea class="width300 height70" name="parent.parentMemo"><%=stuParent==null?"":stuParent.getParentMemo() %></textarea>
			</div>
			
			<!--操作按钮-->
			<div class="button_info clearfix">
				<input type='submit' name='button' value='保存' class="btn_save">
				<input type="button" name='cancel' value="取消" onclick='history.back(-1);' class="btn_save" />
			</div>
		</s:form>
		</div>
    </body>
</html>