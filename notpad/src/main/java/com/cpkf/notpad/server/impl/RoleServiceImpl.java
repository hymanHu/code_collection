package com.cpkf.notpad.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpkf.notpad.dao.IRoleDao;
import com.cpkf.notpad.entity.Role;
import com.cpkf.notpad.server.IRoleService;
@Service
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private IRoleDao roleDao;
	public List getAllRoles() {
		return roleDao.getAllRoles();
	}
	public Role getRoleByRoleName(String roleName) {
		return roleDao.getRoleByRoleName(roleName);
	}
	public void addRole(Role role) {
		roleDao.addRole(role);		
	}

}
