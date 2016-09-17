package com.cpkf.notpad.entity;

import java.util.Date;

import javax.persistence.*;

/**  
 * Filename:    Job.java
 * Description: 工作经历
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   Jun 9, 2011 10:11:46 AM
 * modified:    
 */
@Entity
@Table(name="job")
public class Job {
	@Id
	@Column(name="job_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jobId;
	@Column(name="startDate")
	private Date startDate;
	@Column(name="endDate")
	private Date endDate;
	@Column(name="company")
	private String company;
	@Column(name="property")
	private String property;//性质
	@Column(name="size")
	private String size;//规模
	@Column(name="industry")
	private String industry;//行业
	@Column(name="department")
	private String department;//部门
	@Column(name="position")
	private String position;//职位
	@Column(name="description")
	private String description;
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
