package com.thornBird.think.model.mysqlModel;

import java.util.Date;

/**
 * 监控项输出-key-value值
 * @author hyman
 */
public class PollerOutputKeyValue {
	private int id;
	private int itemId;
	private String statsKey;
	private String statsValue;
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

	public String getStatsKey() {
		return statsKey;
	}

	public void setStatsKey(String statsKey) {
		this.statsKey = statsKey;
	}

	public String getStatsValue() {
		return statsValue;
	}

	public void setStatsValue(String statsValue) {
		this.statsValue = statsValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "PollerOutputDetail [id=" + id + ", itemId=" + itemId + ", statsKey=" + statsKey + ", statsValue="
				+ statsValue + ", createTime=" + createTime + "]";
	}
}
