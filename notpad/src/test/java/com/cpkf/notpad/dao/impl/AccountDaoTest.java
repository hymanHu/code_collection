package com.cpkf.notpad.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.cpkf.notpad.dao.IAccountDao;
import com.cpkf.notpad.entity.Account;

public class AccountDaoTest {
	private IAccountDao accountDao;
	private HibernateTemplate hibernateTemplate;
	@Before
	public void setUp(){
		accountDao = new AccountDaoImpl();
		hibernateTemplate = EasyMock.createMock(HibernateTemplate.class);
		ReflectionTestUtils.setField(accountDao, "hibernateTemplate", hibernateTemplate);
	}
	@After
	public void tearDown(){
		accountDao = null;
		hibernateTemplate = null;
	}
	@Test
	public void testGetAccountByEmailAndPwd(){
		Account account1 = new Account();
		account1.setEmail("hj@163.com");
		account1.setPassWord("1111");
		List<Account> list = new ArrayList<Account>();
		list.add(account1);
		EasyMock.expect(hibernateTemplate.find(
				"from Account where email=? and passWord=?",
				new String[]{"hj@163.com","1111"})).andReturn(list).anyTimes();
		EasyMock.replay(hibernateTemplate);
		Account account = accountDao.getAccountByEmailAndPwd("hj@163.com", "1111");
		EasyMock.verify(hibernateTemplate);
		Assert.assertEquals("hj@163.com", account.getEmail());
	}
	@Test
	public void testGetAccountByEmail(){
		Account account1 = new Account();
		account1.setEmail("hj@163.com");
		account1.setPassWord("1111");
		List<Account> list = new ArrayList<Account>();
		list.add(account1);
		EasyMock.expect(hibernateTemplate.find(
				"from Account where email=?","hj@163.com")).
				andReturn(list).anyTimes();
		EasyMock.replay(hibernateTemplate);
		Account account = accountDao.getAccountByEmail("hj@163.com");
		EasyMock.verify(hibernateTemplate);
		Assert.assertEquals("hj@163.com", account.getEmail());
	}
}
