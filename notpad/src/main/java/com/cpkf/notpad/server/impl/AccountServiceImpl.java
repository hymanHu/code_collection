package com.cpkf.notpad.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpkf.notpad.commons.constants.EnumStatus;
import com.cpkf.notpad.commons.constants.PublicResourceEnum;
import com.cpkf.notpad.dao.IAccountDao;
import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.entity.Resource;
import com.cpkf.notpad.entity.Role;
import com.cpkf.notpad.server.IAccountService;
import com.cpkf.notpad.vo.AccountVo;
import com.cpkf.notpad.vo.Page;
@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private IAccountDao accountDao;

	public Account getAccountByEmailAndPwd(String email, String pwd) {
		Account account = accountDao.getAccountByEmailAndPwd(email, pwd);
		if(null != account){
			return account;
		}
		return null;
	}

	public AccountVo getAccountByEmail(String email) {
		Account account = accountDao.getAccountByEmail(email);
		if(null != account){
			return accountToAccountVo(account);
		}
		return null;
	}

	public void addAccount(Account account) {
		accountDao.addAccount(account);
	}

	public List<String> getResourceListByUserName(String email) {
		List<String> list = new ArrayList<String>();
		//添加公共资源列表
		for(PublicResourceEnum publicResourceEnum : PublicResourceEnum.values()){
			list.add(publicResourceEnum.getPublicResourcePath());
		}
		Account account = accountDao.getAccountByEmail(email);
		if(account != null && null != account.getRoles()){
			for(Role role : account.getRoles()){
				if(null != role.getResources()){
					for(Resource resource : role.getResources()){
						list.add(resource.getUrl());
					}
				}
			}
		}
		return list;
	}

	public List<AccountVo> getAllAccounts() {
		List<Account> accounts = accountDao.getAllAccounts();
		return accountsToAccountVos(accounts);
	}
	/* 
	 * method name   : accountsToAccountVos
	 * description   : 实体对象转化成vo对象
	 * @author       : Jiang.Hu
	 * @param        : @param accounts
	 * @return       : List<AccountVo>
	 * Create at     : 2011-6-5 下午04:59:02
	 * modified      : 
	 */      
	public static List<AccountVo> accountsToAccountVos(List<Account> accounts){
		List<AccountVo> accountVos = null;
		if(accounts != null){
			accountVos = new ArrayList<AccountVo>();
			for(Account account :accounts){
				AccountVo accountVo = new AccountVo();
				accountVo.setAccount(account);
				if(account.getStatus() == true){
					accountVo.setStatus(EnumStatus.VALID.toString());
				} else {
					accountVo.setStatus(EnumStatus.INVALID.toString());
				}
				if(null != account.getRoles() && account.getRoles().size() > 0){
					StringBuffer sb = new StringBuffer();
					for(Role role : account.getRoles()){
						if(null != role.getRoleName() && StringUtils.isNotBlank(role.getRoleName())){
							sb.append(role.getRoleName() + ",");
						}
					}
					if(StringUtils.isBlank(sb.toString())){
						accountVo.setRoleName("No Role!");
					} else {
						accountVo.setRoleName(sb.substring(0, sb.length() - 1));
					}
				}
				accountVos.add(accountVo);
			}
		}
		return accountVos;
	}

	/* 
	 * method name   : accountToAccountVo
	 * description   : account转化为accountVo
	 * @author       : Jiang.Hu
	 * @param        : @param account
	 * @return       : AccountVo
	 * Create at     : Jun 8, 2011 9:33:50 AM
	 * modified      : 
	 */      
	public static AccountVo accountToAccountVo(Account account){
		if(account == null){
			return null;
		}
		AccountVo accountVo = new AccountVo();
		accountVo.setAccount(account);
		if(account.getStatus() == true){
			accountVo.setStatus(EnumStatus.VALID.toString());
		} else {
			accountVo.setStatus(EnumStatus.INVALID.toString());
		}
		if(null != account.getRoles() && account.getRoles().size() > 0){
			StringBuffer sb = new StringBuffer();
			for(Role role : account.getRoles()){
				if(null != role.getRoleName() && StringUtils.isNotBlank(role.getRoleName())){
					sb.append(role.getRoleName() + ",");
				}
			}
			if(StringUtils.isBlank(sb.toString())){
				accountVo.setRoleName("No Role!");
			} else {
				accountVo.setRoleName(sb.substring(0, sb.length() - 1));
			}
		}
		return accountVo;
	}
	public Page getAllAccountVosForPage(Page page) {
		page = accountDao.getAllAccountsForPage(page);
		List<Account> accounts = page.getList();
		page.setList(accountsToAccountVos(accounts));
		return page;
	}

	public Page getQueryAccountVosForPage(Page page) {
		page = accountDao.getQueryAccountsForPage(page);
		List<Account> accounts = page.getList();
		page.setList(accountsToAccountVos(accounts));
		return page;
	}

	public AccountVo getAccountById(Long accountId) {
		return accountToAccountVo(accountDao.getAccountById(accountId));
	}
}
