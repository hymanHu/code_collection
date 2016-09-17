package com.cpkf.notpad.security.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.entity.Resource;
import com.cpkf.notpad.entity.Role;
import com.cpkf.notpad.security.MyUserDetailService;
import com.cpkf.notpad.server.IAccountService;

/**  
 * Filename:    MyUserDetailServiceImpl.java
 * Description: 用自定义的UserDetailService获取登陆用户信息，并赋值给认证管理器
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 26, 2011 4:23:27 PM
 * modified:    
 */
@Service("myUserDetailService")
public class MyUserDetailServiceImpl implements MyUserDetailService {
	private static final Logger logger = Logger.getLogger(MyUserDetailServiceImpl.class);
	@Autowired
	private IAccountService accountService;
	public UserDetails loadUserByUsername(String username) 
		throws UsernameNotFoundException, DataAccessException {
		//取得用户
		Account account = accountService.getAccountByEmail(username) == null ? null : accountService.getAccountByEmail(username).getAccount();
		if(account == null){
			logger.info("Can not found any Account by given username:" + username);
			throw new UsernameNotFoundException("Can not found any Account by given username:" + username);
		}
		//取得用户角色
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if(!account.getRoles().isEmpty() && account.getRoles() != null){
			Iterator<Role> roleIterator = account.getRoles().iterator();
			while(roleIterator.hasNext()){
				Role role = roleIterator.next();
				GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(role.getRoleName().toUpperCase());
//				System.out.println("用户角色：" + role.getRoleName().toUpperCase());
				authorities.add(grantedAuthority);
			}
		}
		
		return new User(username, account.getPassWord(), true, true, true, true, authorities);
	}

}
