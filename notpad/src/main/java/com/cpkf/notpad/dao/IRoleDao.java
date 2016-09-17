package com.cpkf.notpad.dao;

import java.util.List;

import com.cpkf.notpad.entity.Role;

/**  
 * Filename:    IRoleDao.java
 * Description: roleDao
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 30, 2011 11:03:16 AM
 * modified:    
 */
public interface IRoleDao {
	/* 
	 * method name   : getAllRoles
	 * description   : 得到所有角色
	 * @author       : Jiang.Hu
	 * @return       : List
	 * Create at     : May 30, 2011 11:03:30 AM
	 * modified      : 
	 */      
	List getAllRoles();
	/* 
	 * method name   : getRoleByRoleName
	 * description   : 根据角色名查找角色
	 * @author       : Jiang.Hu
	 * @param        : @param roleName
	 * @return       : Role
	 * Create at     : May 31, 2011 2:29:58 PM
	 * modified      : 
	 */      
	Role getRoleByRoleName(String roleName);
	/* 
	 * method name   : addRole
	 * description   : 增加role
	 * @author       : Jiang.Hu
	 * @param        : @param role
	 * @return       : void
	 * Create at     : May 31, 2011 2:39:27 PM
	 * modified      : 
	 */      
	void addRole(Role role);
}
