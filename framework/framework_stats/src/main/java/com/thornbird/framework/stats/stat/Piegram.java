package com.thornbird.framework.stats.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;

/**
 * stats 饼图
 * 
 * @author hyman
 */
public class Piegram {

	private List<String> names = new ArrayList<String>();

	private List<Double> values = new ArrayList<Double>();

	private Double total = 0.0;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	// 内部类，饼图每项
	class Item {
		private String label;
		private Double data;

		public Item(String label, Double data) {
			super();
			this.label = label;
			this.data = data;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Double getData() {
			return data;
		}

		public void setData(Double data) {
			this.data = data;
		}

	}

	// 将list用连接符串联成字符串
	public static String join(List<?> list, String conjunction) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object item : list) {
			if (first) {
				first = false;
			} else {
				sb.append(conjunction);
			}
			sb.append(item);
		}
		return sb.toString();
	}

	public String joinKeys() {
		return join(this.names, "|");
	}

	public String joinValues() {
		return join(this.values, ",");
	}

	public Map<String, Double> toMap() {
		Map<String, Double> items = new HashMap<String, Double>();
		for (int i = 0; i < names.size(); i++) {
			items.put(names.get(i), values.get(i));
		}
		return items;
	}

	public List<Item> toItemsList() {
		List<Item> items = new ArrayList<Piegram.Item>();
		for (int i = 0; i < names.size(); i++) {
			Item item = new Item(names.get(i), values.get(i));
			items.add(item);
		}
		return items;
	}
	
	public void addItem(String itemName, Double itemValue) {
		if (names.contains(itemName)) {
			int index = names.indexOf(itemName);
			Double origValue = values.get(index);
			total -= origValue;
			values.set(index, itemValue);
			total += itemValue;
		} else {
			names.add(itemName);
			values.add(itemValue);
			total += itemValue;
		}
	}
	
	public void addItem(Double itemValue) {
		String itemName = String.format("%s%s", "/dev/null/", names.size());
		addItem(itemName, itemValue);
	}
	
	public String toJSON() {
		return new GsonBuilder().create().toJson(this);
	}
	
	public String toNamesJSON() {
		return new GsonBuilder().create().toJson(names);
	}
	
	public String toValueJSON() {
		return new GsonBuilder().create().toJson(values);
	}
	
	public String toMapJSON() {
		return new GsonBuilder().create().toJson(toMap());
	}

	public static void main(String[] args) {
		Piegram gram = new Piegram();
        gram.addItem("12312", 5.8);
        gram.addItem(8.9);
        gram.addItem(6.9);
        gram.addItem(3.9);
        gram.addItem(12.2);

        System.out.println(gram.joinKeys());
        System.out.println(gram.joinValues());
        System.out.println(gram.toJSON());
        System.out.println(gram.toNamesJSON());
        System.out.println(gram.toValueJSON());
        System.out.println(gram.toMapJSON());

        System.out.println(gram.toItemsList().toString());
	}

}
