package com.cpkf.notpad.dao;

import java.util.List;

import com.cpkf.notpad.entity.Menu;

/**  
 * Filename:    IMenuDao.java
 * Description: menuDao
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   Jun 2, 2011 3:08:28 PM
 * modified:    
 */
public interface IMenuDao {
	/* 
	 * method name   : getAllMenu
	 * description   : 得到所有menu
	 * @author       : Jiang.Hu
	 * @return       : List
	 * Create at     : Jun 2, 2011 3:08:44 PM
	 * modified      : 
	 */      
	List<Menu> getAllMenus();
}
