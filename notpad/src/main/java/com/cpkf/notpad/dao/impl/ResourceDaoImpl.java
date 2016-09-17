package com.cpkf.notpad.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cpkf.notpad.dao.BaseDao;
import com.cpkf.notpad.dao.IResourceDao;
@Repository
public class ResourceDaoImpl extends BaseDao implements IResourceDao {

	public List getAllResources() {
		return getHibernateTemplate().find("from Resource");
	}

}
