package com.cpkf.notpad.entity;

import java.util.Date;

import javax.persistence.*;

/**  
 * Filename:    Event.java
 * Description: 事件记录
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   Jun 9, 2011 10:23:14 AM
 * modified:    
 */
@Entity
@Table(name="event")
public class Event {
	@Id
	@Column(name="event_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long eventId;
	@Column(name="startDate")
	private Date startDate;
	@Column(name="endDate")
	private Date endDate;
	@Column(name="eventName")
	private String eventName;
	@Column(name="description")
	private String description;
	@Column(name="result")
	private String result;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
