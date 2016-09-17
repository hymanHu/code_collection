package com.thornBird.think.notepad.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thornBird.think.notepad.dao.impl.AccountDaoImpl;
import com.thornBird.think.notepad.entity.Account;
import com.thornBird.think.notepad.exception.NotePadException;

// 配置spring测试环境
@RunWith(SpringJUnit4ClassRunner.class)
/*
 * 指定配置文件路径,下面两种方式 locations: 可指定多个文件，用逗号分隔
 * classpath--/src/test/resources(默认)、src/main/webapp(pom下配置的路径，参考pom文件)
 */
@ContextConfiguration(locations = "classpath:/WEB-INF/conf/spring/applicationContext.xml")
public class AccountDaoImplTest {

	@Resource(name = "accountDao")
	public AccountDaoImpl accountDao;
	public Account account;

	@Before
	public void setUp() {
		account = new Account();
		account.setEmail("test@163.com");
		account.setPassword("test");
		account.setAvailable(true);
		account.setCreateTime(new Date());
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testAddAccount() {
		boolean flag = true;
		try {
			accountDao.addAccount(account);
		} catch (NotePadException e) {
			e.printStackTrace();
			flag = false;
		}
		Assert.assertEquals(true, flag);
	}
	
	@Test
	public void testGetAccountByEmailAndPwd() {
		Account result = new Account();
		try {
			result = accountDao.getAccountByEmailAndPwd(account.getEmail(), account.getPassword());
		} catch (NotePadException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(account.getEmail(), result.getEmail());
	}

	@Test
	public void testGetAccountByAccountId() {
		Account result1 = new Account();
		Account result2 = new Account();
		try {
			result1 = accountDao.getAccountByEmailAndPwd(account.getEmail(), account.getPassword());
			result2 = accountDao.getAccountByAccountId(result1.getAccountId());
		} catch (NotePadException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(account.getEmail(), result2.getEmail());
	}
	
	@Test
	public void testUpdatePwdByEmail() {
		boolean flag = true;
		try {
			accountDao.updatePwdByEmail(account.getEmail(), account.getPassword(), "222222");
		} catch (NotePadException e) {
			flag = false;
			e.printStackTrace();
		}
		Assert.assertEquals(true, flag);
	}
	
	@Test
	public void testDeleteAccountByEmail() {
		boolean flag = true;
		try {
			accountDao.deleteAccountByEmail(account.getEmail());
		} catch (NotePadException e) {
			flag = false;
			e.printStackTrace();
		}
		Assert.assertEquals(true, flag);
	}
	
	@Test
	public void testRemoveAccountByEmail() {
		boolean flag = true;
		try {
			accountDao.removeAccountByEmail(account.getEmail());
		} catch (NotePadException e) {
			flag = false;
			e.printStackTrace();
		}
		Assert.assertEquals(true, flag);
	}

}
