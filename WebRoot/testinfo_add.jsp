<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.orga.domain.CourseSchedule" %>
<%@ page import="com.orga.domain.CourseScheduleStudent" %>
<%@ page import="com.orga.domain.Student" %>
<%@ page import="com.orga.domain.ScoreInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    ArrayList<CourseSchedule> courseScheduleList = (ArrayList<CourseSchedule>)request.getAttribute("courseScheduleList");
    List<ScoreInfo> scoreInfoList = (List<ScoreInfo>)request.getAttribute("scoreInfoList");
    List<CourseScheduleStudent> courseScheduleStudentList = (List<CourseScheduleStudent>)request.getAttribute("courseScheduleStudentList");
    
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
		    var courseName = document.getElementById("testInfo.testTitle").value;
		    if(courseName=="") {
		        alert('请输入成绩项名称!');
		        return false;
		    }
		    return true; 
		}
		</script>
        </head>
    <body>
    	<div id="detail_main">
    		<s:form action="TestInfo/TestInfo_AddTestInfo.action" method="post" id="testAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" >
            	<!--必填项-->
                <div class="text_info clearfix"><span>成绩项标题：</span></div>
                <div class="input_info">
                    <input type="text" name="testInfo.testTitle" value="" />
                    <span class="required">*</span>
                    <div class="validate_msg_long">20长度以内的汉字、字母和数字的组合</div>
                </div>
                <div class="text_info clearfix"><span>我的课程：</span></div>
                <div class="input_info">
                    <select name="testInfo.courseSchedule.id">
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
                <div class="text_info clearfix"><span>成绩项评语：</span></div>
                <div class="input_info_high">
                    <textarea class="width300 height70" name="testInfo.testEvaluate"></textarea>
                </div>
                <div class="text_info clearfix"><span>是否记入成绩：</span></div>
                <div class="input_info">
                    <select name="testInfo.recordScore">
                    	<option value='1' selected>是</option>
                    	<option value='0'>否</option>
                    </select>
                </div>
                <!--操作按钮-->
                <div class="button_info clearfix">
                    <input type="submit" value="保存" class="btn_save" />
                </div>
            </s:form>
            <form action="<%=basePath %>TestInfo/TestInfo_QueryTestInfo" name="QueryTestInfoForm" method="post">
            <div id="data">                      
                    <table id="datalist">
                        <tr>                            
                            <th>序号</th>
                            <th>班级</th>
                            <th>学生姓名</th>
                            <th>分数</th>
                            <th class="width400">评语</th>
                        </tr>
                        <% if(courseScheduleStudentList != null) {
                        	List<Student> studentList = courseScheduleStudentList.get(0).getStudentList();
		            	for(int i=0;i<studentList.size();i++) {
		            		Student student = studentList.get(i); //获取到News对象
             			%>                    
                        <tr>
                            <td><%=i+1 %></td>
                            <td><%=student.getStudentClass().getClassName() %></td>
                            <td><%=student.getStudentName() %></td>
                            <td><input type="text" name="testInfo.testTitle" value="" /> </td>
                            <td><input type="text" name="testInfo.testTitle" value="" /></td>
                            <td>
                                <input type="button" value="修改" class="btn_modify" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>TestInfo/TestInfo_AddView.action';" />
                            </td>
                        </tr>
                        <%	}  }%>
                    </table>
                </div> 
                </form>
            
    	</div>
    
    </body>
</html>