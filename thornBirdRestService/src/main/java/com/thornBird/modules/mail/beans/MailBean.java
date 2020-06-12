package com.thornBird.modules.mail.beans;

import java.util.Map;

/**
 * @Description: Mail Bean
 * @author: HymanHu
 * @date: 2019-08-25 13:36:12
 */
public class MailBean {
	private String mailFrom;
	private String[] mailTo;
	private String[] mailCc; // 抄送
	private String[] mailBcc; // 暗抄送
	private String replyTo;
	private String subject;
	private String content;
	private String[] attachments; // 附件地址
	private String[] images; // 图片地址
	private String[] imageCids; // 图片对应的id数组，在HTML模版中对应<img src='cid:image-0'></img>
	private String templateId;
	private Map<String, String> templateMap; // 模版参数
	
	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String[] getMailTo() {
		return mailTo;
	}

	public void setMailTo(String[] mailTo) {
		this.mailTo = mailTo;
	}

	public String[] getMailCc() {
		return mailCc;
	}

	public void setMailCc(String[] mailCc) {
		this.mailCc = mailCc;
	}

	public String[] getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String[] mailBcc) {
		this.mailBcc = mailBcc;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getAttachments() {
		return attachments;
	}

	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String[] getImageCids() {
		return imageCids;
	}

	public void setImageCids(String[] imageCids) {
		this.imageCids = imageCids;
	}

	public Map<String, String> getTemplateMap() {
		return templateMap;
	}

	public void setTemplateMap(Map<String, String> templateMap) {
		this.templateMap = templateMap;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}
