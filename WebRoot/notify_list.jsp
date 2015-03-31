<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.Notify" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<Notify> notifyList = (List<Notify>)request.getAttribute("notifyList");
int currentPage = (Integer)request.getAttribute("currentPage"); //当前页
int totalPage = (Integer)request.getAttribute("totalPage");  //一共多少页
int recordNumber = (Integer)request.getAttribute("recordNumber");  //一共多少记录
String notifyTitle = (String)request.getAttribute("notifyTitle");
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
    <form action="<%=basePath %>/Notify/Notify_QueryNotify.action" name="notifyQueryForm" method="post">
		<!--查询-->
		<div class="search_add">
			<input type=hidden name=currentPage value="1" />
			<div>
				通知标题：<input type="text" name="notifyTitle" value="<%=notifyTitle %>" class="text_search width200" />
			</div>
			<div>
				<input type=submit value="搜索" class="btn_search" />
			</div>
		</div>
		<div id="data">
			<table id="datalist">
				<tr>
					<th>序号</th>
					<th class="width600">通知标题</th>
					<th>发布日期</th>
					<th class="td_modi"></th>
				</tr>
				<%
					/*计算起始序号*/
					int startIndex = (currentPage - 1) * 10;
					/*遍历记录*/
					for (int i = 0; i < notifyList.size(); i++) {
						int currentIndex = startIndex + i + 1; //当前记录的序号
						Notify notify = notifyList.get(i); //获取到Notify对象
				%>
				<tr>
					<td><%=currentIndex %></td>
					<td><%=notify.getNotifyTitle() %></td>
					<td><%=notify.getNotifyDate() %></td>
					<td><input type="button" value="修改" class="btn_modify" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Notify/Notify_ModifyView.action?notifyId=<%=notify.getNotifyId() %>';" />
                        <input type="button" value="删除" class="btn_delete" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Notify/Notify_DeleteNotify.action?notifyId=<%=notify.getNotifyId() %>';" />
                    </td>
				</tr>
				<%	} %>
			</table>
		</div>
		<div id="pages">
			<span>共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %>
				页&nbsp;&nbsp;
			</span> <span><a onclick="GoToPage(1,<%=totalPage %>);" class="link">首页</a></span>
			<span><a onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);">上一页</a></span> <span><a
				onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);">下一页</a></span> <span><a
				onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);">尾页</a></span> <span>转到第&nbsp;<input
				name="pageValue" type="text" class="goto" />&nbsp;页</span>
			<span><button class="goto" onclick="changepage(<%=totalPage %>);">GO</button></span>
		</div>
	</form>
    </body>
</html>