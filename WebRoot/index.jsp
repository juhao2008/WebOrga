<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.utils.CommUtil" %>
<%@ page import="com.orga.utils.CommConst" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String userId = (String) session.getAttribute(CommConst.USER_ID);
	System.out.println("juhao test index.jsp  userId=" + userId);
	if (CommUtil.isNotNull(userId)) {
		response.sendRedirect("main.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
        <title>家校通_首页</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color.css" />
        <script language="javascript" type="text/javascript">
        	function naviTo(index) {
        		var a1 = parent.document.getElementById('a1');
        		var a2 = parent.document.getElementById('a2');
        		var a3 = parent.document.getElementById('a3');
        		var a4 = parent.document.getElementById('a4');
        		var a5 = parent.document.getElementById('a5');
        		var a6 = parent.document.getElementById('a6');
        		var a7 = parent.document.getElementById('a7');
        		var a8 = parent.document.getElementById('a8');
        		var a9 = parent.document.getElementById('a9');
        		var a10 = parent.document.getElementById('a10');
        		a1.className = "index_off";
        		a2.className = "school_off";
        		a3.className = "classinfo_off";
        		a4.className = "course_off";
        		a5.className = "assignment_off";
        		a6.className = "score_off";
        		a7.className = "notify_off";
        		a8.className = "report_off";
        		a9.className = "myself_off";
        		a10.className = "password_off";
        		
        		switch(index) {
        		case 1:
        			break;
        		case 2:
        			a2.className = "school_on";
        			var navi_msg = document.getElementById("navi_msg");
        			return false;
        		case 3:
        			a3.className = "classinfo_on";
        			return false;
        		case 4:
        			a4.className = "course_on";
        			return false;
        		case 5:
        			a5.className = "assignment_on";
        			return false;
        		case 6:
        			a6.className = "score_on";
        			return false;
        		case 7:
        			a7.className = "notify_on";
        			return false;
        		case 8:
        			a8.className = "report_on";
        			return false;
        		case 9:
        			a9.className = "myself_on";
        			return false;
        		case 10:
        			a10.className = "password_on";
        			break;
        		}
        	}
        </script>
    </head>
    <body class="index">
    	<%if(userId == null || userId.trim().length() == 0) {%>
    	<div id="login_div" class="login_box">
    		<form action="<%=basePath %>login/login_CheckLogin.action" method="post" name="form1">
            <table>
                <tr>
                    <td class="login_info">账号：</td>
                    <td colspan="2"><input name="loginUserInfo.userAccount" type="text" class="width150" /></td>
                    <td class="login_error_info"></td>
                </tr>
                <tr>
                    <td class="login_info">密码：</td>
                    <td colspan="2"><input name="loginUserInfo.userPwd" type="password" class="width150" /></td>
                    <td><input name="loginUserInfo.userRoleType" type=hidden value="<%=CommConst.ROLE_R3 %>" /></td>
                </tr>
                <tr>
                    <td class="login_info">验证码：</td>
                    <td class="width70"><input name="" type="text" class="width70" /></td>
                    <td><img src="<%=basePath %>images/valicode.jpg" alt="验证码" title="点击更换" /></td>  
                    <td><span class="required">验证码错误</span></td>              
                </tr>            
                <tr>
                    <td></td>
                    <td class="login_button" colspan="2">
                        <img src="<%=basePath %>images/login_btn.png" onclick="document.forms[0].submit();"/>
                    </td>    
                    <td></td>                
                </tr>
            </table>
            </form>
        </div>
        <% } %>
        <div id="navi_msg" class="navi_msg"><span>测试信息</span>
        </div>
        <!--导航区域开始-->
        <div id="index_navi">
            <ul id="menu">
                <li><a id="a1" href="#" class="index_on"></a></li>
                <li><a id="a2" href="#" onclick="naviTo(2)" class="school_off"></a></li>
                <li><a id="a3" href="#" onclick="naviTo(3);" class="classinfo_off"></a></li>
                <li><a id="a4" href="#" onclick="naviTo(4);" class="course_off"></a></li>
                <li><a id="a5" href="#" onclick="naviTo(5);" class="assignment_off"></a></li>
                <li><a id="a6" href="#" onclick="naviTo(6);" class="score_off"></a></li>
                <li><a id="a7" href="#" onclick="naviTo(7);" class="notify_off"></a></li>
                <li><a id="a8" href="#" onclick="naviTo(8);" class="report_off"></a></li>
                <li><a id="a9" href="#" onclick="naviTo(9);" class="myself_off"></a></li>
                <li><a id="a10" href="<%=basePath %>user/user_modi_pwd.html" class="password_off"></a></li>
            </ul>
        </div>
    </body>
</html>
