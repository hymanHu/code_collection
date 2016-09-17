package com.cpkf.notpad.server.impl;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.cpkf.notpad.dao.IAccountDao;
import com.cpkf.notpad.dao.impl.AccountDaoImpl;
import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.server.IAccountService;

public class AccountServiceImplTest {
	private IAccountDao accountDao;
	private IAccountService accountService;
	@Before
	public void setUp(){
		accountService = new AccountServiceImpl();
		accountDao = EasyMock.createMock(AccountDaoImpl.class);
		ReflectionTestUtils.setField(accountService, "accountDao", accountDao);
	}
	@After
	public void tearDown(){
		accountDao = null;
		accountService = null;
	}
	@Test
	public void testGetAccountByEmailAndPwd(){
		Account account = new Account();
		account.setEmail("hj@163.com");
		account.setPassWord("1111");
		EasyMock.expect(accountDao.getAccountByEmailAndPwd("hj@163.com", "1111")).andReturn(account).anyTimes();
		EasyMock.replay(accountDao);
		Assert.assertEquals("hj@163.com", accountService.getAccountByEmailAndPwd("hj@163.com", "1111").getEmail());
		EasyMock.verify(accountDao);
	}
	@Test
	public void testGetAccountByEmail(){
		Account account = new Account();
		account.setEmail("hj@163.com");
		account.setPassWord("1111");
		EasyMock.expect(accountDao.getAccountByEmail("hj@163.com")).andReturn(account).anyTimes();
		EasyMock.replay(accountDao);
		Assert.assertEquals("hj@163.com", accountDao.getAccountByEmail("hj@163.com").getEmail());
		EasyMock.verify(accountDao);
	}
}
