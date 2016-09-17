package com.cpkf.notpad.controller.account.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;

import com.cpkf.notpad.commons.constants.RegexConstants;
import com.cpkf.notpad.controller.account.model.AddUserModel;
import com.cpkf.notpad.controller.account.model.RegistAccountModel;
import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.server.IAccountService;

/**  
 * Filename:    UserValidator.java
 * Description: 用户相关校验器
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 31, 2011 10:26:00 AM
 * modified:    
 */
public class UserValidator {
	/* 
	 * method name   : RegistAccountValidator
	 * description   : 用户注册页面验证
	 * @author       : Jiang.Hu
	 * @param        : @param model
	 * @param        : @param errors
	 * @return       : void
	 * Create at     : May 31, 2011 10:25:58 AM
	 * modified      : 
	 */      
	public void registAccountValidator(HttpServletRequest request,Object model,BindingResult errors,IAccountService accountService){
		RegistAccountModel registAccountModel = (RegistAccountModel) model;
		Account account = accountService.getAccountByEmail(registAccountModel.getEmail()) == null ? null : accountService.getAccountByEmail(registAccountModel.getEmail()).getAccount();
		if(StringUtils.isNotBlank(registAccountModel.getEmail()) && 
				account != null){
			errors.rejectValue("email", "account.error.email.exist", "email is exist.");
		}
		if(StringUtils.isBlank(registAccountModel.getEmail())){
			errors.rejectValue("email", "account.error.email.required", "email is required.");
		}
		if(StringUtils.isNotBlank(registAccountModel.getEmail()) && 
				!registAccountModel.getEmail().matches(RegexConstants.EMAIL_REGEX)){
			errors.rejectValue("email", "account.error.email.invalid", "email is invalid.");
		}
		if(registAccountModel.getPassword().trim().length() < 6 || registAccountModel.getPassword().trim().length() > 12){
			errors.rejectValue("password", "account.error.passWord.size", "password must be between 6-12.");
		}
		if(registAccountModel.getRePassword().trim().length() < 6 || registAccountModel.getRePassword().trim().length() > 12){
			errors.rejectValue("rePassword", "account.error.passWord.size", "password must be between 6-12.");
		}
		if(StringUtils.isNotBlank(registAccountModel.getRePassword()) && 
				!registAccountModel.getPassword().equals(registAccountModel.getRePassword())){
			errors.rejectValue("rePassword", "account.error.passWord.confirm", "enter the password twice inconsistent.");
		}
		if(StringUtils.isBlank(registAccountModel.getVisualCodeStr())){
			errors.rejectValue("visualCodeStr", "account.error.visualCodeStr.required", "email is required.");
		}
		if(StringUtils.isNotBlank(registAccountModel.getVisualCodeStr()) && 
				!registAccountModel.getVisualCodeStr().equals(request.getSession().getAttribute("checkCode"))){
			errors.rejectValue("visualCodeStr", "account.error.visualCodeStr.invalid", "visualCode is invalid.");
		}
	}
	public void addUserValidator(Object model,BindingResult errors){
		AddUserModel addUserModel = (AddUserModel) model;
		if(StringUtils.isBlank(addUserModel.getUserName())){
			//参数1-对应的字段名，参数2-国际化文件中对应的key值，参数三-没有找到key时默认的提示信息
			errors.rejectValue("userName","account.error.userName.required","userName is required.");
		}
		if(StringUtils.isBlank(addUserModel.getEmail())){
			errors.rejectValue("email", "account.error.email.required", "email is required.");
		}
		if(StringUtils.isNotBlank(addUserModel.getEmail()) && 
				!addUserModel.getEmail().matches(RegexConstants.EMAIL_REGEX)){
			errors.rejectValue("email", "account.error.email.invalid", "email is invalid.");
		}
		if(addUserModel.getPassWord().trim().length() < 6 || addUserModel.getPassWord().trim().length() > 12){
			errors.rejectValue("passWord", "account.error.passWord.size", "password must be between 6-12.");
		}
		if(addUserModel.getRePassWord().trim().length() < 6 || addUserModel.getRePassWord().trim().length() > 12){
			errors.rejectValue("rePassWord", "account.error.passWord.size", "password must be between 6-12.");
		}
		if(StringUtils.isNotBlank(addUserModel.getRePassWord()) && 
				!addUserModel.getPassWord().equals(addUserModel.getRePassWord())){
			errors.rejectValue("rePassWord", "account.error.passWord.confirm", "enter the password twice inconsistent.");
		}
		if(StringUtils.isNotBlank(addUserModel.getPhone()) && 
				!addUserModel.getPhone().matches(RegexConstants.PHONE_REGEX)){
			errors.rejectValue("phone", "account.error.phone.invalid", "phone is invalid.");
		}
	}
}
