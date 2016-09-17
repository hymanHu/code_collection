package com.cpkf.notpad.entity;

import javax.persistence.*;

/**  
 * Filename:    Other.java
 * Description: 其他记录
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   Jun 9, 2011 10:26:10 AM
 * modified:    
 */
@Entity
@Table(name="other")
public class Other {
	@Id
	@Column(name="other_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long otherId;
	@Column(name="title")
	private String title;
	@Column(name="content")
	private String content;
	public Long getOtherId() {
		return otherId;
	}
	public void setOtherId(Long otherId) {
		this.otherId = otherId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
