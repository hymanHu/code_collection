package com.cpkf.basis.xml.simpleXml.sample1;

import java.io.File;
import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * 简单xmlBo对象--对应"/testFile/task.xml"
 * 需要里层节点均以对象形式存在
 * @author Administrator
 */
@Root(name = "taskConfiguration")
public class TaskConfiguration {

	@ElementMap(entry = "property", key = "key", value = "value", attribute = true, inline = true, required = false)
	private Map<String, String> properties;
	@Element(name = "sqlCluster")
	private SqlCluster sqlCluster;
	@Element(name = "zookeeper")
	private Zookeeper zookeeper;
	@Element(name = "scheduledTasks")
	private ScheduledTasks scheduledTasks;
	
	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public SqlCluster getSqlCluster() {
		return sqlCluster;
	}

	public void setSqlCluster(SqlCluster sqlCluster) {
		this.sqlCluster = sqlCluster;
	}

	public Zookeeper getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(Zookeeper zookeeper) {
		this.zookeeper = zookeeper;
	}

	public ScheduledTasks getScheduledTasks() {
		return scheduledTasks;
	}

	public void setScheduledTasks(ScheduledTasks scheduledTasks) {
		this.scheduledTasks = scheduledTasks;
	}

	@Override
	public String toString() {
		return "TaskConfiguration [properties=" + properties + ", sqlCluster="
				+ sqlCluster + ", zookeeper=" + zookeeper + ", scheduledTasks="
				+ scheduledTasks + "]";
	}

	public static void main(String[] args) throws Exception {
		Serializer serializer = new Persister();
		TaskConfiguration task = serializer.read(
				TaskConfiguration.class,
				TaskConfiguration.class.getClassLoader().getResourceAsStream(
						"testFile/task.xml"), false);
		System.out.println(task.toString());
	}

}
