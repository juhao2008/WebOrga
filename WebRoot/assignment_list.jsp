<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.Assignment" %>
<%@ page import="com.orga.domain.ClassInfo" %>
<%@ page import="com.orga.domain.CourseSchedule" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<Assignment> assignmentList = (List<Assignment>)request.getAttribute("assignmentList");
int currentPage = (Integer)request.getAttribute("currentPage"); //��ǰҳ
int totalPage = (Integer)request.getAttribute("totalPage");  //һ������ҳ
int recordNumber = (Integer)request.getAttribute("recordNumber");  //һ�����ټ�¼
//�����ѯ�ֶ�
String assignmentName = (String)request.getAttribute("assignmentName");
List<CourseSchedule> courseScheduleList = (List<CourseSchedule>)request.getAttribute("courseScheduleList");
int courseScheduleId = (Integer)request.getAttribute("courseScheduleId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>��Уͨ</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" /> 
        <script language="javascript" type="text/javascript">
        	function GoToAddView() {
        		parent.document.getElementById("center_frame").src = '<%=basePath %>Assignment/Assignment_AddView.action';
        	}
        	
            function DeleteRecord() {
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
                var pageValue=document.newsQueryForm.pageValue.value;
                if(pageValue>totalPage) {
                    alert('�������ҳ�볬������ҳ��!');
                    return ;
                }
                document.newsQueryForm.currentPage.value = pageValue;
                document.newsQueryForm.submit();
            }
        </script>
    </head>
    <body>
        <!--��Ҫ����ʼ-->
        <!-- <div id="main"> -->
            <form action="<%=basePath %>Assignment/Assignment_QueryAssignment.action" name="QueryAssignmentForm" method="post">
            	<input type=hidden name=currentPage value="1" />
                <!--��ѯ-->
                <div class="search_add">
                    <div>ѡ��γ���Ϣ
                    	<select name="courseSchedule.id" id="selModules" class="select_search">
                            <option value="">ȫ��</option>
                            <%
                            if(courseScheduleList != null) {
                            	for(CourseSchedule courseSchedule : courseScheduleList) {
                            %>
                            <option value="<%=courseSchedule.getId()%>" <%=courseSchedule.getId()==courseScheduleId?"selected":""  %>>
                            <%=courseSchedule.getCourseInfo().getCourseName() 
                            	+ " " + courseSchedule.getTermInfo().getTermName()
                            	+ " " + courseSchedule.getClassInfo().getClassName()%>
                            </option>
                            <% } } %>
                        </select>
                    </div>
                    <div>��ҵ���⣺<input type="text" name="assignmentName" value="<%=assignmentName %>" class="text_search width200" /></div>
                    <div><input type=submit value="����" class="btn_search"/></div>
                    <input type="button" value="����" class="btn_add" onclick="GoToAddView();" />
                </div>
                <!--ɾ���Ĳ�����ʾ-->
                <div id="operate_result_info" class="operate_success">
                    <img src="<%=basePath %>images/close.png" onclick="this.parentNode.style.display='none';" />
                    ɾ���ɹ���
                </div> <!--ɾ�����󣡸ý�ɫ��ʹ�ã�����ɾ����-->
                <!--���������ñ��չʾ����-->     
                <div id="data">                      
                    <table id="datalist">
                        <tr>                            
                            <th>��ҵ���</th>
                            <th>��ҵ����</th>
                            <th>��������</th>
                            <th>�γ�</th>
                            <th>ѧ��</th>
                            <th>�༶</th>
                            <th class="width300">��ҵ����</th>
                            <th class="td_modi">����</th>
                        </tr>
                        <%
		           		/*������ʼ���*/
		            	int startIndex = (currentPage -1) * 10;
		            	/*������¼*/
		            	for(int i=0;i<assignmentList.size();i++) {
		            		int currentIndex = startIndex + i + 1; //��ǰ��¼�����
		            		Assignment assignment = assignmentList.get(i); //��ȡ��News����
             			%>                    
                        <tr>
                            <td><%=assignment.getAssignmentId() %></td>
                            <td><%=assignment.getAssignmentName() %></td>
                            <td><%=assignment.getAssignmentDate() %></td>
                            <td><%=assignment.getCourseSchedule().getCourseInfo().getCourseName() %></td>
                            <td><%=assignment.getCourseSchedule().getTermInfo().getTermName() %></td>
                            <td><%=assignment.getCourseSchedule().getClassInfo().getClassName() %></td>
                            <td><%=assignment.getAssignmentContent() %></td>
                            <td>
                                <input type="button" value="�޸�" class="btn_modify" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Assignment/Assignment_ModifyView.action?assignmentId=<%=assignment.getAssignmentId() %>';" />
                                <input type="button" value="ɾ��" class="btn_delete" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Assignment/Assignment_DeleteAssignment.action?assignmentId=<%=assignment.getAssignmentId() %>';"  />
                            </td>
                        </tr>
                        <%	} %>
                    </table>
                </div> 
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
