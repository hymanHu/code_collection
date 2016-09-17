package com.cpkf.notpad.server.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Service;

import com.cpkf.notpad.dao.IMenuDao;
import com.cpkf.notpad.entity.Menu;
import com.cpkf.notpad.server.IAccountService;
import com.cpkf.notpad.server.IMenuService;
import com.cpkf.notpad.vo.MenuVo;
@Service
public class MenuServiceImpl implements IMenuService {
	@Autowired
	private IMenuDao menuDao;
	@Autowired
	private IAccountService accountService;
	/* 
	 * method name   : getAllMenus
	 * description   : 显示和用户权限对应的menu
	 * 					首先获得用户资源列表和数据库中所有menu
	 * 					其次进行比对，满足条件的menu根据group的不同放入不同的menuVo中
	 * 					返回menuVo集合
	 * @author       : Jiang.Hu
	 * modified      : leo ,  Jun 2, 2011
	 * @see          : @see com.cpkf.notpad.server.IMenuService#getAllMenus()
	 */    
	public List<MenuVo> getShowMenus(String email) {
		Map<String, MenuVo> menuVoMap = new LinkedHashMap<String, MenuVo>();
		UrlMatcher urlMatcher = new AntUrlPathMatcher();
		List<MenuVo> showMenuList = new ArrayList<MenuVo>();
		List<Menu> menuList = menuDao.getAllMenus();
		List<String> userResourceList = accountService.getResourceListByUserName(email);
		/*
		 * menuList:数据库中所有menu集合
		 * userResourceList：登陆用户对应的资源列表
		 * menuVoMap：不同group对应不同的menuVo
		 * showMenuList：需要返回的menuVoList集合
		 * 首先menuList和userResourceList比对，满足的menu根据不同group放入menuVo中
		 * 在放之前，首先从menuVoMap中查询menuVo是否已经存在，存在直接添加，不存在先创建后添加
		 * 这样不同group对应的menuVo全部在menuVoMap中了
		 * 将menuVoMap中的menuVo对象添加到showMenuList就好
		 * */
		if(menuList != null){
			for(Menu menu : menuList){
				boolean flag = false;
				for(String userResource : userResourceList){
					if(urlMatcher.pathMatchesUrl(userResource, menu.getUrl())){
						flag = true;
						break;
					}
				}
				if(flag){
					MenuVo menuVo = menuVoMap.get(menu.getGroup());
					if(menuVo == null){
						menuVo = new MenuVo();
						menuVo.setGroup(menu.getGroup());
						menuVo.addMeny(menu);
						menuVoMap.put(menu.getGroup(), menuVo);
					} else {
						menuVo.getMenuList().add(menu);
					}
				}
			}
		}
		Iterator<String> iterator = menuVoMap.keySet().iterator();
		while(iterator.hasNext()){
			String group = iterator.next();
			showMenuList.add(menuVoMap.get(group));
		}
		return showMenuList;
	}

}
