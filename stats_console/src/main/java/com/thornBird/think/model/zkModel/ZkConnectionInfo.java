package com.thornBird.think.model.zkModel;

/**
 * 配置文件configuratio.properties上stats相关的配置信息
 * @author hyman
 */
public class ZkConnectionInfo {

	private String zkHost;
	private int sessionTimeout;
	private int connectionTimeout;
	private String statsConfig;

	public String getZkHost() {
		return zkHost;
	}

	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getStatsConfig() {
		return statsConfig;
	}

	public void setStatsConfig(String statsConfig) {
		this.statsConfig = statsConfig;
	}

	@Override
	public String toString() {
		return "ZkConnectionINfo [zkHost=" + zkHost + ", sessionTimeout=" + sessionTimeout + ", connectionTimeout="
				+ connectionTimeout + ", statsConfig=" + statsConfig + "]";
	}

}
