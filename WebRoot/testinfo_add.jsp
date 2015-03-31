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
        <title>��Уͨ</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
		<script src="<%=basePath%>calendar.js"></script>
		<script language="javascript" type="text/javascript">
		function checkForm() {
		    var courseName = document.getElementById("testInfo.testTitle").value;
		    if(courseName=="") {
		        alert('������ɼ�������!');
		        return false;
		    }
		    return true; 
		}
		</script>
        </head>
    <body>
    	<div id="detail_main">
    		<s:form action="TestInfo/TestInfo_AddTestInfo.action" method="post" id="testAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" >
            	<!--������-->
                <div class="text_info clearfix"><span>�ɼ�����⣺</span></div>
                <div class="input_info">
                    <input type="text" name="testInfo.testTitle" value="" />
                    <span class="required">*</span>
                    <div class="validate_msg_long">20�������ڵĺ��֡���ĸ�����ֵ����</div>
                </div>
                <div class="text_info clearfix"><span>�ҵĿγ̣�</span></div>
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
                <div class="text_info clearfix"><span>�ɼ������</span></div>
                <div class="input_info_high">
                    <textarea class="width300 height70" name="testInfo.testEvaluate"></textarea>
                </div>
                <div class="text_info clearfix"><span>�Ƿ����ɼ���</span></div>
                <div class="input_info">
                    <select name="testInfo.recordScore">
                    	<option value='1' selected>��</option>
                    	<option value='0'>��</option>
                    </select>
                </div>
                <!--������ť-->
                <div class="button_info clearfix">
                    <input type="submit" value="����" class="btn_save" />
                </div>
            </s:form>
            <form action="<%=basePath %>TestInfo/TestInfo_QueryTestInfo" name="QueryTestInfoForm" method="post">
            <div id="data">                      
                    <table id="datalist">
                        <tr>                            
                            <th>���</th>
                            <th>�༶</th>
                            <th>ѧ������</th>
                            <th>����</th>
                            <th class="width400">����</th>
                        </tr>
                        <% if(courseScheduleStudentList != null) {
                        	List<Student> studentList = courseScheduleStudentList.get(0).getStudentList();
		            	for(int i=0;i<studentList.size();i++) {
		            		Student student = studentList.get(i); //��ȡ��News����
             			%>                    
                        <tr>
                            <td><%=i+1 %></td>
                            <td><%=student.getStudentClass().getClassName() %></td>
                            <td><%=student.getStudentName() %></td>
                            <td><input type="text" name="testInfo.testTitle" value="" /> </td>
                            <td><input type="text" name="testInfo.testTitle" value="" /></td>
                            <td>
                                <input type="button" value="�޸�" class="btn_modify" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>TestInfo/TestInfo_AddView.action';" />
                            </td>
                        </tr>
                        <%	}  }%>
                    </table>
                </div> 
                </form>
            
    	</div>
    
    </body>
</html>