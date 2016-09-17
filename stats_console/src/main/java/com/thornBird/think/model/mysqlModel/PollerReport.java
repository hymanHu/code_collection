package com.thornBird.think.model.mysqlModel;

import java.util.Date;

import com.thornBird.think.model.PollerType;

/**
 * 轮询报告，task从PollerOutPutKeyValue中读取数值，生成报告
 * @author hyman
 */
public class PollerReport {
	private int id;
	private int itemId;
	private PollerType pollertype;
	private String statsKey;
	private String statsValue;
	private Date startTime;
	private Date endTime;

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

	public PollerType getPollertype() {
		return pollertype;
	}

	public void setPollertype(PollerType pollertype) {
		this.pollertype = pollertype;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "PollerReport [id=" + id + ", itemId=" + itemId + ", pollertype=" + pollertype + ", statsKey="
				+ statsKey + ", statsValue=" + statsValue + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
