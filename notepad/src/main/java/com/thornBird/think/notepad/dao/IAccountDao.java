package com.thornBird.think.notepad.dao;

import com.thornBird.think.notepad.entity.Account;
import com.thornBird.think.notepad.exception.NotePadException;

public interface IAccountDao {

	public Account getAccountByEmailAndPwd(String email, String pwd) throws NotePadException;

	public Account getAccountByAccountId(long accountId) throws NotePadException;
	
	public Account getAccountByEmail(String email) throws NotePadException;

	public Account addAccount(Account account) throws NotePadException;

	public Account updatePwdByEmail(String email, String oldPwd, String newPwd) throws NotePadException;

	public void deleteAccountByEmail(String email) throws NotePadException;
	
	public void removeAccountByEmail(String email) throws NotePadException;
}
