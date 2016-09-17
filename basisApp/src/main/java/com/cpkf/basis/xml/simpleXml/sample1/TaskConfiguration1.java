package com.cpkf.basis.xml.simpleXml.sample1;

import java.io.File;
import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Persister;
/**
 * 简单xmlBo对象--对应"/testFile/task.xml"
 * 将root节点当作element读取，每个属性定义text & path
 * @author hyman
 */
@Root(name = "taskConfiguration")
public class TaskConfiguration1 {

	/*
	 *  如果xml多层均含有property属性，单独读取某层该属性没有问题
	 *  如果多层读取，那么每个每个map都是各层property属性合集
	 */
	@ElementMap(entry = "property", key = "key", value = "value", attribute = true, inline = true, required = false)
	private Map<String, String> properties;
	@Text
	@Path("zookeeper/host")
	private String host;
	@Text
	@Path("zookeeper/rootConfigNode")
	private String rootConfigNode;
	@Text
	@Path("zookeeper/sessionTimeout")
	private int sessionTimeout;
	@Text
	@Path("zookeeper/connectionTimeout")
	private int connectionTimeout;
	@Text
	@Path("scheduledTasks/deleteRemoved/delay")
	private int deleteRemoved;
	@Text
	@Path("scheduledTasks/deleteArchived/delay")
	private int deleteArchived;
	@Path("sqlCluster")
	@ElementMap(entry = "property", key = "key", value = "value", attribute = true, inline = true, required = false)
	private Map<String, String> sqlCluster;

	public TaskConfiguration1() {
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getRootConfigNode() {
		return rootConfigNode;
	}

	public void setRootConfigNode(String rootConfigNode) {
		this.rootConfigNode = rootConfigNode;
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

	public int getDeleteRemoved() {
		return deleteRemoved;
	}

	public void setDeleteRemoved(int deleteRemoved) {
		this.deleteRemoved = deleteRemoved;
	}

	public int getDeleteArchived() {
		return deleteArchived;
	}

	public void setDeleteArchived(int deleteArchived) {
		this.deleteArchived = deleteArchived;
	}

	public Map<String, String> getSqlCluster() {
		return sqlCluster;
	}

	public void setSqlCluster(Map<String, String> sqlCluster) {
		this.sqlCluster = sqlCluster;
	}

	@Override
	public String toString() {
		return "TaskConfiguration1 [properties=" + properties + ", host=" + host + ", rootConfigNode=" + rootConfigNode
				+ ", sessionTimeout=" + sessionTimeout + ", connectionTimeout=" + connectionTimeout
				+ ", deleteRemoved=" + deleteRemoved + ", deleteArchived=" + deleteArchived + ", sqlCluster="
				+ sqlCluster + "]";
	}

	public static void main(String[] args) throws Exception {
		Serializer serializer = new Persister();
		TaskConfiguration1 task = serializer.read(TaskConfiguration1.class, TaskConfiguration1.class.getClassLoader()
				.getResourceAsStream("testFile/task.xml"), false);
		System.out.println(task.toString());
		
		File file = new File("/1.xml");
		serializer.write(task, file);
	}

}
