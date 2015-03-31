<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.ClassInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<ClassInfo> classInfoList = (List<ClassInfo>)request.getAttribute("classInfoList");
int currentPage = (Integer)request.getAttribute("currentPage"); //��ǰҳ
int totalPage = (Integer)request.getAttribute("totalPage");  //һ������ҳ
int recordNumber = (Integer)request.getAttribute("recordNumber");  //һ�����ټ�¼
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>��Уͨ</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" /> 
        <script language="javascript" type="text/javascript">
	        function ChangeTab(e,ulObj) {                
	            var obj = e.srcElement || e.target;
	            if (obj.nodeName == "A") {
	                var links = ulObj.getElementsByTagName("a");
	                for (var i = 0; i < links.length; i++) {
	                    if (links[i].innerHTML == obj.innerHTML) {
	                        links[i].className = "tab_on";
	                    } else {
	                        links[i].className = "tab_out";
	                    }
	                }
	            }
	        }
	        
	        function QueryAll() {
	        	document.getElementById('table_frame').src='<%=basePath %>ClassInfo/ClassInfo_QueryClassInfoTable.action';
	        }
	        
        </script>
    </head>
    <body class="transparent">
        <!--��Ҫ����ʼ-->
           <div class="tabs">
    	        <ul onclick="ChangeTab(event,this);">
    	        <li><a href="#" class="tab_on" title="���а༶���" onclick="QueryAll();">���а༶</a></li>
    	        <% for(int i=0; i<classInfoList.size(); i++) {
    	        	ClassInfo classInfo = classInfoList.get(i);
    	        %>
        	        <li><a href="#" class="tab_out" title="�鿴ÿ�����" onclick="document.getElementById('table_frame').src='<%=basePath%>/Student/Student_QueryClassStudent.action?studentClass.classNumber=<%=classInfo.getClassNumber()%>';"><%=classInfo.getClassGrade().getGradeName() + classInfo.getClassName() %></a></li>
                <% } %>
                </ul>
            </div> 
            <iframe src="<%=basePath %>ClassInfo/ClassInfo_QueryClassInfoTable.action" id="table_frame" scrolling="no" marginwidth=0 
        	marginheight=0 frameborder=0 vspace=0 hspace=0></iframe>
    </body>
</html>
