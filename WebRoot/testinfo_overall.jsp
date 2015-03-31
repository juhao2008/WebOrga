<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.CourseTestInfo" %>
<%@ page import="com.orga.domain.CourseSchedule" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<CourseSchedule> courseScheduleList = (List<CourseSchedule>)request.getAttribute("courseScheduleList");

List<CourseTestInfo> courseTestList = (List<CourseTestInfo>)request.getAttribute("courseTestList");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>��Уͨ</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" /> 
        </head>
	<body>
		<div class="search_add">
            <div>�ҵĿγ�</div>
        </div>
        <!--���������ñ��չʾ����-->     
        <div id="data">                      
           <table id="datalist">
			 <tr>
				<th>���</th>
				<th>�γ�����</th>
				<th>ѧ��</th>
				<th>�༶</th>
				<th>�������</th>
				<th>ƽ���ɼ�</th>
				<th class="td_modi">����</th>
			</tr>
			<%
				/*������¼*/
				for (int i = 0; i < courseTestList.size(); i++) {
					CourseTestInfo courseTestInfo = courseTestList.get(i);
			%>                    
             <tr>
             	<td><%=i+1 %></td>
             	<td><%=courseTestInfo.getCourseSchedule().getCourseInfo().getCourseName() %></td>
             	<td><%=courseTestInfo.getCourseSchedule().getTermInfo().getTermName() %></td>
             	<td><%=courseTestInfo.getCourseSchedule().getClassInfo().getClassName() %></td>
             	<td><%=courseTestInfo.getTestCount() %></td>
             	<td></td>
                <td>
                   <input type="button" value="�޸�" class="btn_modify" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Assignment/Assignment_ModifyView.action %>';" />
                   <input type="button" value="ɾ��" class="btn_delete" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Assignment/Assignment_DeleteAssignment.action %>';"  />
                </td>
             </tr>
            <%	} %>
          	</table>
        </div>
	</body>
</html>