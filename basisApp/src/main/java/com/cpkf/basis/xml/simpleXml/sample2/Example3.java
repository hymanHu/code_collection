package com.cpkf.basis.xml.simpleXml.sample2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * 写xml
 * 1. 每个属性注解
 * 2. get & set方法
 * 3. 空构造
 * 4. 重写toString()方法
 */
public class Example3 {
	@Attribute
	private int id;
	@Element(required = true)
	private Example1 example1;
	@ElementList(required = true, type = Example2.class)
	private List<Example2> example2s = new ArrayList<Example2>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Example1 getExample1() {
		return example1;
	}

	public void setExample1(Example1 example1) {
		this.example1 = example1;
	}

	public List<Example2> getExample2s() {
		return example2s;
	}

	public void setExample2s(List<Example2> example2s) {
		this.example2s = example2s;
	}

	public Example3(int id, Example1 example1, List<Example2> example2s) {
		this.id = id;
		this.example1 = example1;
		this.example2s = example2s;
	}
	
	public Example3() {
	}

	@Override
	public String toString() {
		return "Example3 [id=" + id + ", example1=" + example1 + ", example2s="
				+ example2s + "]";
	}

	public static void main(String[] args) throws Exception {
		List<String> boys = new ArrayList<String>();
		boys.add("boy1");
		boys.add("boy2");
		boys.add("boy3");
		Map<String, String> girls = new HashMap<String, String>();
		girls.put("a", "girl1");
		girls.put("b", "girl2");
		girls.put("c", "girl3");
		Example1 example1 = new Example1(1, "hyman", 33, true, boys, girls);
		List<Example2> example2s = new ArrayList<Example2>();
		for (int i = 0; i < 4; i++) {
			Example2 example2 = new Example2(i, "name" + i);
			example2s.add(example2);
		}
		Example3 example3 = new Example3(1, example1, example2s);
		
		Serializer serializer = new Persister();
		File file = new File("/1.xml");
		serializer.write(example3, file);
		
		Example3 result = serializer.read(Example3.class, file);
		System.out.println(result.toString());
	}

}
