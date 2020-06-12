package com.thornBird.modules.mail.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thornBird.commons.utils.DateUtil;
import com.thornBird.modules.mail.beans.MailBean;
import com.thornBird.modules.mail.services.SendMailService;

/**
 * @Description: Mail Controller
 * @author: HymanHu
 * @date: 2019-08-26 21:43:26
 */
@RestController
@RequestMapping("/mail")
public class MailController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MailController.class);

	@Autowired
	private SendMailService sendMailService;
	
	/**
	 * http://dev-services.thornBird.com/mail/simpleMail
	 * https://dev-services.thornBird.com:8085/mail/simpleMail
	 * {"mailTo":["898899721@qq.com"],"mailCc":["hujiangyx@163.com"],"subject":"SimpleMailSubject",
	 * "content":"SimpleMailContent"}
	 */
	@RequestMapping(value="/simpleMail", method=RequestMethod.POST, produces="application/json")
	public void sendSimpleMail(@RequestBody MailBean mailBean) {
		LOGGER.debug("Send simple mail at " + DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_PATTERN));
		sendMailService.sendSimpleMail(mailBean);
		LOGGER.debug("Send simple mail complete at " + DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_PATTERN));
	}
	
	/**
	 * http://dev-services.thornBird.com/mail/complexMail
	 * https://dev-services.thornBird.com:8085/mail/complexMail
	 * {"mailTo":["898899721@qq.com"],"mailCc":["hujiangyx@163.com"],"subject":"SimpleMailSubject",
	 * "content":"<html><head></head><body><h1>hello! Welcome thornBird!</h1><br><img src='cid:image-0'>
	 * </img><br><img src='cid:image-1'></img></body></html>","attachments":["E:/temp/attachments_1.txt",
	 * "E:/temp/attachments_2.txt"],"images":["E:/temp/QQ截图20190824190407.jpg","E:/temp/QQ截图20190824190855.jpg"],
	 * "imageCids":["image-0","image-1"]}
	 */
	@RequestMapping(value="/complexMail", method=RequestMethod.POST, produces="application/json")
	public void sendComplexMail(@RequestBody MailBean mailBean) {
		LOGGER.debug("Send complex mail at " + DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_PATTERN));
		sendMailService.sendComplexMail(mailBean);
		LOGGER.debug("Send complex mail complete at " + DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_PATTERN));
	}
	
	/**
	 * http://dev-services.thornBird.com/mail/templateMail/wellCome
	 * https://dev-services.thornBird.com:8085/mail/templateMail/wellCome
	 * {"mailTo":["898899721@qq.com"],"mailCc":["hujiangyx@163.com"],"subject":"SimpleMailSubject",
	 * "content":"SimpleMailContent","templateMap":{"name":"HymanHu","id":"12345"},"templateId":"wellCome"}
	 */
	@RequestMapping(value="/templateMail/{templateId}", method=RequestMethod.POST, produces="application/json")
	public void sendTemplateMail(@RequestBody MailBean mailBean) {
		LOGGER.debug("Send template mail at " + DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_PATTERN));
		sendMailService.sendTemplateMail(mailBean);
		LOGGER.debug("Send template mail complete at " + DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_PATTERN));
	}
}
