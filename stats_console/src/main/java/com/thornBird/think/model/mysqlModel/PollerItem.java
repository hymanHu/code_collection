package com.thornBird.think.model.mysqlModel;

import com.thornBird.think.model.StatsType;

/**
 * 监控项
 * @author hyman
 */
public class PollerItem {
	private int id;
	private int groupId;
	private String pollerName;
	private StatsType statsType;
	private String pollerCommand;
	private int pollerInterval;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getPollerName() {
		return pollerName;
	}

	public void setPollerName(String pollerName) {
		this.pollerName = pollerName;
	}

	public StatsType getStatsType() {
		return statsType;
	}

	public void setStatsType(StatsType statsType) {
		this.statsType = statsType;
	}

	public String getPollerCommand() {
		return pollerCommand;
	}

	public void setPollerCommand(String pollerCommand) {
		this.pollerCommand = pollerCommand;
	}

	public int getPollerInterval() {
		return pollerInterval;
	}

	public void setPollerInterval(int pollerInterval) {
		this.pollerInterval = pollerInterval;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + groupId;
		result = prime * result + id;
		result = prime * result + ((pollerCommand == null) ? 0 : pollerCommand.hashCode());
		result = prime * result + pollerInterval;
		result = prime * result + ((pollerName == null) ? 0 : pollerName.hashCode());
		result = prime * result + ((statsType == null) ? 0 : statsType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollerItem other = (PollerItem) obj;
		if (groupId != other.groupId)
			return false;
		if (id != other.id)
			return false;
		if (pollerCommand == null) {
			if (other.pollerCommand != null)
				return false;
		} else if (!pollerCommand.equals(other.pollerCommand))
			return false;
		if (pollerInterval != other.pollerInterval)
			return false;
		if (pollerName == null) {
			if (other.pollerName != null)
				return false;
		} else if (!pollerName.equals(other.pollerName))
			return false;
		if (statsType != other.statsType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PollerItem [id=" + id + ", groupId=" + groupId
				+ ", pollerName=" + pollerName + ", statsType=" + statsType
				+ ", pollerCommand=" + pollerCommand + ", pollerInterval="
				+ pollerInterval + "]";
	}

}
