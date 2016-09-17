package com.cpkf.notpad.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cpkf.notpad.entity.Menu;

/**  
 * Filename:    MenuVo.java
 * Description: menuVo对象，不同的menu归于不同的group中
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   Jun 3, 2011 9:57:25 AM
 * modified:    
 */
public class MenuVo {
	private String group;
	private List<Menu> menuList;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	public void addMeny(Menu menu){
		if(this.menuList != null){
			this.menuList.add(menu);
		} else {
			this.menuList = new ArrayList<Menu>();
			this.menuList.add(menu);
		}
	}
}
