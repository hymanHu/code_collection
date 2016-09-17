package com.cpkf.notpad.entity;

import java.util.Date;

import javax.persistence.*;

/**  
 * Filename:    Education.java
 * Description: 教育经历
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   Jun 9, 2011 10:03:06 AM
 * modified:    
 */
@Entity
@Table(name="education")
public class Education {
	@Id
	@Column(name="education_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long educationId;
	@Column(name="startDate")
	private Date startDate;
	@Column(name="endDate")
	private Date endDate;
	@Column(name="school")
	private String school;
	@Column(name="profession")
	private String profession;//专业
	@Column(name="degree")
	private String degree;//学历
	@Column(name="description")
	private String description;
	public Long getEducationId() {
		return educationId;
	}
	public void setEducationId(Long educationId) {
		this.educationId = educationId;
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
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
