<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.utils.CommConst" %>
<%@ page import="com.orga.utils.CommUtil" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userId = (String)session.getAttribute(CommConst.USER_ID);
System.out.println("  admin_main.jsp");
if(CommUtil.isNull(userId)){
    //response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    response.sendRedirect("index.jsp");
}
String userName = (String)session.getAttribute(CommConst.USER_NAME);
String userRoleName = (String)session.getAttribute(CommConst.USER_ROLE_NAME);
String userRole = (String)session.getAttribute(CommConst.USER_ROLE);

if(!CommConst.ROLE_R4.equals(userRole)) {
	response.sendRedirect("index.jsp");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>家校通_学校后台管理首页</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color.css" /> 
        <script language="javascript" type="text/javascript">
        	function OnMenuClick(index) {
        		var a1 = parent.document.getElementById('a1');
        		var a2 = parent.document.getElementById('a2');
        		var a3 = parent.document.getElementById('a3');
        		var a4 = parent.document.getElementById('a4');
        		var a5 = parent.document.getElementById('a5');
        		var a6 = parent.document.getElementById('a6');
        		var a7 = parent.document.getElementById('a7');
        		var a8 = parent.document.getElementById('a8');
        		a1.className = "myself_off";
        		a2.className = "school_off";
        		a3.className = "classinfo_off";
        		a4.className = "course_off";
        		a5.className = "assignment_off";
        		a6.className = "score_off";
        		a7.className = "notify_off";
        		a8.className = "report_off";
        		
        		switch(index) {
        		case 1:
        			a1.className = "myself_on";
        			document.getElementById('center_frame').src='<%=basePath %>login/login_QuerySelfInfo.action';
        			break;
        		case 2:
        			a2.className = "school_on";
        			parent.document.getElementById('center_frame').src='<%=basePath %>News/News_QueryNews.action';
        			return false;
        		case 3:
        			a3.className = "classinfo_on";
        			parent.document.getElementById('center_frame').src='<%=basePath %>ClassInfo/ClassInfo_QueryClassInfoRecords.action';
        			return false;
        		case 4:
        			a4.className = "course_on";
        			parent.document.getElementById('center_frame').src='<%=basePath %>CourseSchedule/CourseSchedule_CourseScheduleOverall.action';
        			return false;
        		case 5:
        			a5.className = "assignment_on";
        			parent.document.getElementById('center_frame').src='<%=basePath %>Assignment/Assignment_QueryAssignment.action';
        			return false;
        		case 6:
        			a6.className = "score_on";
        			parent.document.getElementById('center_frame').src='<%=basePath %>TestInfo/TestInfo_QueryTestInfo.action';
        			return false;
        		case 7:
        			a7.className = "notify_on";
        			parent.document.getElementById('center_frame').src='<%=basePath %>Notify/Notify_QueryNotify.action';
        			return false;
        		case 8:
        			a8.className = "report_on";
        			return false;
        		case 9:
        			a9.className = "myself_on";
        			return false;
        		}
        	}
        	function GoBack() {
        		location.href = '<%=basePath %>main.jsp';
        	}
        	function Logout() {
        		parent.document.getElementById('center_frame').src='<%=basePath %>logout.jsp';
        		return false;
        	}
        </script>
    </head>
    <body>
    <!--Logo区域开始-->
        <div id="header">
            <img src="<%=basePath %>images/logo.png" alt="logo" class="left"/>
            <a href="#" ><span onclick="OnMenuClick(9);"><%=userName %>&nbsp;[<%=userRoleName %>]</span>
            &nbsp;&nbsp;<span onclick="GoBack();">返回教师首页</span>
            &nbsp;&nbsp;<span onclick="Logout();" >[退出]</span></a>            
        </div>
    <!--Logo区域结束-->
        <!--导航区域开始-->
        <div id="navi">                   
            <ul id="menu">
                <li><a id="a1" href="#" onclick="OnMenuClick(1);" class="myself_on"></a></li>
                <li><a id="a2" href="#" onclick="OnMenuClick(2);" class="school_off"></a></li>
                <li><a id="a3" href="#" onclick="OnMenuClick(3);" class="classinfo_off"></a></li>
                <li><a id="a4" href="#" onclick="OnMenuClick(4);" class="course_off"></a></li>
                <li><a id="a5" href="#" onclick="OnMenuClick(5);" class="assignment_off"></a></li>
                <li><a id="a6" href="#" onclick="OnMenuClick(6);" class="score_off"></a></li>
                <li><a id="a7" href="#" onclick="OnMenuClick(7);" class="notify_off"></a></li>
                <li><a id="a8" href="#" onclick="OnMenuClick(8);" class="report_off"></a></li>
            </ul>            
        </div>
        <!--导航区域结束-->
        <div id="main">
        	<iframe src="<%=basePath %>login/login_QuerySelfInfo.action" id="center_frame" name="center_frame" scrolling="no" marginwidth=0 
        	marginheight=0 frameborder=0 vspace=0 hspace=0></iframe>
        <!--主要区域结束-->
        </div>
        <div id="footer">
            <p>版权所有(C)合肥讯通科技股分公司 </p>
        </div>
    </body>
</html>