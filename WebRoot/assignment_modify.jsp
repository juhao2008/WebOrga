<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.orga.domain.Assignment" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的studentNumber信息
    Assignment assignment = (Assignment)request.getAttribute("assignmentData");
	System.out.println("assignment modify::" + (assignment == null));
    String userId=(String)session.getAttribute("userId");
    if(userId==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>家校通</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
		<script src="<%=basePath%>calendar.js"></script>
		<script language="javascript" type="text/javascript">
		function checkForm() {
		    var courseName = document.getElementById("assignment.assignmentName").value;
		    if(courseName=="") {
		        alert('请输入作业名称!');
		        return false;
		    }
		    return true; 
		}
		</script>
        </head>
    <body>
    	<div id="detail_main">
    		<s:form action="Assignment/Assignment_ModifyAssignment.action" method="post" id="assignmentModifyForm" onsubmit="return checkForm();"  enctype="multipart/form-data">
            	<div class="text_info clearfix"><span>编号：</span></div>
                <div class="input_info"><input type="text" name="assignment.assignmentId" class="readonly" readonly value="<%=assignment.getAssignmentId() %>" /></div>
            	<!--必填项-->
                <div class="text_info clearfix"><span>作业标题：</span></div>
                <div class="input_info">
                    <input type="text" name="assignment.assignmentName" value="<%=assignment.getAssignmentName() %>" />
                    <span class="required">*</span>
                    <div class="validate_msg_long">20长度以内的汉字、字母和数字的组合</div>
                </div>
                <div class="text_info clearfix"><span>我的课程：</span></div>
                <div class="input_info">
                	<select name="assignment.courseSchedule.id" class="readonly">
                        <%
                        	if(assignment != null) {
      					%>
          					<option value='<%=assignment.getCourseSchedule().getId() %>' selected><%=assignment.getCourseSchedule().getCourseInfo().getCourseName() 
          																+ " " + assignment.getCourseSchedule().getTermInfo().getTermName()
          																+ " " + assignment.getCourseSchedule().getClassInfo().getClassName() %></option>
      					<%
      						}
     	 				%>
                    </select>
                </div>
                
                <div class="text_info clearfix"><span>日期：</span></div>
                <div class="input_info">
                    <input type="text" name="assignment.assignmentDate" value="<%=assignment.getAssignmentDate() %>" onclick="setDay(this);" />
                </div>
                <div class="text_info clearfix"><span>作业内容：</span></div>
                <div class="input_info_high">
                    <textarea class="width300 height70" name="assignment.assignmentContent"><%=assignment.getAssignmentContent() %></textarea>
                </div>
                <!--操作按钮-->
                <div class="button_info clearfix">
                    <input type="submit" value="保存" class="btn_save" />
                    <input type="button" name='back' value="返回" onclick='history.back(-1);' class="btn_save" />
                </div>
            </s:form>
    	</div> 
    </body>
</html>