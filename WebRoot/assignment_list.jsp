<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.orga.domain.Assignment" %>
<%@ page import="com.orga.domain.ClassInfo" %>
<%@ page import="com.orga.domain.CourseSchedule" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<Assignment> assignmentList = (List<Assignment>)request.getAttribute("assignmentList");
int currentPage = (Integer)request.getAttribute("currentPage"); //当前页
int totalPage = (Integer)request.getAttribute("totalPage");  //一共多少页
int recordNumber = (Integer)request.getAttribute("recordNumber");  //一共多少记录
//界面查询字段
String assignmentName = (String)request.getAttribute("assignmentName");
List<CourseSchedule> courseScheduleList = (List<CourseSchedule>)request.getAttribute("courseScheduleList");
int courseScheduleId = (Integer)request.getAttribute("courseScheduleId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <title>家校通</title>
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>styles/global_color_iframe.css" /> 
        <script language="javascript" type="text/javascript">
        	function GoToAddView() {
        		parent.document.getElementById("center_frame").src = '<%=basePath %>Assignment/Assignment_AddView.action';
        	}
        	
            function DeleteRecord() {
                var r = window.confirm("确定要删除此角色吗？");
                document.getElementById("operate_result_info").style.display = "block";
            }
            /*跳转到查询结果的某页*/
            function GoToPage(currentPage,totalPage) {
                if(currentPage==0) return;
                if(currentPage>totalPage) return;
                document.forms[0].currentPage.value = currentPage;
                document.forms[0].submit();
            }

            function changepage(totalPage){
                var pageValue=document.newsQueryForm.pageValue.value;
                if(pageValue>totalPage) {
                    alert('你输入的页码超出了总页数!');
                    return ;
                }
                document.newsQueryForm.currentPage.value = pageValue;
                document.newsQueryForm.submit();
            }
        </script>
    </head>
    <body>
        <!--主要区域开始-->
        <!-- <div id="main"> -->
            <form action="<%=basePath %>Assignment/Assignment_QueryAssignment.action" name="QueryAssignmentForm" method="post">
            	<input type=hidden name=currentPage value="1" />
                <!--查询-->
                <div class="search_add">
                    <div>选择课程信息
                    	<select name="courseSchedule.id" id="selModules" class="select_search">
                            <option value="">全部</option>
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
                    <div>作业标题：<input type="text" name="assignmentName" value="<%=assignmentName %>" class="text_search width200" /></div>
                    <div><input type=submit value="搜索" class="btn_search"/></div>
                    <input type="button" value="增加" class="btn_add" onclick="GoToAddView();" />
                </div>
                <!--删除的操作提示-->
                <div id="operate_result_info" class="operate_success">
                    <img src="<%=basePath %>images/close.png" onclick="this.parentNode.style.display='none';" />
                    删除成功！
                </div> <!--删除错误！该角色被使用，不能删除。-->
                <!--数据区域：用表格展示数据-->     
                <div id="data">                      
                    <table id="datalist">
                        <tr>                            
                            <th>作业编号</th>
                            <th>作业标题</th>
                            <th>发布日期</th>
                            <th>课程</th>
                            <th>学期</th>
                            <th>班级</th>
                            <th class="width300">作业内容</th>
                            <th class="td_modi">操作</th>
                        </tr>
                        <%
		           		/*计算起始序号*/
		            	int startIndex = (currentPage -1) * 10;
		            	/*遍历记录*/
		            	for(int i=0;i<assignmentList.size();i++) {
		            		int currentIndex = startIndex + i + 1; //当前记录的序号
		            		Assignment assignment = assignmentList.get(i); //获取到News对象
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
                                <input type="button" value="修改" class="btn_modify" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Assignment/Assignment_ModifyView.action?assignmentId=<%=assignment.getAssignmentId() %>';" />
                                <input type="button" value="删除" class="btn_delete" onclick="parent.document.getElementById('center_frame').src='<%=basePath %>Assignment/Assignment_DeleteAssignment.action?assignmentId=<%=assignment.getAssignmentId() %>';"  />
                            </td>
                        </tr>
                        <%	} %>
                    </table>
                </div> 
                <!--分页-->
                <div id="pages">
                	<span>共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页&nbsp;&nbsp;</span>
        	        <span><a onclick="GoToPage(1,<%=totalPage %>);" class="link">首页</a></span>
                    <span><a onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);">上一页</a></span>
                    <span><a onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);">下一页</a></span>
                    <span><a onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);">尾页</a></span>
                    <span>转到第&nbsp;<input name="pageValue" type="text" class="goto" />&nbsp;页</span>
                    <span><button class="goto" onclick="changepage(<%=totalPage %>);">GO</button></span>
                </div>
            </form>
        <!-- </div> -->
    </body>
</html>
