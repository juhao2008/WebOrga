package com.orga.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.orga.dao.AdminDAO;
import com.orga.dao.LoginRecordDAO;
import com.orga.dao.StudentDAO;
import com.orga.dao.TeacherDAO;
import com.orga.domain.LoginRecord;
import com.orga.domain.Student;
import com.orga.domain.Teacher;
import com.orga.domain.UserInfo;
import com.orga.utils.CommConst;
import com.orga.utils.CommUtil;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 4157468710237778029L;

	private UserInfo loginUserInfo;
	
	private StudentDAO studentDao = new StudentDAO();
	private TeacherDAO teacherDao = new TeacherDAO();
	private LoginRecordDAO loginRecordDAO = new LoginRecordDAO();
	
	public UserInfo getLoginUserInfo() {
		return loginUserInfo;
	}
	public void setLoginUserInfo(UserInfo loginUserInfo) {
		this.loginUserInfo = loginUserInfo;
	}

	/*直接跳转到登陆界面*/
	public String view() {
		ActionContext ctx = ActionContext.getContext();
		
		String userId=(String)ctx.getSession().get("userId");
		if (userId != null) {
			dumpMsg("view, userId=" + userId + ", could jump to teacher or student index.jsp");
		}
		return "login_view";
	}
	 
	
	/* 验证用户登录 */
	public String CheckLogin() {
		dumpMsg(" CheckLogin::" + (loginUserInfo == null));
		
		ActionContext ctx = ActionContext.getContext();
		String userId = (String)ctx.getSession().get("userId");
		if(userId != null) {
			System.out.println("juhao test userId != null");
			return "login_view";
		}
		
		if(loginUserInfo == null) {
			System.out.println("juhao test loginUserInfo == null");
			return "login_view";
		}
		final String roleType = loginUserInfo.getUserRoleType();
		
		if(CommConst.ROLE_R2.equals(roleType)) {
			return checkTeacher();
			
		} else if(CommConst.ROLE_R3.equals(roleType)) {
			return checkTeacher();
			
		} else if(CommConst.ROLE_R4.equals(roleType)) {
			return checkTeacher();
			
		} else if(CommConst.ROLE_R5.equals(roleType)) {
			return checkSuperAdmin();
		}
		System.out.println("juhao test no match role=" + roleType );
		return "login_view";
	}
	
	/**
	private String checkStudent() {
		dumpMsg(" checkStudent start");
		ActionContext ctx = ActionContext.getContext();
		if(!studentDao.CheckStudentUser(loginUserInfo.getUserAccount(), loginUserInfo.getUserPwd())) {
			ctx.put("error",  java.net.URLEncoder.encode(studentDao.getErrMessage()));
			return "error";
		}
		ctx.getSession().put("userId", loginUserInfo.getUserAccount());
		
		Student student = studentDao.GetStudentByStudentNumber(loginUserInfo.getUserAccount());
		
		ctx.getSession().put("userName", student.getStudentName());
		String schoolNumber = student.getStudentClass().getClassSchool().getSchoolNumber();
		ctx.getSession().put("schoolNumber", schoolNumber);
		String schoolName = student.getStudentClass().getClassSchool().getSchoolName();
		ctx.getSession().put("schoolName", schoolName);
		ctx.getSession().put("userRole", CommConst.ROLE_R1);
		
		dumpMsg(" checkStudent end.");
		return "student_view";
	} */

	private void updateLoginRecord(String loginName) {
		LoginRecord loginRecord = loginRecordDAO.GetRecordByName(loginName);
		try {
			if(loginRecord == null) {
				loginRecord = new LoginRecord();
				loginRecord.setLoginName(loginUserInfo.getUserAccount());
				
				loginRecord.setLoginIp(CommUtil.getRemortIP());
				loginRecord.setLoginTime(CommUtil.getCurrentDateTimeStr());
				
				loginRecord.setLoginWhere("PC browser");
				loginRecordDAO.AddLoginRecord(loginRecord);
			} else {
				loginRecord.setLastestLoginTime(loginRecord.getLoginTime());
				loginRecord.setLoginTime(CommUtil.getCurrentDateTimeStr());
				
				loginRecordDAO.UpdateLoginRecord(loginRecord);
			}
			dumpMsg(" updateLoginRecord end.");
		} catch (Exception ex) {
			ex.printStackTrace();
			dumpMsg(" updateLoginRecord error :" + ex.getMessage());
		}
	}
	
	private String checkTeacher() {
		ActionContext ctx = ActionContext.getContext();
		try {
			if(!teacherDao.CheckTeacherUser(loginUserInfo.getUserAccount(), loginUserInfo.getUserPwd())) {
				ctx.put("error",  java.net.URLEncoder.encode(teacherDao.getErrMessage()));
				dumpMsg("checkTeacher account or password error.");
				return "error";
			}
			updateLoginRecord(loginUserInfo.getUserAccount());
			
			ctx.getSession().put(CommConst.USER_ID, loginUserInfo.getUserAccount());
			
			Teacher teacher = teacherDao.GetTeacherByTeacherNumber(loginUserInfo.getUserAccount());
			ctx.getSession().put(CommConst.USER_NAME, teacher.getTeacherName());
			
			String schoolNumber = teacher.getTeacherSchool().getSchoolNumber();
			ctx.getSession().put(CommConst.USER_SCHOOL_NUMBER, schoolNumber);
			
			String schoolNume = teacher.getTeacherSchool().getSchoolName();
			ctx.getSession().put(CommConst.USER_SCHOOL_NAME, schoolNume);
			
			String roleType = teacher.getTeacherRole().getRoleId();
			String roleName = teacher.getTeacherRole().getRoleName();
			ctx.getSession().put(CommConst.USER_ROLE, roleType);
			ctx.getSession().put(CommConst.USER_ROLE_NAME, roleName);
			
			dumpMsg(" checkTeacher end. roleType="+roleType);
			return "teacher_view";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "login_view";
		}
	}
	
	public String CheckAdminRole() {
		return "school_admin_view";
	}

	private String checkSuperAdmin() {
		AdminDAO adminDAO = new AdminDAO();
		ActionContext ctx = ActionContext.getContext();
		
		if (!adminDAO.CheckLogin(loginUserInfo.getUserAccount(), loginUserInfo.getUserPwd())) {
			ctx.put("error",  java.net.URLEncoder.encode(adminDAO.getErrMessage()));
			return "error";
		}
		ctx.getSession().put("userId", loginUserInfo.getUserAccount());
		ctx.getSession().put("userRole", CommConst.ROLE_R3);
		return "super_admin_view";

		/*
		 * ActionContext ctx = ActionContext.getContext();
		 * ctx.getApplication().put("app", "应用范围");//往ServletContext里放入app
		 * ctx.getSession().put("ses", "session范围");//往session里放入ses ctx.put("req",
		 * "request范围");//往request里放入req ctx.put("names", Arrays.asList("老张", "老黎",
		 * "老方")); HttpServletRequest request = ServletActionContext.getRequest();
		 * ServletContext servletContext = ServletActionContext.getServletContext();
		 * request.setAttribute("req", "请求范围属性");
		 * request.getSession().setAttribute("ses", "会话范围属性");
		 * servletContext.setAttribute("app", "应用范围属性"); // HttpServletResponse
		 * response = ServletActionContext.getResponse();
		 */
	}
	
	public String QuerySelfInfo() {
		ActionContext ctx = ActionContext.getContext();
		String userId = (String)ctx.getSession().get(CommConst.USER_ID);
		Teacher teacher = teacherDao.GetTeacherByTeacherNumber(userId);
		ctx.put("teacher", teacher);
		return "self_center";
	}
	
	public void dumpMsg(String msg) {
		System.out.println(CommUtil.getCurrentDateTimeStr() + " [LoginAction] " + msg);
	}
	
	public static void main(String[] args) {
		String value = CommUtil.getCurrentDateTime().toLocaleString();
		System.out.println(value);
	}
	

}
