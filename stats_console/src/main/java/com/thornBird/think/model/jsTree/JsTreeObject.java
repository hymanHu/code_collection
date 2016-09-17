package com.thornBird.think.model.jsTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jstree对象
 * @author hyman
 *
 */
public class JsTreeObject {
	public static final String STATE_OPEN = "open";
	public static final String STATE_CLOSED = "closed";
	
	private String data;
	private String icon = null;
	private String state = STATE_OPEN;
	private Map<String, String> attr  = new HashMap<String, String>();
	private List<JsTreeObject> children = new ArrayList<JsTreeObject>();
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Map<String, String> getAttr() {
		return attr;
	}
	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}
	public List<JsTreeObject> getChildren() {
		return children;
	}
	public void setChildren(List<JsTreeObject> children) {
		this.children = children;
	}
	
	@Override
	public String toString() {
		return "JsTreeObject [data=" + data + ", icon=" + icon + ", state=" + state + ", attr=" + attr + ", children="
				+ children + "]";
	}
	
}
