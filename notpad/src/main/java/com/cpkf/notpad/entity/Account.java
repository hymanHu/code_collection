package com.cpkf.notpad.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**  
 * Filename:    Account.java
 * Description: account实体类
 * Company:     
 * @author:     
 * @version:    1.0
 * Create at:   May 10, 2011 1:44:50 PM
 * modified:    
 */
@Entity
@Table(name="account")
public class Account {
	/**
	 * @Id：该类中独特标志
	 * @Column：对应表中的列
	 * @GeneratedValue：主键生成策略-自增长
	 */
	@Id
	@Column(name="account_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long accountId;
	@Column(name="email",unique=true,nullable=false)
	private String email;
	@Column(name="passWord")
	private String passWord;
	@Column(name="status")
	private Boolean status;
	//多对多单向，并指定中间表的表明和字段名,中间表自动生成联合组建
	//fetch加载方式EAGER-即时加载，LAZY-延迟加载
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="account_role",
			joinColumns={@JoinColumn(name="account_id",referencedColumnName="account_id")},
			inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="role_id")})
	private Set<Role> roles = new HashSet<Role>();
	//cascade-联级操作，eg：删除account同时删除user
	//unique-外键的唯一性
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",unique=true)
	private User user;
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
