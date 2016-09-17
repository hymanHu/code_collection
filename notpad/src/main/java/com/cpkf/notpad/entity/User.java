package com.cpkf.notpad.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.aspectj.weaver.NewConstructorTypeMunger;
/**  
 * Filename:    User.java
 * Description: 用户类
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 20, 2011 10:07:19 AM
 * modified:    
 */
@Entity
@Table(name="user")
public class User {
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	/*基本资料*/
	@Column(name="userName")
	private String userName;
	@Column(name="nickName")
	private String nickName;
	@Column(name="image")
	private String image;
	@Column(name="gender",columnDefinition="boolean default false not null")
	private boolean gender;
	@Column(name="nationality")
	private String nationality;//国籍
	@Column(name="nation")
	private String nation;//民族
	@Column(name="birthday")
	private Date birthday;
	//设置默认时间，columnDefinition="datetime DEFAULT now()"
	//mysql现在还不支持函数
	@Column(name="registTime")
	private Date registTime;
	@Column(name="height")
	private Double height;
	@Column(name="weight")
	private Double weight;
	@Column(name="health")
	private String health;
	@Column(name="cardType")
	private Integer cardType;//证件类型
	@Column(name="nationalId")
	private String nationalId;//身份证
	@Column(name="militaryId")
	private String militaryId;//军官证
	@Column(name="passportId")
	private String passportId;//护照
	@Column(name="householdRegister")
	private String householdRegister;//户籍
	@Column(name="residence")
	private String residence;//现居住地
	@Column(name="marriage",columnDefinition="boolean default false not null")
	private boolean marriage;
	@Column(name="children",columnDefinition="boolean default false not null")
	private boolean children;
	@Column(name="career")
	private String career;//职业
	@Column(name="salary")
	private Double salary;
	@Column(name="hobby")
	private String hobby;//爱好
	/*联系方式*/
	@Column(name="address")
	private String address;
	@Column(name="zipCode")
	private String zipCode;
	@Column(name="mobile")
	private String mobile;
	@Column(name="phone")
	private String phone;
	@Column(name="qq")
	private String QQ;
	@Column(name="msn")
	private String MSN;
	@Column(name="homePage")
	private String homePage;
	/*梦想与评价*/
	@Column(name="dream")
	private String dream;
	@Column(name="evaluation")
	private String evaluation;
	/*问题与答案*/	
	@Column(name="question")
	private String question;
	@Column(name="answer")
	private String answer;
	/*教育经历*/
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	@OrderBy("endDate desc")
	private Set<Education> educations = new HashSet<Education>();
	/*工作经历*/
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	@OrderBy("endDate desc")
	private Set<Job> jobs = new HashSet<Job>();
	/*项目经历*/
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	@OrderBy("endDate desc")
	private Set<Project> projects = new HashSet<Project>();
	/*记事*/
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	@OrderBy("endDate desc")
	private Set<Event> events = new HashSet<Event>();
	/*其他*/
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	private Set<Other> others = new HashSet<Other>();
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public String getMilitaryId() {
		return militaryId;
	}
	public void setMilitaryId(String militaryId) {
		this.militaryId = militaryId;
	}
	public String getPassportId() {
		return passportId;
	}
	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}
	public String getHouseholdRegister() {
		return householdRegister;
	}
	public void setHouseholdRegister(String householdRegister) {
		this.householdRegister = householdRegister;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public boolean isMarriage() {
		return marriage;
	}
	public void setMarriage(boolean marriage) {
		this.marriage = marriage;
	}
	public boolean isChildren() {
		return children;
	}
	public void setChildren(boolean children) {
		this.children = children;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getMSN() {
		return MSN;
	}
	public void setMSN(String mSN) {
		MSN = mSN;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public String getDream() {
		return dream;
	}
	public void setDream(String dream) {
		this.dream = dream;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Set<Education> getEducations() {
		return educations;
	}
	public void setEducations(Set<Education> educations) {
		this.educations = educations;
	}
	public Set<Job> getJobs() {
		return jobs;
	}
	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}
	public Set<Project> getProjects() {
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	public Set<Event> getEvents() {
		return events;
	}
	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	public Set<Other> getOthers() {
		return others;
	}
	public void setOthers(Set<Other> others) {
		this.others = others;
	}
	
}
