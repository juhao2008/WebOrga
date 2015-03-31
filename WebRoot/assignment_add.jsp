<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.orga.domain.CourseSchedule" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    ArrayList<CourseSchedule> courseScheduleList = (ArrayList<CourseSchedule>)request.getAttribute("courseScheduleList");
    
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
    <!--��Ҫ����ʼ-->
        <div id="detail_main">  
        	<!--����ɹ�����ʧ�ܵ���ʾ��Ϣ-->     
            <div id="save_result_info" class="save_fail">����ʧ�ܣ������֤�Ѿ���ͨ�������˺ţ�</div>          
            <s:form action="Assignment/Assignment_AddAssignment.action" method="post" id="assignmentAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
            	<!--������-->
                <div class="text_info clearfix"><span>��ҵ���⣺</span></div>
                <div class="input_info">
                    <input type="text" name="assignment.assignmentName" value="" />
                    <span class="required">*</span>
                    <div class="validate_msg_long">20�������ڵĺ��֡���ĸ�����ֵ����</div>
                </div>
                <div class="text_info clearfix"><span>�ҵĿγ̣�</span></div>
                <div class="input_info">
                    <select name="assignment.courseSchedule.id">
                        <%
                        	if(courseScheduleList != null) {
      						for(CourseSchedule courseSchedule:courseScheduleList) {
      					%>
          					<option value='<%=courseSchedule.getId() %>'><%=courseSchedule.getCourseInfo().getCourseName() 
          																+ " " + courseSchedule.getTermInfo().getTermName()
          																+ " " + courseSchedule.getClassInfo().getClassName() %></option>
      					<%
      						}	}
     	 				%>
                    </select>                        
                </div>
                
                <div class="text_info clearfix"><span>���ڣ�</span></div>
                <div class="input_info">
                    <input type="text" id="assignment.assignmentDate" name="assignment.assignmentDate" onclick="setDay(this);" />
                </div>
                <div class="text_info clearfix"><span>��ҵ���ݣ�</span></div>
                <div class="input_info_high">
                    <textarea class="width300 height70" name="assignment.assignmentContent"></textarea>
                </div>
                <!--������ť-->
                <div class="button_info clearfix">
                    <input type="submit" value="����" class="btn_save" />
                    <input type="reset" value="��д" class="btn_save" />
                </div>
            </s:form>
        </div>
    </body>
</html>