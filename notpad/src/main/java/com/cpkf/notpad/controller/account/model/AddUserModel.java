package com.cpkf.notpad.controller.account.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.*;

import com.cpkf.notpad.commons.constants.RegexConstants;


/**  
 * Filename:    AddUserModel.java
 * Description: 与页面表单对应的模型对象，在控制器里以
 * 				@Valid @ModelAttribute("addUserModel") AddUserModel addUserModel
 * 				参数形式出现，绑定表单输入数据
 * 				在根目录中创建校验提示信息的配置文件 ValidationMessages.properties进行国际化配置
 * 				这种校验方式局限性大，hibernate本身提供的校验器较少，还必须放在根目录
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 16, 2011 3:06:45 PM
 * modified:    
 */
public class AddUserModel {
	//@NotEmpty(message="{account.error.userName.required}")
	private String userName;
	private String nickname;
	//@NotEmpty(message="{account.error.email.required}")
	//@Email(message="{account.error.email.invalid}")
	private String email;
	//@Size(min=6,max=12,message="{account.error.passWord.size}")
	private String passWord;
	private String rePassWord;
	private String gender;
	private String userProvince;
	private String userCity;
	private String birthday;
	//@Pattern(regexp=RegexConstants.PHONE_REGEX,message="{account.error.phone.invalid}")
	private String phone;
	private String registTime;
	//@AssertTrue(message="{account.error.passWord.confirm}")
	public boolean isPassWordValid(){
		if(StringUtils.isNotBlank(passWord) && StringUtils.isNotBlank(rePassWord)){
			return passWord.equals(rePassWord);
		}
		return false;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRePassWord() {
		return rePassWord;
	}
	public void setRePassWord(String rePassWord) {
		this.rePassWord = rePassWord;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUserProvince() {
		return userProvince;
	}
	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
}
