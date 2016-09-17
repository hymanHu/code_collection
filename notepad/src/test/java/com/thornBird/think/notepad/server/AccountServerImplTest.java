package com.thornBird.think.notepad.server;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thornBird.think.notepad.entity.Account;
import com.thornBird.think.notepad.exception.NotePadException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/WEB-INF/conf/spring/applicationContext.xml")
public class AccountServerImplTest {

	@Resource(name = "accountServer")
	private IAccountServer accountServer;
	private Account account;

	@Before
	public void setUp() {
		account = new Account("test@test.com", "test");
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testSignUp() {
		Account result = null;
		try {
			result = accountServer.signUp(account.getEmail(), account.getPassword());
		} catch (NotePadException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(account.getEmail(), result.getEmail());
	}

	@Test
	public void testSignIn() {
		Account result = null;
		try {
			result = accountServer.signIn(account.getEmail(), account.getPassword());
		} catch (NotePadException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(account.getEmail(), result.getEmail());
	}

	@Test
	public void testDeleteAccountByEmail() {
		boolean flag = true;
		try {
			accountServer.deleteAccountByEmail(account.getEmail());
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
			accountServer.removeAccountByEmail(account.getEmail());
		} catch (NotePadException e) {
			flag = false;
			e.printStackTrace();
		}
		Assert.assertEquals(true, flag);
	}
}
