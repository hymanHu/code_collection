package com.thornBird.modules.mail.services;

import com.thornBird.modules.mail.beans.MailBean;

/**
 * @Description: Send Mail Service
 * @author: HymanHu
 * @date: 2019-08-25 13:15:22
 */
public interface SendMailService {

	void sendSimpleMail(MailBean mailBean);
	
	void sendComplexMail(MailBean mailBean);
	
	void sendTemplateMail(MailBean mailBean);
}
