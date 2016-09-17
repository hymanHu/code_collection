package com.thornBird.think.model.mysqlModel;

/**
 * stats 监控组
 * 
 * @author hyman
 */
public class PollerGroup {
	private int id;
	private String groupName;
	private String groupValue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupValue() {
		return groupValue;
	}

	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}

	@Override
	public String toString() {
		return "PollerGroup [id=" + id + ", groupName=" + groupName + ", groupValue=" + groupValue + "]";
	}

}
