package com.thornBird.modules.mail.services.impl;

import java.io.File;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.thornBird.modules.mail.beans.MailBean;
import com.thornBird.modules.mail.services.SendMailService;

/**
 * @Description: Send Mail Service Impl
 * @author: HymanHu
 * @date: 2019-08-25 13:39:32
 */
@Service
public class SendMailServiceImpl implements SendMailService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SendMailServiceImpl.class);
	
	@Autowired
	private JavaMailSenderImpl javaMailSenderImpl;
	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public void sendSimpleMail(MailBean mailBean) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//		simpleMailMessage.setFrom(javaMailSenderImpl.getJavaMailProperties().getProperty("mail.smtp.from"));
		simpleMailMessage.setTo(mailBean.getMailTo());
		simpleMailMessage.setCc(mailBean.getMailCc());
		simpleMailMessage.setBcc(mailBean.getMailBcc());
		simpleMailMessage.setReplyTo(mailBean.getReplyTo());
		simpleMailMessage.setSubject(mailBean.getSubject());
		simpleMailMessage.setText(mailBean.getContent());
		simpleMailMessage.setSentDate(new Date());
		
		javaMailSenderImpl.send(simpleMailMessage);
	}

	@Override
	public void sendComplexMail(MailBean mailBean) {
		MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(mimeMessage, true); // 群发
//			helper.setFrom(javaMailSenderImpl.getJavaMailProperties().getProperty("mail.smtp.from"));
			helper.setTo(mailBean.getMailTo());
			if (null != mailBean.getMailCc() && mailBean.getMailCc().length > 0) {
				helper.setCc(mailBean.getMailCc());
			}
			if (null != mailBean.getMailBcc() && mailBean.getMailBcc().length > 0) {
				helper.setBcc(mailBean.getMailBcc());
			}
			if (StringUtils.isNotBlank(mailBean.getReplyTo())) {
				helper.setReplyTo(mailBean.getReplyTo());
			}
			helper.setSubject(mailBean.getSubject());
			helper.setText(mailBean.getContent(), true);
			
			// build attachments
			if (null != mailBean.getAttachments() && mailBean.getAttachments().length > 0) {
				for (String attachment : mailBean.getAttachments()) {
					if (StringUtils.isNotBlank(attachment)) {
						FileSystemResource attachmentFile = new FileSystemResource(new File(attachment));
						helper.addAttachment(attachmentFile.getFilename(), attachmentFile);
					}
				}
			}
			
			// build images
			if (null != mailBean.getImages() && mailBean.getImages().length > 0) {
				for (int i = 0; i < mailBean.getImages().length; i++) {
					String imagePath = mailBean.getImages()[i];
					FileSystemResource imageFile = new FileSystemResource(new File(imagePath));
					String imageCid = null;
					if (null != mailBean.getImageCids() && mailBean.getImageCids().length > 0) {
						imageCid = mailBean.getImageCids()[i];
					}
					helper.addInline(
						StringUtils.isNotBlank(imageCid) ? imageCid : String.format("%s%d", "image-", i), 
						imageFile);
				}
			}
			
			javaMailSenderImpl.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.debug(e.getMessage());
		}
	}

	@Override
	public void sendTemplateMail(MailBean mailBean) {
		Context context = new Context();
		if (null != mailBean.getTemplateMap() && !mailBean.getTemplateMap().isEmpty()) {
			mailBean.getTemplateMap().entrySet().stream().forEach(item -> {
				context.setVariable(item.getKey(), item.getValue());
			});
		}
		String emailContent = templateEngine.process("emailTemplates/" + mailBean.getTemplateId(), context);
		
		MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(mimeMessage, true);
//			helper.setFrom(javaMailSenderImpl.getJavaMailProperties().getProperty("mail.smtp.from"));
			helper.setTo(mailBean.getMailTo());
			if (null != mailBean.getMailCc() && mailBean.getMailCc().length > 0) {
				helper.setCc(mailBean.getMailCc());
			}
			if (null != mailBean.getMailBcc() && mailBean.getMailBcc().length > 0) {
				helper.setBcc(mailBean.getMailBcc());
			}
			if (StringUtils.isNotBlank(mailBean.getReplyTo())) {
				helper.setReplyTo(mailBean.getReplyTo());
			}
			helper.setSubject(mailBean.getSubject());
			helper.setText(emailContent, true);
			
			javaMailSenderImpl.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.debug(e.getMessage());
		}
	}
}
