package com.thornBird.think.model.mysqlModel;

import java.util.Date;

/**
 * 监控项输出-json字符串
 * @author hyman
 */
public class PollerOutputJSON {
	private int id;
	private int itemId;
	private String output;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "PollerOutput [id=" + id + ", itemId=" + itemId + ", output=" + output + ", createTime=" + createTime
				+ "]";
	}

}
