<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.orga.domain.Assignment" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�studentNumber��Ϣ
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
        <title>��Уͨ</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
		<script src="<%=basePath%>calendar.js"></script>
		<script language="javascript" type="text/javascript">
		function checkForm() {
		    var courseName = document.getElementById("assignment.assignmentName").value;
		    if(courseName=="") {
		        alert('��������ҵ����!');
		        return false;
		    }
		    return true; 
		}
		</script>
        </head>
    <body>
    	<div id="detail_main">
    		<s:form action="Assignment/Assignment_ModifyAssignment.action" method="post" id="assignmentModifyForm" onsubmit="return checkForm();"  enctype="multipart/form-data">
            	<div class="text_info clearfix"><span>��ţ�</span></div>
                <div class="input_info"><input type="text" name="assignment.assignmentId" class="readonly" readonly value="<%=assignment.getAssignmentId() %>" /></div>
            	<!--������-->
                <div class="text_info clearfix"><span>��ҵ���⣺</span></div>
                <div class="input_info">
                    <input type="text" name="assignment.assignmentName" value="<%=assignment.getAssignmentName() %>" />
                    <span class="required">*</span>
                    <div class="validate_msg_long">20�������ڵĺ��֡���ĸ�����ֵ����</div>
                </div>
                <div class="text_info clearfix"><span>�ҵĿγ̣�</span></div>
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
                
                <div class="text_info clearfix"><span>���ڣ�</span></div>
                <div class="input_info">
                    <input type="text" name="assignment.assignmentDate" value="<%=assignment.getAssignmentDate() %>" onclick="setDay(this);" />
                </div>
                <div class="text_info clearfix"><span>��ҵ���ݣ�</span></div>
                <div class="input_info_high">
                    <textarea class="width300 height70" name="assignment.assignmentContent"><%=assignment.getAssignmentContent() %></textarea>
                </div>
                <!--������ť-->
                <div class="button_info clearfix">
                    <input type="submit" value="����" class="btn_save" />
                    <input type="button" name='back' value="����" onclick='history.back(-1);' class="btn_save" />
                </div>
            </s:form>
    	</div> 
    </body>
</html>