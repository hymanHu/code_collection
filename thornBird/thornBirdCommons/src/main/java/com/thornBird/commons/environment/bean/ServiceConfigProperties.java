package com.thornBird.commons.environment.bean;

/**
 * @Description: Service Config Properties
 * @author: HymanHu
 * @date: 2019-01-14 09:51:21  
 */
public class ServiceConfigProperties {
	public String hostName;
	public int port;
	public int microPort;
	
	/**
	 * @return like "dev-services.thornBird.com：8085"
	 */
	public String getHost() {
		return this.getHostName() + ":" + this.getPort();
	}
	
	/**
	 * @return like "dev-services.thornBird.com：8998"
	 */
	public String getMicroHost() {
		return this.getHostName() + ":" + this.getMicroPort();
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMicroPort() {
		return microPort;
	}

	public void setMicroPort(int microPort) {
		this.microPort = microPort;
	}

}
