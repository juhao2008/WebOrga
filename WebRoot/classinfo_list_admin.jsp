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
            function deleteRole() {
                var r = window.confirm("ȷ��Ҫɾ���˽�ɫ��");
                document.getElementById("operate_result_info").style.display = "block";
            }
            /*��ת����ѯ�����ĳҳ*/
            function GoToPage(currentPage,totalPage) {
                if(currentPage==0) return;
                if(currentPage>totalPage) return;
                document.forms[0].currentPage.value = currentPage;
                document.forms[0].submit();
            }

            function changepage(totalPage){
                var pageValue=document.forms[0].pageValue.value;
                if(pageValue>totalPage) {
                    alert('�������ҳ�볬������ҳ��!');
                    return ;
                }
                document.forms[0].currentPage.value = pageValue;
                document.forms[0].submit();
            }
        </script>
    </head>
    <body>
        <!--��Ҫ����ʼ-->
        <!-- <div id="main"> -->
            <form action="<%=basePath %>/News/News_QueryNews.action" name="newsQueryForm" method="post">
            	<input type=hidden name=currentPage value="1" />
                <!--��ѯ-->
                <div class="search_add">
                    <input type="button" value="����" class="btn_add" onclick="location.href='role_add.html';" />
                </div>  
                <!--ɾ���Ĳ�����ʾ-->
                <div id="operate_result_info" class="operate_success">
                    <img src="<%=basePath %>images/close.png" onclick="this.parentNode.style.display='none';" />
                    ɾ���ɹ���
                </div> <!--ɾ�����󣡸ý�ɫ��ʹ�ã�����ɾ����-->
                <!--���������ñ��չʾ����-->     
                
                <!--��ҳ-->
                <div id="pages">
                	<span>����<%=recordNumber %>����¼����ǰ�� <%=currentPage %>/<%=totalPage %> ҳ&nbsp;&nbsp;</span>
        	        <span><a onclick="GoToPage(1,<%=totalPage %>);" class="link">��ҳ</a></span>
                    <span><a onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);">��һҳ</a></span>
                    <span><a onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);">��һҳ</a></span>
                    <span><a onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);">βҳ</a></span>
                    <span>ת����&nbsp;<input name="pageValue" type="text" class="goto" />&nbsp;ҳ</span>
                    <span><button class="goto" onclick="changepage(<%=totalPage %>);">GO</button></span>
                </div>
            </form>
        <!-- </div> -->
    </body>
</html>
