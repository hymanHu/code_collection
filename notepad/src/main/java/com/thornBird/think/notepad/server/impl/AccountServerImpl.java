package com.thornBird.think.notepad.server.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thornBird.think.notepad.dao.IAccountDao;
import com.thornBird.think.notepad.entity.Account;
import com.thornBird.think.notepad.exception.NotePadException;
import com.thornBird.think.notepad.server.IAccountServer;
import com.thornBird.think.notepad.util.MD5;

@Service("accountServer")
public class AccountServerImpl implements IAccountServer {
	
	@Resource(name = "accountDao")
	private IAccountDao accountDao;

	public Account signIn(String email, String pwd) throws NotePadException {
		return accountDao.getAccountByEmailAndPwd(email, MD5.getMD5ofStr(pwd));
	}

	public Account signUp(String email, String pwd) throws NotePadException {
		Account account = new Account(email, MD5.getMD5ofStr(pwd));
		return accountDao.addAccount(account);
	}

	public void deleteAccountByEmail(String email) throws NotePadException {
		accountDao.deleteAccountByEmail(email);
	}

	public void removeAccountByEmail(String email) throws NotePadException {
		accountDao.removeAccountByEmail(email);
	}

}
