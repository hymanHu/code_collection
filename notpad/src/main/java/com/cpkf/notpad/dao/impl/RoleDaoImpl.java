package com.cpkf.notpad.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cpkf.notpad.dao.BaseDao;
import com.cpkf.notpad.dao.IRoleDao;
import com.cpkf.notpad.entity.Role;
@Repository
public class RoleDaoImpl extends BaseDao implements IRoleDao {

	public List getAllRoles() {
		return getHibernateTemplate().find("from Role");
	}

	public Role getRoleByRoleName(String roleName) {
		List<Role> list = getHibernateTemplate().find("from Role where roleName = ?",roleName);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public void addRole(Role role) {
		getHibernateTemplate().saveOrUpdate(role);
	}

}
