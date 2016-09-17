package com.cpkf.basis.xml.simpleXml.sample2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Example1 {
	
	@Attribute
	private int index;
	@Element(required = true)
	private String name;
	@Element(required = true)
	private int age;
	@Element(required = false)
	private boolean marry;
	@ElementList(required = false)
	private List<String> boys = new ArrayList<String>();
	@ElementMap(entry = "property", key = "key", value = "value", attribute = true, inline = true, required = false)
	private Map<String, String> girls = new HashMap<String, String>();

	public Example1() {
	}

	public Example1(int index, String name, int age, boolean marry, List<String> boys, Map<String, String> girls) {
		this.index = index;
		this.name = name;
		this.age = age;
		this.marry = marry;
		this.boys = boys;
		this.girls = girls;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isMarry() {
		return marry;
	}

	public void setMarry(boolean marry) {
		this.marry = marry;
	}

	public List<String> getBoys() {
		return boys;
	}

	public void setBoys(List<String> boys) {
		this.boys = boys;
	}

	public Map<String, String> getGirls() {
		return girls;
	}

	public void setGirls(Map<String, String> girls) {
		this.girls = girls;
	}

	@Override
	public String toString() {
		return "Example1 [index=" + index + ", name=" + name + ", age=" + age + ", marry=" + marry + ", boys=" + boys
				+ ", girls=" + girls + "]";
	}

	public static void main(String[] args) throws Exception {
		Serializer serializer = new Persister();
		List<String> boys = new ArrayList<String>();
		boys.add("boy1");
		boys.add("boy2");
		boys.add("boy3");
		Map<String, String> girls = new HashMap<String, String>();
		girls.put("a", "girl1");
		girls.put("b", "girl2");
		girls.put("c", "girl3");
		Example1 example1 = new Example1(1, "hyman", 33, true, boys, girls);
		
		File file = new File("/1.xml");
		serializer.write(example1, file);
		
		Example1 result = serializer.read(Example1.class, file);
		System.out.println(result.toString());
	}

}
