package com.cpkf.basis.xml.simpleXml.sample1;

import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * 简单xmlBo对象--对应"/testFile/task.xml"
 * 只读取xml文件其中一个节点 & 读取所有
 * 1、类名上指定为element；
 * 2、属性上指定path & text，如果为attribute要声明；
 * 3、空构造；
 * 4、调用read方法时候声明在非严格模式下阅读，参数为false
 * 5、如果读root节点需要去掉path属性
 * @author Administrator
 */
@Element(name = "sqlCluster")
public class SqlCluster {
	
	@ElementMap(entry = "property", key = "key", value = "value", attribute = true, inline = true, required = false)
	private Map<String, String> properties;

	public SqlCluster(Map<String, String> properties) {
		this.properties = properties;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public SqlCluster() {
	}

	@Override
	public String toString() {
		return "SqlCluster [properties=" + properties + "]";
	}

	public static void main(String[] args) throws Exception {
		Serializer serializer = new Persister();
		SqlCluster sqlCluster = serializer.read(SqlCluster.class, 
				SqlCluster.class.getClassLoader().getResourceAsStream("testFile/task.xml"), false);
		System.out.println(sqlCluster.toString());
	}
}
