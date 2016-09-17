package com.cpkf.notpad.dao;

import java.util.List;

import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.vo.Page;

/**  
 * Filename:    IAccountDao.java
 * Description: account数据操作接口
 * Company:     
 * @author:     
 * @version:    1.0
 * Create at:   May 10, 2011 2:52:53 PM
 * modified:    
 */
public interface IAccountDao {
	/* 
	 * method name   : getAccountByEmailAndPwd
	 * description   : 根据email和password查询account
	 * @author       : 
	 * @param        : @param email
	 * @param        : @param pwd
	 * @return       : Account
	 * Create at     : May 10, 2011 3:21:53 PM
	 * modified      : 
	 */      
	Account getAccountByEmailAndPwd(String email,String pwd);
	/* 
	 * method name   : getAccountByEmail
	 * description   : 根据email查询用户
	 * @author       : Jiang.Hu
	 * @param        : @param email
	 * @return       : Account
	 * Create at     : May 25, 2011 4:20:13 PM
	 * modified      : 
	 */      
	Account getAccountByEmail(String email);
	/* 
	 * method name   : addAccount
	 * description   : 添加账户实体
	 * @author       : Jiang.Hu
	 * @param        : @param account
	 * @return       : void
	 * Create at     : May 31, 2011 1:51:55 PM
	 * modified      : 
	 */      
	void addAccount(Account account);
	/* 
	 * method name   : getAllAccounts
	 * description   : 得到用户列表
	 * @author       : Jiang.Hu
	 * @return       : List<Account>
	 * Create at     : Jun 3, 2011 3:41:27 PM
	 * modified      : 
	 */      
	List<Account> getAllAccounts();
	/* 
	 * method name   : getAllAccountsForPage
	 * description   : 分页查询
	 * @author       : Jiang.Hu
	 * @param        : @param page-分页对象
	 * @return       : page
	 * Create at     : 2011-6-6 上午09:48:11
	 * modified      : 
	 */      
	Page getAllAccountsForPage(Page page);
	/* 
	 * method name   : getQueryAccountsForPage
	 * description   : 根据条件查询account列表
	 * @author       : Jiang.Hu
	 * @param        : @param page
	 * @return       : Page
	 * Create at     : Jun 7, 2011 10:11:56 AM
	 * modified      : 
	 */      
	Page getQueryAccountsForPage(Page page);
	/* 
	 * method name   : getAccountById
	 * description   : 根据accountId查询账户信息
	 * @author       : Jiang.Hu
	 * @param        : @param accountId
	 * @return       : Account
	 * Create at     : Jun 8, 2011 9:20:29 AM
	 * modified      : 
	 */      
	Account getAccountById(Long accountId);
}
