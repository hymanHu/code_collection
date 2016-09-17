package com.cpkf.notpad.server;

import java.util.List;

import com.cpkf.notpad.entity.Role;

/**  
 * Filename:    IRoleService.java
 * Description: role Service
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 30, 2011 11:07:07 AM
 * modified:    
 */
public interface IRoleService {
	/* 
	 * method name   : getAllRoles
	 * description   : 得到所有角色
	 * @author       : Jiang.Hu
	 * @return       : List
	 * Create at     : May 31, 2011 2:35:42 PM
	 * modified      : 
	 */      
	List getAllRoles();
	/* 
	 * method name   : getRoleByRoleName
	 * description   : 根据角色名查找角色
	 * @author       : Jiang.Hu
	 * @param        : @param roleName
	 * @return       : Role
	 * Create at     : May 31, 2011 2:35:38 PM
	 * modified      : 
	 */      
	Role getRoleByRoleName(String roleName);
	/* 
	 * method name   : addRole
	 * description   : 增加role
	 * @author       : Jiang.Hu
	 * @param        : @param role
	 * @return       : void
	 * Create at     : May 31, 2011 2:40:47 PM
	 * modified      : 
	 */      
	void addRole(Role role);
}
