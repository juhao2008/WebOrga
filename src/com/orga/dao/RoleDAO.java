package com.orga.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.orga.domain.Role;
import com.orga.utils.HibernateUtil;

public class RoleDAO {

	/*查询学校信息*/
    public ArrayList<Role> QueryAllRoles() { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Role";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            List roleList = q.list();
            return (ArrayList<Role>) roleList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /**
     * 
     * @param roleId
     * @return
     */
    public Role GetRoleById(String roleId) {
    	Session s = null;
        try {
            s = HibernateUtil.getSession();
            Role role = (Role)s.get(Role.class, roleId);
            return role;
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
