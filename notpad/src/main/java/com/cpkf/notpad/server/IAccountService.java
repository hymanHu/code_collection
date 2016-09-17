package com.cpkf.notpad.server;

import java.util.List;

import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.entity.Resource;
import com.cpkf.notpad.vo.AccountVo;
import com.cpkf.notpad.vo.Page;

/**  
 * Filename:    IAccountService.java
 * Description: account服务类
 * Company:     
 * @author:     
 * @version:    1.0
 * Create at:   May 10, 2011 3:21:09 PM
 * modified:    
 */
public interface IAccountService {
	/* 
	 * method name   : getAccountByEmailAndPwd
	 * description   : 根据email和password查询account
	 * @author       : Jiang.Hu
	 * @param        : @param email
	 * @param        : @param pwd
	 * @return       : Account
	 * Create at     : May 10, 2011 3:21:35 PM
	 * modified      : 
	 */      
	Account getAccountByEmailAndPwd(String email,String pwd);
	/* 
	 * method name   : getAccountByEmail
	 * description   : 根据email查询用户
	 * @author       : Jiang.Hu
	 * @param        : @param email
	 * @return       : AccountVo
	 * Create at     : May 25, 2011 4:17:46 PM
	 * modified      : 
	 */      
	AccountVo getAccountByEmail(String email);
	/* 
	 * method name   : addAccount
	 * description   : 添加account实体
	 * @author       : Jiang.Hu
	 * @param        : @param account
	 * @return       : void
	 * Create at     : May 31, 2011 1:57:06 PM
	 * modified      : 
	 */      
	void addAccount(Account account);
	/* 
	 * method name   : getResourceListByUserName
	 * description   : 根据email获取用户资源列表
	 * @author       : Jiang.Hu
	 * @param        : @param email
	 * @return       : List<Resource>
	 * Create at     : Jun 2, 2011 3:47:10 PM
	 * modified      : 
	 */      
	List<String> getResourceListByUserName(String email);
	/* 
	 * method name   : getAllAccounts
	 * description   : 获得用户列表
	 * @author       : Jiang.Hu
	 * @return       : List<Account>
	 * Create at     : Jun 3, 2011 3:40:26 PM
	 * modified      : 
	 */      
	List<AccountVo> getAllAccounts();
	/* 
	 * method name   : getAllAccountVosForPage
	 * description   : 分页查询用户列表
	 * @author       : Jiang.Hu
	 * @param        : @param page-page对象
	 * @return       : page对象
	 * Create at     : 2011-6-6 上午10:01:17
	 * modified      : 
	 */      
	Page getAllAccountVosForPage(Page page);
	/* 
	 * method name   : getQueryAccountVosForPage
	 * description   : 根据条件查询用户列表
	 * @author       : Jiang.Hu
	 * @param        : @param page
	 * @return       : Page
	 * Create at     : Jun 7, 2011 10:23:38 AM
	 * modified      : 
	 */      
	Page getQueryAccountVosForPage(Page page);
	/* 
	 * method name   : getAccountById
	 * description   : 根据id查找账户信息
	 * @author       : Jiang.Hu
	 * @param        : @param accountId
	 * @return       : AccountVo
	 * Create at     : Jun 8, 2011 9:27:54 AM
	 * modified      : 
	 */      
	AccountVo getAccountById(Long accountId);
}
