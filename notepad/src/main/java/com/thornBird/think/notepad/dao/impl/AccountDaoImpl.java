package com.thornBird.think.notepad.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.thornBird.think.notepad.common.ResultCode;
import com.thornBird.think.notepad.dao.IAccountDao;
import com.thornBird.think.notepad.entity.Account;
import com.thornBird.think.notepad.exception.NotePadException;

/**
 * sql语句from跟的是实体类名，最好写全路径
 * 
 * @author Hyman
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public Account getAccountByEmailAndPwd(String email, String pwd) throws NotePadException {
		@SuppressWarnings("unchecked")
		List<Account> list = hibernateTemplate.find(
				"FROM com.thornBird.think.notepad.entity.Account WHERE email=? AND password=? AND available=TRUE",
				new Object[] { email, pwd });
		if (list != null && list.size() > 0) {
			return (Account) list.get(0);
		} else {
			throw new NotePadException(ResultCode.AccountNotFoundOrPwdError.getCode(),
					ResultCode.AccountNotFoundOrPwdError.getMessage(), String.format("%s%s", "email: ", email));
		}
	}

	public Account getAccountByAccountId(long accountId) throws NotePadException {
		@SuppressWarnings("unchecked")
		List<Account> list = hibernateTemplate.find(
				"FROM com.thornBird.think.notepad.entity.Account WHERE account_id=? AND available=TRUE",
				new Object[] { accountId });
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			throw new NotePadException(ResultCode.AccountNotFound.getCode(), ResultCode.AccountNotFound.getMessage(),
					String.format("%s%d", "accountId: ", accountId));
		}
	}

	public Account getAccountByEmail(String email) throws NotePadException {
		@SuppressWarnings({ "unchecked" })
		List<Account> list = hibernateTemplate.find(
				"FROM com.thornBird.think.notepad.entity.Account WHERE email=?", new Object[] { email });
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			throw new NotePadException(ResultCode.AccountNotFound.getCode(), ResultCode.AccountNotFound.getMessage(),
					String.format("%s%s", "email: ", email));
		}
	}

	// 返回account已被自动赋值id
	public Account addAccount(Account account) throws NotePadException {
		@SuppressWarnings({ "unchecked" })
		List<Account> list = hibernateTemplate.find(
				"FROM com.thornBird.think.notepad.entity.Account WHERE email=?", new Object[] { account.getEmail() });
		if (list == null || list.size() == 0) {
			hibernateTemplate.save(account);
		} else {
			throw new NotePadException(ResultCode.AccountAlreadyExist.getCode(),
					ResultCode.AccountAlreadyExist.getMessage(), String.format("%s%s", "email: ", account.getEmail()));
		}
		return account;
	}

	public Account updatePwdByEmail(String email, String oldPwd, String newPwd) throws NotePadException {
		Account account = getAccountByEmailAndPwd(email, oldPwd);
		if (account != null) {
			account.setPassword(newPwd);
			hibernateTemplate.update(account);
		} else {
			throw new NotePadException(ResultCode.AccountNotFoundOrPwdError.getCode(),
					ResultCode.AccountNotFoundOrPwdError.getMessage(), String.format("%s%s", "email:", email));
		}
		return account;
	}

	public void deleteAccountByEmail(String email) throws NotePadException {
		@SuppressWarnings({ "unchecked" })
		List<Account> list = hibernateTemplate.find(
				"FROM com.thornBird.think.notepad.entity.Account WHERE email=?", new Object[] { email });
		if (list != null && list.size() > 0) {
			Account temp = list.get(0);
			temp.setAvailable(false);
			hibernateTemplate.update(temp);
		} else {
			throw new NotePadException(ResultCode.AccountNotFound.getCode(), ResultCode.AccountNotFound.getMessage(),
					String.format("%s%s", "email:", email));
		}
	}
	
	public void removeAccountByEmail(String email) throws NotePadException {
		@SuppressWarnings({ "unchecked" })
		List<Account> list = hibernateTemplate.find(
				"FROM com.thornBird.think.notepad.entity.Account WHERE email=?", new Object[] { email });
		if (list != null && list.size() > 0) {
			hibernateTemplate.delete(list.get(0));
		} else {
			throw new NotePadException(ResultCode.AccountNotFound.getCode(), ResultCode.AccountNotFound.getMessage(),
					String.format("%s%s", "email:", email));
		}
	}

	public static void main(String[] args) throws NotePadException {
		// 加载配置文件时，applicationContext.xml里引用的properties文件也要加上'/src/main/webapp'
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
				new String[] { "/src/main/webapp/WEB-INF/conf/spring/applicationContext.xml" });
		HibernateTemplate HibernateTemplate = ctx.getBean("hibernateTemplate", HibernateTemplate.class);

		AccountDaoImpl accountDao = new AccountDaoImpl();
		accountDao.setHibernateTemplate(HibernateTemplate);

		Account account = new Account();
		account.setEmail("test@163.com");
		account.setPassword("test");
		account.setAvailable(true);
		account.setCreateTime(new Date());

		// accountDao.addAccount(account);
		// System.out.println("======" +
		// accountDao.getAccountByAccountId(2).toString());
		System.out.println("======" + accountDao.getAccountByEmailAndPwd(account.getEmail(), account.getPassword()));
	}

}
