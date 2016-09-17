package com.cpkf.notpad.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.cpkf.notpad.dao.BaseDao;
import com.cpkf.notpad.dao.IAccountDao;
import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.vo.Page;
import com.sun.istack.FinalArrayList;
@Repository
public class AccountDaoImpl extends BaseDao implements IAccountDao {

	public Account getAccountByEmailAndPwd(String email, String pwd) {
		List list = getHibernateTemplate().find(
				"from Account where email=? and passWord=?",new String[]{email,pwd});
		if(list != null && list.size() > 0){
			return (Account) list.get(0);
		}
		return null;
	}

	public Account getAccountByEmail(String email) {
		List list = getHibernateTemplate().find("from Account where email=?",email);
		if(list != null && list.size() > 0){
			return (Account) list.get(0);
		}
		return null;
	}

	public void addAccount(Account account) {
		getHibernateTemplate().saveOrUpdate(account);
	}

	public List<Account> getAllAccounts() {
		return getHibernateTemplate().find("from Account");
	}

	
	public Page getAllAccountsForPage(Page page) {
		final String hql = "from Account where status = true order by user.registTime desc";
		//从0开始
		final int offset = (page.getCurrentPage() - 1) * page.getPageSize();
		final int length = page.getPageSize();
		int count = getHibernateTemplate().find(hql).size();
		List<Account> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			
			public Object doInHibernate(Session session) 
				throws HibernateException,SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();
			}			
		});
		page.setCount(count);
		page.setList(list);
		return page;
	}

	public Page getQueryAccountsForPage(Page page) {
		/*
		 * 中文条件查询，最好使用?占位符并使用query.setParameter方式
		 * jdbc url必须设置编码jdbc:mysql://localhost:3306/notepad?useUnicode=true&characterEncoding=utf8
		 * */
		final String hql = "from Account where status = true and(email like ? or user.userName like ?) order by user.registTime desc";
		final String[] parameter = {new String("%" + page.getKeyWord() + "%"),new String("%" + page.getKeyWord() + "%")};
		final int offset = (page.getCurrentPage() - 1) * page.getPageSize();
		final int length = page.getPageSize();
		int count = getHibernateTemplate().find(hql,parameter).size();
		List<Account> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) 
				throws HibernateException,SQLException {
				Query query = session.createQuery(hql);
				query.setParameter(0, parameter[0]);
				query.setParameter(1, parameter[1]);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();
			}			
		});
		/*
		 * 使用criteria分页查询
		 * 如果一个用户有多个角色，那么查询该用户时，会返回多个该用户对象
		 * 未找到合适办法
		 * */
//		List<Account> list = getHibernateTemplate().executeFind(new HibernateCallback() {
//			public Object doInHibernate(Session session) 
//				throws HibernateException, SQLException {
//				Criteria criteria = session.createCriteria(Account.class);
//				//设置关联属性
//				criteria.createCriteria("user", "user");
//				//设置模糊查询
//				if(keyWord.length() > 0){
//					criteria.add(
//						Restrictions.or(
//							Restrictions.like("email", "%" + keyWord + "%"),
//							Restrictions.like("user.userName", "%" + keyWord + "%")
//						)
//					);
//				}
//				//设置分页起始
//				criteria.setFirstResult(offset);
//				criteria.setMaxResults(length);
//				//设置排序
//				criteria.addOrder(Order.desc("user.registTime"));
//				return removeRepeatForAccount(criteria.list());
//			}
//		});
		page.setCount(count);
		page.setList(list);
		return page;
	}
	/* 
	 * method name   : removeRepeat
	 * description   : 去除集合中重复的元素
	 * @author       : Jiang.Hu
	 * @param        : @param list
	 * @return       : List
	 * Create at     : Jun 7, 2011 2:16:28 PM
	 * modified      : 
	 */      
	public List<Account> removeRepeatForAccount(List<Account> list){
		for(int i = 0;i < list.size();i ++){
			for(int j = i + 1;j < list.size();j ++){
				if(((Account)list.get(i)).getAccountId() == ((Account)list.get(j)).getAccountId()){
					list.remove(j);
				}
			}
		}
		return list;
	}

	public Account getAccountById(Long accountId) {
		List<Account> list = getHibernateTemplate().find("from Account where accountId = ?",accountId);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
