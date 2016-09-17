package com.cpkf.notpad.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**  
 * Filename:    BaseDao.java
 * Description: 数据操作基础类，注入hibernateTemplate
 * Company:     
 * @author:     
 * @version:    1.0
 * Create at:   May 10, 2011 2:50:47 PM
 * modified:    
 */
@Repository
public abstract class BaseDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
}
