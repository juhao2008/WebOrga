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
<title>��Уͨ</title>
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" />
</head>
<body>
	<!--��Ҫ����ʼ-->
        <div id="detail_main">            
            <form action="" method="" >
                <!--������-->
                <div class="text_info clearfix"><span>��ţ�</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getId() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>�γ����ƣ�</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getCourseInfo().getCourseName() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>ѧ�ڣ�</span></div>
                <div class="input_info"><input type="text" readonly class="readonly" value="<%=courseSchedule.getTermInfo().getTermName() %>" /></div>
                <div class="text_info clearfix"><span>�༶��</span></div>
                <div class="input_info"><input type="text" readonly class="readonly" value="<%=courseSchedule.getClassInfo().getClassName() %>" /></div>
                <div class="text_info clearfix"><span>�꼶��</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getClassInfo().getClassGrade().getGradeName() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>�ον�ʦ��</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getTeacherInfo().getTeacherName() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>�༶������</span></div>
                <div class="input_info"><input type="text" value="<%= %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>��ҵ������</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getAssignmentCount() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>���������</span></div>
                <div class="input_info"><input type="text" value="<%=courseSchedule.getTestCount() %>" readonly class="readonly" /></div>
                <div class="text_info clearfix"><span>�γ̱�ע��</span></div>
                <div class="input_info_high">
                    <textarea class="width300 height70 readonly" readonly><%=courseSchedule.getScheduleMemo() %></textarea>
                </div>
                <!--������ť-->
                <div class="button_info clearfix">
                    <input type="button" value="����" class="btn_save" onclick="history.back(-1);" />
                </div>
            </form>
        </div>
</body>
</html>