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
    <!--主要区域开始-->
        <div id="detail_main">  
        	<!--保存成功或者失败的提示消息-->     
            <div id="save_result_info" class="save_fail">保存失败，该身份证已经开通过账务账号！</div>          
            <s:form action="Assignment/Assignment_AddAssignment.action" method="post" id="assignmentAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
            	<!--必填项-->
                <div class="text_info clearfix"><span>作业标题：</span></div>
                <div class="input_info">
                    <input type="text" name="assignment.assignmentName" value="" />
                    <span class="required">*</span>
                    <div class="validate_msg_long">20长度以内的汉字、字母和数字的组合</div>
                </div>
                <div class="text_info clearfix"><span>我的课程：</span></div>
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
                
                <div class="text_info clearfix"><span>日期：</span></div>
                <div class="input_info">
                    <input type="text" id="assignment.assignmentDate" name="assignment.assignmentDate" onclick="setDay(this);" />
                </div>
                <div class="text_info clearfix"><span>作业内容：</span></div>
                <div class="input_info_high">
                    <textarea class="width300 height70" name="assignment.assignmentContent"></textarea>
                </div>
                <!--操作按钮-->
                <div class="button_info clearfix">
                    <input type="submit" value="保存" class="btn_save" />
                    <input type="reset" value="重写" class="btn_save" />
                </div>
            </s:form>
        </div>
    </body>
</html>