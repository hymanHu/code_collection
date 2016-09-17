package com.cpkf.notpad.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cpkf.notpad.dao.BaseDao;
import com.cpkf.notpad.dao.IMenuDao;
import com.cpkf.notpad.entity.Menu;
@Repository
public class MenuDaoImpl extends BaseDao implements IMenuDao {

	public List<Menu> getAllMenus() {
		return getHibernateTemplate().find("from Menu");
	}

}
