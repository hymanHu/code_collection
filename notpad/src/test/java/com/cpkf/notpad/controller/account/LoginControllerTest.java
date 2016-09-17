package com.cpkf.notpad.controller.account;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.server.IAccountService;
import com.cpkf.notpad.server.impl.AccountServiceImpl;

public class LoginControllerTest {
	private IAccountService accountService;
	private AccountController loginController;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	@Before
	public void setUp(){
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		loginController = new AccountController();
		accountService = EasyMock.createMock(AccountServiceImpl.class);
		ReflectionTestUtils.setField(loginController, "accountService", accountService);
	}
	@After
	public void tearDown(){
		accountService = null;
		loginController = null;
		request = null;
		response = null;
	}
	@Test
	public void testLogin(){
		request.setMethod("POST");
		request.setRequestURI("/login.do");
		request.setParameter("email", "hj@163.com");
		request.setParameter("passWord", "1111");
		Account account = new Account();
		account.setEmail("hj@163.com");
		account.setPassWord("B59C67BF196A4758191E42F76670CEBA");
		EasyMock.expect(accountService.getAccountByEmailAndPwd("hj@163.com", "B59C67BF196A4758191E42F76670CEBA")).andReturn(account).anyTimes();
		EasyMock.replay(accountService);
		ModelAndView modelAndView = loginController.login(request, response);
		EasyMock.verify(accountService);
		System.out.println(modelAndView.getViewName());
		Assert.assertNotNull(modelAndView.getViewName());
	}
}
