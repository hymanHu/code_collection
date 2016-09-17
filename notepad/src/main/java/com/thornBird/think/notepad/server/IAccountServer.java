package com.thornBird.think.notepad.server;

import com.thornBird.think.notepad.entity.Account;
import com.thornBird.think.notepad.exception.NotePadException;

public interface IAccountServer {

	public Account signIn(String email, String pwd) throws NotePadException;
	
	public Account signUp(String email, String pwd) throws NotePadException;
	
	public void deleteAccountByEmail(String email) throws NotePadException;
	
	public void removeAccountByEmail(String email) throws NotePadException;
	

}
