package com.cpkf.basis.xml.simpleXml.sample1;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Persister;

/**
 * 简单xmlBo对象--对应"/testFile/task.xml"
 * 只读取xml文件其中一个节点 & 读取所有
 * 1、类名上指定为element；
 * 2、属性上指定path & text，去掉element属性，如果为attribute要声明；
 * 3、空构造；
 * 4、调用read方法时候声明在非严格模式下阅读，参数为false
 * 5、如果读root节点，则需要去掉text & path 加上element属性
 * @author hyman
 */
@Element(name = "zookeeper")
public class Zookeeper {

	@Element(required = true)
	//@Text
	//@Path("zookeeper/host")
	private String host;
	@Element(required = true)
	//@Text
	//@Path("zookeeper/rootConfigNode")
	private String rootConfigNode;
	@Element(required = true)
	//@Text
	//@Path("zookeeper/sessionTimeout")
	private int sessionTimeout;
	@Element(required = true)
	//@Text
	//@Path("zookeeper/connectionTimeout")
	private int connectionTimeout;

	public Zookeeper(String host, String rootConfigNode, int sessionTimeout, int connectionTimeout) {
		this.host = host;
		this.rootConfigNode = rootConfigNode;
		this.sessionTimeout = sessionTimeout;
		this.connectionTimeout = connectionTimeout;
	}

	public Zookeeper() {
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

	@Override
	public String toString() {
		return "Zookeeper [host=" + host + ", rootConfigNode=" + rootConfigNode + ", sessionTimeout=" + sessionTimeout
				+ ", connectionTimeout=" + connectionTimeout + "]";
	}

	public static void main(String[] args) throws Exception {
		Serializer serializer = new Persister();
		Zookeeper zookeeper = serializer.read(Zookeeper.class,
				Zookeeper.class.getClassLoader().getResourceAsStream("testFile/task.xml"), false);
		System.out.println(zookeeper.toString());
	}
}
