package com.thornBird.think.zookeeper.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树对象
 * @author Hyman
 */
public class JsTreeObject {
	private String data;
	private Map<String, String> attr = new HashMap<String, String>();
	private String icon;
	private String state;
	private List<JsTreeObject> children = new ArrayList<JsTreeObject>();

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Map<String, String> getAttr() {
		return attr;
	}

	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<JsTreeObject> getChildren() {
		return children;
	}

	public void setChildren(List<JsTreeObject> children) {
		this.children = children;
	}

	public void setAttr(String key, String value) {
		this.attr.put(key, value);
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "JsTreeObject [data=" + data + ", attr=" + attr + ", icon="
				+ icon + ", state=" + state + ", children=" + children + "]";
	}

}
