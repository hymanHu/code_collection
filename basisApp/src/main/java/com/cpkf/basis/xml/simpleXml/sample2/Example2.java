package com.cpkf.basis.xml.simpleXml.sample2;

import org.simpleframework.xml.Element;

public class Example2 {
	@Element(required = true)
	private int code;
	@Element(required = true)
	private String name;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Example2(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public Example2() {
	}

	@Override
	public String toString() {
		return "Example2 [code=" + code + ", name=" + name + "]";
	}

}
