package com.cpkf.notpad.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**  
 * Filename:    Resource.java
 * Description: 资源类
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 19, 2011 4:09:41 PM
 * modified:    
 */
@Entity
@Table(name="resource")
public class Resource {
	@Id
	@Column(name="resource_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long resourceId;
	@Column(name="resourceName")
	private String resourceName;
	@Column(name="url")
	private String url;
	@ManyToMany(mappedBy="resources",fetch=FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
