<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.CourseSchedule"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
CourseSchedule courseSchedule = (CourseSchedule)request.getAttribute("courseSchedule");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>家校通</title>
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
</head>
<body>
	<!--主要区域开始-->
        <div id="detail_main">            
            <form action="" method="" >
                <!--必填项-->
                <div class="text_info clearfix"><span>序号：</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getId() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>课程名称：</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getCourseInfo().getCourseName() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>学期：</span></div>
                <div class="input_info"><input type="text" readonly class="readonly" value="<%=courseSchedule.getTermInfo().getTermName() %>" /></div>
                <div class="text_info clearfix"><span>班级：</span></div>
                <div class="input_info"><input type="text" readonly class="readonly" value="<%=courseSchedule.getClassInfo().getClassName() %>" /></div>
                <div class="text_info clearfix"><span>年级：</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getClassInfo().getClassGrade().getGradeName() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>任课教师：</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getTeacherInfo().getTeacherName() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>班级人数：</span></div>
                <div class="input_info"><input type="text" value="<%= %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>作业次数：</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getAssignmentCount() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>测验次数：</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getTestCount() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>课程备注：</span></div>
                <div class="input_info_high">
                    <textarea class="width300 height70 readonly" readonly><%=courseSchedule.getScheduleMemo() %></textarea>
                </div>
                <!--操作按钮-->
                <div class="button_info clearfix">
                    <input type="button" value="返回" class="btn_save" onclick="history.back(-1);" />
                </div>
            </form>
        </div>
</body>
</html>