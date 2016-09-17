package com.cpkf.notpad.server;

import java.util.List;

import com.cpkf.notpad.entity.Menu;
import com.cpkf.notpad.vo.MenuVo;

/**  
 * Filename:    IMenuService.java
 * Description: MenuService
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   Jun 2, 2011 3:14:59 PM
 * modified:    
 */
public interface IMenuService {
	/* 
	 * method name   : getAllMenus
	 * description   : 得到menu列表，不同的用户权限显示不同的menu
	 * @author       : Jiang.Hu
	 * @return       : List<Menu>
	 * Create at     : Jun 2, 2011 3:15:47 PM
	 * modified      : 
	 */      
	List<MenuVo> getShowMenus(String email);
}
