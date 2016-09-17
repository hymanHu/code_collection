package com.cpkf.notpad.vo;

import com.cpkf.notpad.entity.Account;

/**  
 * Filename:    AccountVo.java
 * Description: accountVo
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   2011-6-4 下午10:21:43
 * modified:    
 */
public class AccountVo {
	private Account account;
	private String roleName;
	private String status;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
