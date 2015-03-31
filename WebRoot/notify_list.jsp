<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.Notify" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<Notify> notifyList = (List<Notify>)request.getAttribute("notifyList");
int currentPage = (Integer)request.getAttribute("currentPage"); //��ǰҳ
int totalPage = (Integer)request.getAttribute("totalPage");  //һ������ҳ
int recordNumber = (Integer)request.getAttribute("recordNumber");  //һ�����ټ�¼
String notifyTitle = (String)request.getAttribute("notifyTitle");
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
    <form action="<%=basePath %>/Notify/Notify_QueryNotify.action" name="notifyQueryForm" method="post">
		<!--��ѯ-->
		<div class="search_add">
			<input type=hidden name=currentPage value="1" />
			<div>
				֪ͨ���⣺<input type="text" name="notifyTitle" value="<%=notifyTitle %>" class="text_search width200" />
			</div>
			<div>
				<input type=submit value="����" class="btn_search" />
			</div>
		</div>
		<div id="data">
			<table id="datalist">
				<tr>
					<th>���</th>
					<th class="width600">֪ͨ����</th>
					<th>��������</th>
					<th class="td_modi"></th>
				</tr>
				<%
					/*������ʼ���*/
					int startIndex = (currentPage - 1) * 10;
					/*������¼*/
					for (int i = 0; i < notifyList.size(); i++) {
						int currentIndex = startIndex + i + 1; //��ǰ��¼�����
						Notify notify = notifyList.get(i); //��ȡ��Notify����
				%>
				<tr>
					<td><%=currentIndex %></td>
					<td><%=notify.getNotifyTitle() %></td>
					<td><%=notify.getNotifyDate() %></td>
					<td><input type="button" value="�޸�" class="btn_modify" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Notify/Notify_ModifyView.action?notifyId=<%=notify.getNotifyId() %>';" />
                        <input type="button" value="ɾ��" class="btn_delete" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Notify/Notify_DeleteNotify.action?notifyId=<%=notify.getNotifyId() %>';" />
                    </td>
				</tr>
				<%	} %>
			</table>
		</div>
		<div id="pages">
			<span>����<%=recordNumber %>����¼����ǰ�� <%=currentPage %>/<%=totalPage %>
				ҳ&nbsp;&nbsp;
			</span> <span><a onclick="GoToPage(1,<%=totalPage %>);" class="link">��ҳ</a></span>
			<span><a onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);">��һҳ</a></span> <span><a
				onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);">��һҳ</a></span> <span><a
				onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);">βҳ</a></span> <span>ת����&nbsp;<input
				name="pageValue" type="text" class="goto" />&nbsp;ҳ</span>
			<span><button class="goto" onclick="changepage(<%=totalPage %>);">GO</button></span>
		</div>
	</form>
    </body>
</html>