package com.cpkf.notpad.entity;

import javax.persistence.*;

@Entity
@Table(name="menu")
public class Menu {
	@Id
	@Column(name="menu_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long menuId;
	@Column(name="menuName")
	private String menuName;
	@Column(name="url")
	private String url;
	@Column(name="groupName")
	private String group;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
