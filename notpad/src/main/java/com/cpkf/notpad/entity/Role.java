package com.cpkf.notpad.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**  
 * Filename:    Role.java
 * Description: 角色类
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 19, 2011 4:13:53 PM
 * modified:    
 */
@Entity
@Table(name="role")
public class Role {
	@Id
	@Column(name="role_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleId;
	@Column(name="roleName")
	private String roleName;
	@Column(name="description")
	private String description;
	@Column(name="status")
	private Boolean status;
	//多对多双向
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="role_resource",
			joinColumns={@JoinColumn(name="role_id",referencedColumnName="role_id")},
			inverseJoinColumns={@JoinColumn(name="resource_id",referencedColumnName="resource_id")})
	private Set<Resource> resources = new HashSet<Resource>();
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Set<Resource> getResources() {
		return resources;
	}
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
}
