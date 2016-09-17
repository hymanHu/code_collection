package com.cpkf.notpad.controller.account.model;

/**  
 * Filename:    RegistAccountModel.java
 * Description: 与regist.jsp页面对应的model对象
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 31, 2011 10:22:03 AM
 * modified:    
 */
public class RegistAccountModel {
	private String email;
	private String password;
	private String rePassword;
	private String visualCodeStr;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	public String getVisualCodeStr() {
		return visualCodeStr;
	}
	public void setVisualCodeStr(String visualCodeStr) {
		this.visualCodeStr = visualCodeStr;
	}
}
